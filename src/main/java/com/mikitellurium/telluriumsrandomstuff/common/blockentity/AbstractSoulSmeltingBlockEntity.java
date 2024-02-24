package com.mikitellurium.telluriumsrandomstuff.common.blockentity;

import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import com.mikitellurium.telluriumsrandomstuff.util.CachedObject;
import com.mikitellurium.telluriumsrandomstuff.util.MappedItemStackHandler;
import com.mikitellurium.telluriumsrandomstuff.util.WrappedHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

public abstract class AbstractSoulSmeltingBlockEntity<R extends Recipe<Container>> extends AbstractSoulFueledBlockEntity {

    private final int bucketSlot;
    private final MappedItemStackHandler itemHandler;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private final Map<Direction, LazyOptional<WrappedHandler>> directionWrappedHandlerMap;

    private final RecipeManager.CachedCheck<Container, R> quickCheck;
    private CachedObject<R> cachedRecipe = CachedObject.empty();

    @SuppressWarnings("all")
    public AbstractSoulSmeltingBlockEntity(BlockEntityType<? extends AbstractSoulSmeltingBlockEntity> type,
                                           BlockPos pos, BlockState state,  int tankCapacity, RecipeType<R> recipeType,
                                           int itemSlots, Predicate<Integer> isInputSlot, Predicate<Integer> isOutputSlot,
                                           int bucketSlot) {
        super(type, pos, state, tankCapacity);
        this.quickCheck = RecipeManager.createCheck((RecipeType) recipeType);
        this.bucketSlot = bucketSlot;
        this.itemHandler = new MappedItemStackHandler(itemSlots, isInputSlot, isOutputSlot, (i) -> i == bucketSlot) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }
        };
        // Handle item transportation trough other blocks
        // Credit to Kaupenjoe
        this.directionWrappedHandlerMap = Map.of(
                Direction.UP, LazyOptional.of(() -> new WrappedHandler(itemHandler,
                        (i, s) -> true,
                        itemHandler::isInput)),
                Direction.DOWN, LazyOptional.of(() -> new WrappedHandler(itemHandler,
                        (i, s) -> false,
                        (i) -> itemHandler.isOutput(i) || hasEmptyBucket(i))),
                Direction.NORTH, LazyOptional.of(() -> new WrappedHandler(itemHandler,
                        (i, s) -> itemHandler.isBucket(i) && itemHandler.isItemValid(this.getBucketSlot(), s),
                        itemHandler::isBucket)),
                Direction.SOUTH, LazyOptional.of(() -> new WrappedHandler(itemHandler,
                        (i, s) -> itemHandler.isBucket(i) && itemHandler.isItemValid(this.getBucketSlot(), s),
                        itemHandler::isBucket)),
                Direction.EAST, LazyOptional.of(() -> new WrappedHandler(itemHandler,
                        (i, s) -> itemHandler.isBucket(i) && itemHandler.isItemValid(this.getBucketSlot(), s),
                        itemHandler::isBucket)),
                Direction.WEST, LazyOptional.of(() -> new WrappedHandler(itemHandler,
                        (i, s) -> itemHandler.isBucket(i) && itemHandler.isItemValid(this.getBucketSlot(), s),
                        itemHandler::isBucket)));
    }

    /**
     * Called every tick to update the block entity logic
     * @param level
     * @param blockPos
     * @param blockState
     */
    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        if (level.isClientSide) {
            return;
        }
        this.handleTankRefill();
        Optional<R> optionalRecipe = this.getRecipe();
        if (optionalRecipe.isPresent() && this.canProcessRecipe(optionalRecipe.get())) {
            R recipe = optionalRecipe.get();
            this.validateCurrentRecipe(recipe);
            this.onProcessRecipe(recipe);
        } else {
            this.resetProgress();
        }
    }

    private void handleTankRefill() {
        final int amount = 1000;
        if (this.itemHandler.getStackInSlot(this.bucketSlot).is(ModItems.SOUL_LAVA_BUCKET.get())
                && this.canRefillFluidTank(amount)) {
            this.itemHandler.setStackInSlot(bucketSlot, Items.BUCKET.getDefaultInstance());
            this.fillTank(amount);
        }
    }

    private void validateCurrentRecipe(R recipe) {
        if (!recipe.equals(this.cachedRecipe.get())) {
            this.resetProgress();
        }
        this.cachedRecipe = CachedObject.of(recipe);
    }

    /**
     * Checks if the current recipe can be processed .
     * @param recipe the recipe to be checked
     * @return if the recipe can be checked or not
     */
    protected abstract boolean canProcessRecipe(R recipe);

    /**
     * Handle the recipe processing.
     * @param recipe the recipe that is being processed
     */
    protected abstract void onProcessRecipe(R recipe);

    /**
     * Produce an output when the recipe has finished processing.
     * @param recipe the recipe that is being processed
     */
    protected abstract void produceOutput(R recipe);

    /**
     * Used to reset the progress when recipe is not
     * present or can't be processed.
     */
    protected abstract void resetProgress();

    /**
     * @return an optional that contains a recipe if one is present or an empty optional if not
     */
    protected abstract Optional<R> getRecipe();

    public boolean hasEnoughFuel(int recipeCost) {
        return this.getFluidTank().getFluidAmount() >= recipeCost;
    }

    public RecipeManager.CachedCheck<Container, R> quickCheck() {
        return this.quickCheck;
    }

    public MappedItemStackHandler getItemHandler() {
        return this.itemHandler;
    }
    // todo use this on child classes
    public ItemStack getStackInSlot(int slot) {
        return this.itemHandler.getStackInSlot(slot);
    }

    public SimpleContainer getInventory() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        return inventory;
    }

    private boolean hasEmptyBucket(int slot) {
        return slot == this.bucketSlot && this.itemHandler.getStackInSlot(this.bucketSlot).is(Items.BUCKET);
    }

    public int getBucketSlot() {
        return this.bucketSlot;
    }

    @SuppressWarnings("ConstantConditions")
    public void dropItemsOnBreak() {
        SimpleContainer inventory = getInventory();
        Containers.dropContents(this.level, this.worldPosition, inventory);
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

    // NBT
    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        Optional<R> optionalRecipe = this.getRecipe();
        cachedRecipe = optionalRecipe.map(CachedObject::of).orElseGet(CachedObject::empty);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.put("soul_smelter.inventory", itemHandler.serializeNBT());
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        itemHandler.deserializeNBT(tag.getCompound("soul_smelter.inventory"));
    }

}
