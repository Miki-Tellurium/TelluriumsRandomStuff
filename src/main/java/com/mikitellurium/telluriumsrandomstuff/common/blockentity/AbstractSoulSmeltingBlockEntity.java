package com.mikitellurium.telluriumsrandomstuff.common.blockentity;

import com.mikitellurium.telluriumsrandomstuff.lib.MappedItemStackHandler;
import com.mikitellurium.telluriumsrandomstuff.lib.SidedCapabilityProvider;
import com.mikitellurium.telluriumsrandomstuff.lib.WrappedHandler;
import com.mikitellurium.telluriumsrandomstuff.registry.ModFluids;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import com.mikitellurium.telluriumsrandomstuff.util.CachedObject;
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
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public abstract class AbstractSoulSmeltingBlockEntity<R extends Recipe<Container>> extends AbstractSoulFueledBlockEntity implements SidedCapabilityProvider<WrappedHandler> {

    private final int bucketSlot;
    private final MappedItemStackHandler itemHandler;
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private final RecipeManager.CachedCheck<Container, R> quickCheck;
    private CachedObject<R> cachedRecipe = CachedObject.empty();

    @SuppressWarnings("all")
    public AbstractSoulSmeltingBlockEntity(BlockEntityType<? extends AbstractSoulSmeltingBlockEntity> type,
                                           BlockPos pos, BlockState state, int tankCapacity, RecipeType<R> recipeType,
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

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return isInput(slot) || (isBucket(slot) && isFluidHandlerValid(stack));
            }
        };
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
        if (this.canRefillFluidTank(amount)) {
            ItemStack itemStack = this.getStackInSlot(this.bucketSlot);
            LazyOptional<IFluidHandlerItem> optional = FluidUtil.getFluidHandler(itemStack);
            optional.ifPresent((handler) -> {
                FluidStack fluidStack = new FluidStack(ModFluids.SOUL_LAVA_SOURCE.get(), amount);
                if (handler.drain(fluidStack, IFluidHandler.FluidAction.SIMULATE).getAmount() == amount) {
                    handler.drain(fluidStack, IFluidHandler.FluidAction.EXECUTE);
                    this.setStackInSlot(this.bucketSlot, handler.getContainer());
                    this.fillTank(amount);
                }
            });
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

    public boolean isItemValid(int slot, ItemStack itemStack) {
        return this.itemHandler.isItemValid(slot, itemStack);
    }

    public ItemStack getStackInSlot(int slot) {
        return this.itemHandler.getStackInSlot(slot);
    }

    public void setStackInSlot(int slot, ItemStack itemStack) {
        this.itemHandler.setStackInSlot(slot, itemStack);
    }

    public void forEachStack(BiPredicate<Integer, ItemStack> filter, BiConsumer<Integer, ItemStack> consumer) {
        if (filter == null) {
            this.itemHandler.forEachStack(consumer);
        } else {
            this.itemHandler.forEachStack(filter, consumer);
        }
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
            Direction localDir = this.getBlockState().getValue(AbstractFurnaceBlock.FACING);

            if(side == Direction.UP || side == Direction.DOWN) {
                return this.sidedLazyOptional(side).cast();
            }
            // Get the correct direction based on the furnace FACING property
            return switch (localDir) {
                default -> this.sidedLazyOptional(side.getOpposite()).cast();
                case EAST -> this.sidedLazyOptional(side.getClockWise()).cast();
                case SOUTH -> this.sidedLazyOptional(side).cast();
                case WEST -> this.sidedLazyOptional(side.getCounterClockWise()).cast();
            };
        }

        return super.getCapability(cap, side);
    }

    @Override
    public WrappedHandler capabilityBySide(Direction side) {
        return switch (side) {
            case UP -> new WrappedHandler(itemHandler, (i, s) -> true, itemHandler::isInput);
            case DOWN -> new WrappedHandler(itemHandler, (i, s) -> false, (i) -> itemHandler.isOutput(i) || hasEmptyBucket(i));
            case NORTH, SOUTH, EAST, WEST -> new WrappedHandler(itemHandler, (i, s) -> itemHandler.isBucket(i) && itemHandler.isItemValid(this.getBucketSlot(), s), itemHandler::isBucket);
        };
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
