package com.mikitellurium.telluriumsrandomstuff.common.blockentity;

import com.mikitellurium.telluriumsrandomstuff.client.gui.menu.SoulFurnaceMenu;
import com.mikitellurium.telluriumsrandomstuff.common.block.SoulFurnaceBlock;
import com.mikitellurium.telluriumsrandomstuff.common.blockentity.util.SoulFurnaceItemHandler;
import com.mikitellurium.telluriumsrandomstuff.lib.SidedCapabilityProvider;
import com.mikitellurium.telluriumsrandomstuff.lib.WrappedHandler;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlockEntities;
import com.mikitellurium.telluriumsrandomstuff.registry.ModFluids;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import com.mikitellurium.telluriumsrandomstuff.util.CachedObject;
import com.mikitellurium.telluriumsrandomstuff.util.LogUtils;
import net.minecraft.Util;
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
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings("ConstantConditions")
public class SoulFurnaceBlockEntity extends AbstractSoulFueledBlockEntity implements MenuProvider, SidedCapabilityProvider<WrappedHandler> {

    private static final int BUCKET_SLOT = 0;
    private static final List<Integer> inputSlots = List.of(1, 2, 3, 4);
    private static final List<Integer> outputSlots = List.of(5, 6, 7, 8);

    private final SoulFurnaceItemHandler itemHandler = new SoulFurnaceItemHandler() {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return isInput(slot) || (isBucket(slot) && isFluidHandlerValid(stack));
        }
    };
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private final RecipeManager.CachedCheck<Container, SmeltingRecipe> quickCheck = RecipeManager.createCheck(RecipeType.SMELTING);
    private final Map<Integer, CachedObject<SmeltingRecipe>> cachedRecipes = Util.make(new HashMap<>(), (map) -> {
        inputSlots.forEach((slot) -> map.put(slot, CachedObject.empty()));
    });

    private static final int litFurnaceCost = 100; // How much soul lava is consumed to lit the furnace
    private static final int itemSmelted = 4; // How many item get smelted each slot with a full "lit"
    private int maxProgress = 100;
    private int litTime = 0;
    private int maxLitTime = maxProgress * itemSmelted;
    private final ContainerData containerData = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> SoulFurnaceBlockEntity.this.maxProgress;
                case 1, 2, 3, 4 -> SoulFurnaceBlockEntity.this.itemHandler.getProgress(index);
                case 5 -> SoulFurnaceBlockEntity.this.litTime;
                case 6 -> SoulFurnaceBlockEntity.this.maxLitTime;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> SoulFurnaceBlockEntity.this.maxProgress = value;
                case 1, 2, 3, 4 -> SoulFurnaceBlockEntity.this.itemHandler.setProgress(index, value);
                case 5 -> SoulFurnaceBlockEntity.this.litTime = value;
                case 6 -> SoulFurnaceBlockEntity.this.maxLitTime = value;
            }
        }

        @Override
        public int getCount() {
            return 7;
        }
    };

    public SoulFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SOUL_FURNACE.get(), pos, state, 4000);
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        if (level.isClientSide) {
            return;
        }

        this.handleTankRefill();
        for (int slot : inputSlots) {
            Optional<SmeltingRecipe> optionalRecipe = this.getRecipe(slot);
            if (optionalRecipe.isPresent() && this.canProcessRecipe(optionalRecipe.get())) {
                SmeltingRecipe recipe = optionalRecipe.get();
                this.validateRecipe(slot, recipe);
                this.onProcessRecipe(slot, recipe);
            } else {
                this.resetProgress(slot);
            }
        }

        level.setBlock(blockPos, blockState.setValue(SoulFurnaceBlock.LIT, this.isLit()), 2);
        if (this.isLit()) {
            this.litTime--;
        }
        setChanged(level, blockPos, blockState);
    }

    private void handleTankRefill() {
        final int amount = 1000;
        if (this.canRefillFluidTank(amount)) {
            ItemStack itemStack = this.getStackInSlot(BUCKET_SLOT);
            LazyOptional<IFluidHandlerItem> optional = FluidUtil.getFluidHandler(itemStack);
            optional.ifPresent((handler) -> {
                FluidStack fluidStack = new FluidStack(ModFluids.SOUL_LAVA_SOURCE.get(), amount);
                if (handler.drain(fluidStack, IFluidHandler.FluidAction.SIMULATE).getAmount() == amount) {
                    handler.drain(fluidStack, IFluidHandler.FluidAction.EXECUTE);
                    this.setStackInSlot(BUCKET_SLOT, handler.getContainer());
                    this.fillTank(amount);
                }
            });
        }
    }

    private void validateRecipe(int slot, SmeltingRecipe recipe) {
        if (!recipe.equals(this.cachedRecipes.get(slot).get())) {
            this.resetProgress(slot);
        }
        this.cachedRecipes.put(slot, CachedObject.of(recipe));
    }

    protected boolean canProcessRecipe(SmeltingRecipe recipe) {
        for (int slot : outputSlots) {
            ItemStack outputStack = this.getStackInSlot(slot);
            if (outputStack.getCount() >= outputStack.getMaxStackSize()) continue;
            ItemStack result = recipe.getResultItem(this.level.registryAccess());
            if (!outputStack.isEmpty() && !result.is(outputStack.getItem())) continue;
            if (outputStack.getCount() + result.getCount() <= outputStack.getMaxStackSize()) return true;
        }
        return false;
    }

    protected void onProcessRecipe(int slot, SmeltingRecipe recipe) {
        if (!this.isLit() && this.hasEnoughFuel()) {
            this.drainTank(litFurnaceCost);
            this.litTime = this.maxLitTime;
        }
        if (this.isLit()) {
            this.itemHandler.tickProgress(slot);
            if (this.itemHandler.getProgress(slot) >= this.maxProgress) {
                this.produceOutput(slot, recipe);
                this.resetProgress(slot);
            }
        } else {
            this.resetProgress(slot);
        }
    }

    protected void produceOutput(int inputSlot, SmeltingRecipe recipe) {
        ItemStack resultStack = recipe.assemble(this.getInventory(), level.registryAccess());
        int outputSlot = this.findOutputSlot(resultStack);
        ItemStack outputStack = this.getStackInSlot(outputSlot);
        this.getStackInSlot(inputSlot).shrink(1);
        if (outputStack.isEmpty()) {
            this.setStackInSlot(outputSlot, resultStack);
        } else {
            outputStack.grow(resultStack.getCount());
        }
    }

    private int findOutputSlot(ItemStack resultStack) {
        for (int slot : outputSlots) {
            ItemStack outputStack = this.getStackInSlot(slot);
            if (outputStack.isEmpty()) return slot;
            if (resultStack.is(outputStack.getItem()) &&
                    resultStack.getCount() + outputStack.getCount() <= outputStack.getMaxStackSize()) return slot;
        }
        throw new RuntimeException("Could not find available slot for output item");
    }

    protected void resetProgress(int slot) {
        this.itemHandler.resetProgress(slot);
    }

    protected Optional<SmeltingRecipe> getRecipe(int slot) {
        return this.quickCheck.getRecipeFor(new SimpleContainer(this.getStackInSlot(slot)), this.level);
    }

    private boolean isLit() {
        return this.litTime > 0;
    }

    private boolean hasEnoughFuel() {
        return this.getFluidTank().getFluidAmount() >= litFurnaceCost;
    }

    public SimpleContainer getInventory() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, this.getStackInSlot(i));
        }

        return inventory;
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

    private boolean hasEmptyBucket(int slot) {
        return slot == BUCKET_SLOT && this.getStackInSlot(BUCKET_SLOT).is(Items.BUCKET);
    }

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
        return new SoulFurnaceMenu(id, inventory, this, this.containerData);
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
            case UP, EAST, NORTH, SOUTH -> new WrappedHandler(itemHandler, (i, s) -> true, itemHandler::isInput);
            case DOWN -> new WrappedHandler(itemHandler, (i, s) -> false, (i) -> itemHandler.isOutput(i) || hasEmptyBucket(i));
            case WEST -> new WrappedHandler(itemHandler, (i, s) -> itemHandler.isBucket(i) && itemHandler.isItemValid(BUCKET_SLOT, s), itemHandler::isBucket);
        };
    }

    // NBT
    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        for (int slot : inputSlots) {
            Optional<SmeltingRecipe> optionalRecipe = this.getRecipe(slot);
            cachedRecipes.put(slot, optionalRecipe.map(CachedObject::of).orElseGet(CachedObject::empty));
        }
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
