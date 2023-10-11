package com.mikitellurium.telluriumsrandomstuff.common.blockentity;

import com.mikitellurium.telluriumsrandomstuff.client.gui.menu.SoulFurnaceMenu;
import com.mikitellurium.telluriumsrandomstuff.client.gui.menu.SoulInfuserMenu;
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
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

public class SoulInfuserBlockEntity extends AbstractSoulFueledBlockEntity implements MenuProvider {

    private static final int BUCKET_SLOT = 0;
    private static final int INPUT_SLOT1 = 1;
    private static final int INPUT_SLOT2 = 2;
    private static final int OUTPUT_SLOT = 3;
    private final SpecialMappedItemStackHandler itemHandler =
            new SpecialMappedItemStackHandler(4, BUCKET_SLOT, new int[] {INPUT_SLOT1, INPUT_SLOT2}, new int[] {OUTPUT_SLOT}) {
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
                            itemHandler::isInput,
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

    private int progress = 0;
    private int maxProgress = 100;
    private final RecipeManager.CachedCheck<Container, Recipe<Container>> quickCheck;
    private final ContainerData containerData = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> SoulInfuserBlockEntity.this.progress;
                case 1 -> SoulInfuserBlockEntity.this.maxProgress;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> SoulInfuserBlockEntity.this.progress = value;
                case 1 -> SoulInfuserBlockEntity.this.maxProgress = value;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    };
    private ItemStack itemCheck = ItemStack.EMPTY;

    @SuppressWarnings("all")
    public SoulInfuserBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SOUL_INFUSER.get(), pos, state, 4000);
        this.quickCheck = RecipeManager.createCheck((RecipeType) RecipeType.SMELTING);
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        if (level.isClientSide) {
            return;
        }

//        this.validateCurrentRecipe();
//        this.handleTankRefill();
//
//        // Recipe handling
//        if (this.hasValidRecipe()) {
//            // Lit if furnace is not lit
//            if (!this.isLit() && hasEnoughFuel()) {
//                this.drainTank(litFurnaceCost);
//                this.litTime = this.maxLitTime;
//            }
//            // If furnace is lit start smelting item
//            if (this.isLit()) {
//                this.progress++;
//                // If progress is completed output smelted item
//                if (this.progress >= this.maxProgress) {
//                    this.smeltItem();
//                    this.resetProgress();
//                }
//            } else {
//                this.resetProgress();
//            }
//
//        } else {
//            this.resetProgress();
//        }
//
//        level.setBlock(blockPos, blockState.setValue(SoulFurnaceBlock.LIT, this.isLit()), 2);
//        if (this.isLit()) {
//            this.litTime--;
//        }
//        setChanged(level, blockPos, blockState);
    }

//    void handleTankRefill() {
//        super.handleTankRefill(itemHandler, BUCKET_SLOT);
//    }
//
//    void validateCurrentRecipe() {
//        // For some reason half the time the input item is air, accounting for that
//        if (!this.itemHandler.getStackInSlot(INPUT_SLOT).is(Items.AIR)) {
//            if (this.itemHandler.getStackInSlot(INPUT_SLOT).getItem() != itemCheck.getItem()) {
//                this.resetProgress();
//            }
//            itemCheck = this.itemHandler.getStackInSlot(INPUT_SLOT);
//        }
//    }

//    boolean hasValidRecipe() {
//        if (this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() >=
//                this.itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize()) return false;
//
//        Optional<?> optionalRecipe = getRecipe();
//        if (optionalRecipe.isEmpty()) return false;
//        Recipe<?> recipe = (Recipe<?>) optionalRecipe.get();
//        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() ||
//                recipe.getResultItem(level.registryAccess()).getItem() == this.itemHandler.getStackInSlot(OUTPUT_SLOT).getItem();
//    }

    @SuppressWarnings("all")
//    void smeltItem() {
//        Optional<?> optionalRecipe = getRecipe();
//        if (optionalRecipe.isPresent()) {
//            Recipe<Container> recipe = (Recipe<Container>) optionalRecipe.get();
//            ItemStack result = recipe.getResultItem(level.registryAccess());
//            this.itemHandler.getStackInSlot(INPUT_SLOT1).shrink(1);
//            ItemStack outputStack = this.itemHandler.getStackInSlot(OUTPUT_SLOT);
//            if (outputStack.isEmpty()) {
//                this.itemHandler.setStackInSlot(OUTPUT_SLOT, result);
//            } else {
//                outputStack.grow(result.getCount());
//            }
//        }
//    }

//    Optional<Recipe<Container>> getRecipe() {
//        //return this.quickCheck().getRecipeFor(new SimpleContainer(this.itemHandler.getStackInSlot(INPUT_SLOT1)), this.level);
//    }

//    protected void resetProgress() {
//        this.progress = 0;
//    }
//
//    protected boolean hasEnoughFuel() {
//        return this.getFluidTank().getFluidAmount() >= litFurnaceCost;
//    }
//
//    private boolean isLit() {
//        return this.litTime > 0;
//    }
//
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

    protected ItemStackHandler getItemHandler() {
        return this.itemHandler;
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
        return Component.translatable("blockentity.telluriumsrandomstuff.soul_infuser");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new SoulInfuserMenu(id, inventory, this, this.getContainerData());
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
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        itemHandler.deserializeNBT(tag.getCompound("inventory"));
        itemCheck = itemHandler.getStackInSlot(INPUT_SLOT1);
    }

}