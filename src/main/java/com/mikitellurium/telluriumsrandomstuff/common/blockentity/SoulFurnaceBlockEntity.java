package com.mikitellurium.telluriumsrandomstuff.common.blockentity;

import com.mikitellurium.telluriumsrandomstuff.client.gui.menu.SoulFurnaceMenu;
import com.mikitellurium.telluriumsrandomstuff.common.block.SoulFurnaceBlock;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlockEntities;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import com.mikitellurium.telluriumsrandomstuff.util.SpecialMappedItemStackHandler;
import com.mikitellurium.telluriumsrandomstuff.util.WrappedHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

public class SoulFurnaceBlockEntity extends AbstractSoulFueledBlockEntity implements MenuProvider {

    private static final int BUCKET_SLOT = 0;
    private static final int INPUT_SLOT = 1;
    private static final int OUTPUT_SLOT = 2;
    private final SpecialMappedItemStackHandler itemHandler =
            new SpecialMappedItemStackHandler(3, BUCKET_SLOT, new int[] {INPUT_SLOT}, new int[] {OUTPUT_SLOT}) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return isInput(slot) || (isBucket(slot) && stack.is(ModItems.SOUL_LAVA_BUCKET.get()));
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    // Handle item transportation trough other blocks
    // Credit to Kaupenjoe
    private final Map<Direction, LazyOptional<WrappedHandler>> directionWrappedHandlerMap =
            Map.of(Direction.UP, LazyOptional.of(() -> new WrappedHandler(itemHandler,
                            (i) -> i == INPUT_SLOT,
                            (i, s) -> true)),
                    Direction.DOWN, LazyOptional.of(() -> new WrappedHandler(itemHandler,
                            (i) -> i == OUTPUT_SLOT || hasEmptyBucket(i),
                            (i, s) -> false)),
                    Direction.NORTH, LazyOptional.of(() -> new WrappedHandler(itemHandler,
                            (i) -> i == BUCKET_SLOT,
                            (i, s) -> i == BUCKET_SLOT && itemHandler.isItemValid(BUCKET_SLOT, s))),
                    Direction.SOUTH, LazyOptional.of(() -> new WrappedHandler(itemHandler,
                            (i) -> i == BUCKET_SLOT,
                            (i, s) -> i == BUCKET_SLOT && itemHandler.isItemValid(BUCKET_SLOT, s))),
                    Direction.EAST, LazyOptional.of(() -> new WrappedHandler(itemHandler,
                            (i) -> i == BUCKET_SLOT,
                            (i, s) -> i == BUCKET_SLOT && itemHandler.isItemValid(BUCKET_SLOT, s))),
                    Direction.WEST, LazyOptional.of(() -> new WrappedHandler(itemHandler,
                            (i) -> i == BUCKET_SLOT,
                            (i, s) -> i == BUCKET_SLOT && itemHandler.isItemValid(BUCKET_SLOT, s)))
            );

