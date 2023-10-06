package com.mikitellurium.telluriumsrandomstuff.common.blockentity;

import com.mikitellurium.telluriumsrandomstuff.common.block.SoulFurnaceBlock;
import com.mikitellurium.telluriumsrandomstuff.integration.jei.recipe.SoulLavaInfoCategory;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlockEntities;
import com.mikitellurium.telluriumsrandomstuff.registry.ModFluids;
import com.mikitellurium.telluriumsrandomstuff.client.gui.menu.SoulFurnaceMenu;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import com.mikitellurium.telluriumsrandomstuff.common.recipe.SoulFurnaceRecipe;
import com.mikitellurium.telluriumsrandomstuff.common.networking.ModMessages;
import com.mikitellurium.telluriumsrandomstuff.common.networking.packets.FluidSyncS2CPacket;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

public class AbstractSoulLavaFurnace extends AbstractSoulFueledBlockEntity {

    private final ItemStackHandler itemHandler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case (BUCKET_SLOT) -> stack.is(ModItems.SOUL_LAVA_BUCKET.get());
                case (INPUT_SLOT) -> true;
                case (OUTPUT_SLOT) -> false;
                default -> super.isItemValid(slot, stack);
            };
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

    private static final int BUCKET_SLOT = 0;
    private static final int INPUT_SLOT = 1;
    private static final int OUTPUT_SLOT = 2;
    private static final int litFurnaceCost = 50; // How much soul lava is consumed to lit the furnace
    private static final int itemSmelted = 8; // How many item get smelted with a full "lit"
    private int progress = 0;
    private int maxProgress = 100;
    private int litTime = 0;
    private int maxLitTime = maxProgress * itemSmelted;
    public RecipeType<?> recipeType;
    private final RecipeManager.CachedCheck<Container, ? extends Recipe<?>> quickCheck;
    public final ContainerData containerData = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> AbstractSoulLavaFurnace.this.progress;
                case 1 -> AbstractSoulLavaFurnace.this.maxProgress;
                case 2 -> AbstractSoulLavaFurnace.this.litTime;
                case 3 -> AbstractSoulLavaFurnace.this.maxLitTime;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> AbstractSoulLavaFurnace.this.progress = value;
                case 1 -> AbstractSoulLavaFurnace.this.maxProgress = value;
                case 2 -> AbstractSoulLavaFurnace.this.litTime = value;
                case 3 -> AbstractSoulLavaFurnace.this.maxLitTime = value;
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    };

    @SuppressWarnings("all")
    public AbstractSoulLavaFurnace(BlockEntityType<? extends AbstractSoulLavaFurnace> entityType, RecipeType<?> recipeType,
                                   BlockPos pos, BlockState state) {
        super(entityType, pos, state, 4000);
        this.recipeType = recipeType;
        this.quickCheck = RecipeManager.createCheck((RecipeType)recipeType);
    }

    private static ItemStack itemCheck = ItemStack.EMPTY;

    //private static boolean test = true;
    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        if (level.isClientSide) {
            return;
        }

        // For some reason half the time the input item is air, accounting for that
        if (!this.itemHandler.getStackInSlot(INPUT_SLOT).is(Items.AIR)) {
            // Check if the input item is the same as last tick
            if (this.itemHandler.getStackInSlot(INPUT_SLOT).getItem() != itemCheck.getItem()) {
                this.resetProgress();
            }
            itemCheck = this.itemHandler.getStackInSlot(INPUT_SLOT);
        }
        this.tankTick(this.itemHandler, BUCKET_SLOT);

        // Recipe handling
        if (this.hasValidRecipe()) {
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
                    this.smeltItem();
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

    private boolean hasValidRecipe() {
        if (this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() >=
                this.itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize()) return false;

        Optional<?> optionalRecipe = getRecipe();
        if (optionalRecipe.isEmpty()) return false;
        Recipe<?> recipe = (Recipe<?>) optionalRecipe.get();
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() ||
                recipe.getResultItem(level.registryAccess()).getItem() == this.itemHandler.getStackInSlot(OUTPUT_SLOT).getItem();
    }

    @SuppressWarnings("unchecked")
    private void smeltItem() {
        Optional<?> optionalRecipe = getRecipe();
        if (optionalRecipe.isPresent()) {
            Recipe<SimpleContainer> recipe = (Recipe<SimpleContainer>) optionalRecipe.get();
            ItemStack result = recipe.assemble(this.getInventory(), level.registryAccess());
            this.itemHandler.getStackInSlot(INPUT_SLOT).shrink(1);
            ItemStack outputStack = this.itemHandler.getStackInSlot(OUTPUT_SLOT);
            if (outputStack.isEmpty()) {
                this.itemHandler.setStackInSlot(OUTPUT_SLOT, result);
            } else {
                outputStack.grow(result.getCount());
            }
        }
    }

    private Optional<? extends Recipe<?>> getRecipe() {
        return this.quickCheck.getRecipeFor(this.getInventory(), this.level);
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private boolean hasEnoughFuel() {
        return this.getFluidTank().getFluidAmount() >= litFurnaceCost;
    }

    private boolean isLit() {
        return this.litTime > 0;
    }

    private SimpleContainer getInventory() {
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

    // Capability stuff
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER) {
            if(side == null) {
                return lazyItemHandler.cast();
            }
            // Return capability based on side
            if(directionWrappedHandlerMap.containsKey(side)) {
                Direction localDir = this.getBlockState().getValue(AbstractFurnaceBlock.FACING);

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

    // NBT stuff
    @Override
    public void deserializeNBT(CompoundTag nbt) {
        super.deserializeNBT(nbt);
    }

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
    }

}