    private static final int litFurnaceCost = 50; // How much soul lava is consumed to lit the furnace
    private static final int itemSmelted = 8; // How many item get smelted with a full "lit"
    private int progress = 0;
    private int maxProgress = 100;
    private int litTime = 0;
    private int maxLitTime = maxProgress * itemSmelted;
    private final RecipeManager.CachedCheck<Container, Recipe<Container>> quickCheck;
    private final ContainerData containerData = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> SoulFurnaceBlockEntity.this.progress;
                case 1 -> SoulFurnaceBlockEntity.this.maxProgress;
                case 2 -> SoulFurnaceBlockEntity.this.litTime;
                case 3 -> SoulFurnaceBlockEntity.this.maxLitTime;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> SoulFurnaceBlockEntity.this.progress = value;
                case 1 -> SoulFurnaceBlockEntity.this.maxProgress = value;
                case 2 -> SoulFurnaceBlockEntity.this.litTime = value;
                case 3 -> SoulFurnaceBlockEntity.this.maxLitTime = value;
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    };
    private ItemStack itemCheck = ItemStack.EMPTY;

    @SuppressWarnings("all")
    public SoulFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SOUL_FURNACE.get(), pos, state, 4000);
        this.quickCheck = RecipeManager.createCheck((RecipeType) RecipeType.SMELTING);
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        if (level.isClientSide) {
            return;
        }

        this.handleTankRefill();
        this.validateCurrentRecipe();

        // Recipe handling
        Optional<Recipe<Container>> optionalRecipe = this.getRecipe();
        if (optionalRecipe.isPresent() && this.canProcessRecipe(optionalRecipe.get())) {
            Recipe<Container> recipe = optionalRecipe.get();
            // Lit if furnace is not lit
            if (!this.isLit() && hasEnoughFuel()) {
                this.drainTank(litFurnaceCost);
                this.litTime = this.maxLitTime;
            }
            // If furnace is lit start smelting item
            if (this.isLit()) {
                this.progress++;
                // If progress is completed output smelted item
                if (this.progress >= this.maxProgress) {
                    this.smeltItem(recipe);
                    this.resetProgress();
                }
            } else {
                this.resetProgress();
            }

        } else {
            this.resetProgress();
        }

        level.setBlock(blockPos, blockState.setValue(SoulFurnaceBlock.LIT, this.isLit()), 2);
        if (this.isLit()) {
            this.litTime--;
        }
        setChanged(level, blockPos, blockState);
    }

    void handleTankRefill() {
        super.handleTankRefill(itemHandler, BUCKET_SLOT);
    }

    void validateCurrentRecipe() {
        if (this.itemHandler.getStackInSlot(INPUT_SLOT).getItem() != itemCheck.getItem()) {
            this.resetProgress();
        }
        itemCheck = this.itemHandler.getStackInSlot(INPUT_SLOT);
    }

    boolean canProcessRecipe(Recipe<?> recipe) {
        if (this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() >=
                this.itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize()) return false;
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() ||
                recipe.getResultItem(level.registryAccess()).getItem() == this.itemHandler.getStackInSlot(OUTPUT_SLOT).getItem();
    }

    @SuppressWarnings("all")
    void smeltItem(Recipe<Container> recipe) {
        ItemStack result = recipe.assemble(this.getInventory(), level.registryAccess());
        ItemStack outputStack = this.itemHandler.getStackInSlot(OUTPUT_SLOT);
        this.itemHandler.getStackInSlot(INPUT_SLOT).shrink(1);
        if (outputStack.isEmpty()) {
            this.itemHandler.setStackInSlot(OUTPUT_SLOT, result);
        } else {
            outputStack.grow(result.getCount());
        }
    }

    private Optional<Recipe<Container>> getRecipe() {
        return this.quickCheck().getRecipeFor(new SimpleContainer(this.itemHandler.getStackInSlot(INPUT_SLOT)), this.level);
    }

    protected void resetProgress() {
        this.progress = 0;
    }

    protected boolean hasEnoughFuel() {
        return this.getFluidTank().getFluidAmount() >= litFurnaceCost;
    }

    private boolean isLit() {
        return this.litTime > 0;
    }

    public ContainerData getContainerData() {
        return this.containerData;
    }

    public RecipeManager.CachedCheck<Container, Recipe<Container>> quickCheck() {
        return this.quickCheck;
    }

    public SimpleContainer getInventory() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        return inventory;
    }

    private boolean hasEmptyBucket(int slot) {
        return slot == BUCKET_SLOT && this.itemHandler.getStackInSlot(BUCKET_SLOT).is(Items.BUCKET);
    }

    @SuppressWarnings("ConstantConditions")
    public void dropItemsOnBreak() {
        SimpleContainer inventory = getInventory();
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("blockentity.telluriumsrandomstuff.soul_furnace");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new SoulFurnaceMenu(id, inventory, this, this.getContainerData());
    }

    // Capabilities
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER) {
            if(side == null) {
                return lazyItemHandler.cast();
            }
            // Return capability based on side
            if(directionWrappedHandlerMap.containsKey(side)) {
                Direction localDir = this.getBlockState().getValue(SoulFurnaceBlock.FACING);

                if(side == Direction.UP || side == Direction.DOWN) {
                    return directionWrappedHandlerMap.get(side).cast();
                }
                // Get the correct direction based on the furnace FACING property
                return switch (localDir) {
                    default -> directionWrappedHandlerMap.get(side.getOpposite()).cast();
                    case EAST -> directionWrappedHandlerMap.get(side.getClockWise()).cast();
                    case SOUTH -> directionWrappedHandlerMap.get(side).cast();
                    case WEST -> directionWrappedHandlerMap.get(side.getCounterClockWise()).cast();
                };
            }
        }

        return super.getCapability(cap, side);
    }

    // NBT
    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.put("inventory", itemHandler.serializeNBT());
        tag.putInt("soul_furnace.progress", this.progress);
        tag.putInt("soul_furnace.litTime", this.litTime);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        itemHandler.deserializeNBT(tag.getCompound("inventory"));
        this.progress = tag.getInt("soul_furnace.progress");
        this.litTime = tag.getInt("soul_furnace.litTime");
        itemCheck = itemHandler.getStackInSlot(INPUT_SLOT);
    }

}
