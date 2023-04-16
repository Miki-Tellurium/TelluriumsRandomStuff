package com.mikitellurium.telluriumsrandomstuff.blockentity.custom;

import com.mikitellurium.telluriumsrandomstuff.block.custom.SoulFurnaceBlock;
import com.mikitellurium.telluriumsrandomstuff.blockentity.ModBlockEntities;
import com.mikitellurium.telluriumsrandomstuff.fluid.ModFluids;
import com.mikitellurium.telluriumsrandomstuff.gui.SoulFurnaceMenu;
import com.mikitellurium.telluriumsrandomstuff.item.ModItems;
import com.mikitellurium.telluriumsrandomstuff.networking.ModMessages;
import com.mikitellurium.telluriumsrandomstuff.networking.packets.FluidSyncS2CPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
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

public class SoulFurnaceBlockEntity extends BlockEntity implements MenuProvider {

    private final ItemStackHandler itemHandler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            if (slot == BUCKET_SLOT) {
                return stack.is(ModItems.SOUL_LAVA_BUCKET.get());
            } else if (slot == OUTPUT_SLOT) {
                return false;
            } else {
                return true;
            }
        }
    };

    private final FluidTank fluidTank = new FluidTank(4000) {
        @Override
        protected void onContentsChanged() {
            setChanged();
            ModMessages.sendToClients(new FluidSyncS2CPacket(this.fluid, getBlockPos()));
        }

        @Override
        public boolean isFluidValid(FluidStack fluidStack) {
            return fluidStack.getFluid().isSame(ModFluids.SOUL_LAVA_SOURCE.get());
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();

    private final int BUCKET_SLOT = 0;
    private final int INPUT_SLOT = 1;
    private final int OUTPUT_SLOT = 2;
    private final int soulLavaConsumed = 50; // How much soul lava is used per operation
    private final int itemSmelted = 8; // How many item get smelted per operation
    private int progress = 0;
    private int maxProgress = 100;
    private int litTime = 0;
    private int maxLitTime = maxProgress * itemSmelted;
    public RecipeType<? extends AbstractCookingRecipe> recipeType;
    public final ContainerData containerData = new ContainerData() {
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

    public SoulFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SOUL_FURNACE.get(), pos, state);
        this.recipeType = RecipeType.SMELTING;
    }

    private static ItemStack itemCheck = ItemStack.EMPTY;

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, SoulFurnaceBlockEntity furnace) {
        if (level.isClientSide) {
            return;
        }
        // For some reason half the time the input item is air, accounting for that
        if (!furnace.itemHandler.getStackInSlot(furnace.INPUT_SLOT).is(Items.AIR)) {
            // Check if the input item is the same as last tick
            if (furnace.itemHandler.getStackInSlot(furnace.INPUT_SLOT).getItem() != itemCheck.getItem()) {
                furnace.resetProgress();
            }
            itemCheck = furnace.itemHandler.getStackInSlot(furnace.INPUT_SLOT);
        }
        // Handle tank refill
        if (furnace.itemHandler.getStackInSlot(furnace.BUCKET_SLOT).is(ModItems.SOUL_LAVA_BUCKET.get()) &&
                canRefillFluidTank(furnace.fluidTank)) {
            refillFluidTank(furnace);
        }
        // Recipe handling
        if (hasValidRecipe(level, furnace)) {
            // Lit if furnace is not lit
            if (!isLit(furnace) && hasEnoughFuel(furnace)) {
                furnace.fluidTank.drain(furnace.soulLavaConsumed, IFluidHandler.FluidAction.EXECUTE);
                furnace.litTime = furnace.maxLitTime;
            }
            // If furnace is lit start smelting item
            if (isLit(furnace)) {
                furnace.progress++;
                // If progress is completed output smelted item
                if (furnace.progress >= furnace.maxProgress) {
                    smeltItem(level, furnace);
                    furnace.resetProgress();
                }
            } else {
                furnace.resetProgress();
            }

        } else {
            furnace.resetProgress();
        }

        level.setBlock(blockPos, blockState.setValue(SoulFurnaceBlock.LIT, isLit(furnace)), 2);
        if (isLit(furnace)) {
            furnace.litTime--;
        }
        setChanged(level, blockPos, blockState);
    }

    private static boolean hasValidRecipe(Level level, SoulFurnaceBlockEntity furnace) {
        if (furnace.itemHandler.getStackInSlot(furnace.INPUT_SLOT).isEmpty()) return false;
        if (furnace.itemHandler.getStackInSlot(furnace.OUTPUT_SLOT).getCount() >=
                furnace.itemHandler.getStackInSlot(furnace.OUTPUT_SLOT).getMaxStackSize()) return false;

        Recipe<?> recipe = level.getRecipeManager().getRecipeFor(RecipeType.SMELTING,
                new SimpleContainer(furnace.itemHandler.getStackInSlot(furnace.INPUT_SLOT)), level).orElse(null);
        if (recipe == null) return false;
        if (!furnace.itemHandler.getStackInSlot(furnace.OUTPUT_SLOT).isEmpty() &&
                recipe.getResultItem().getItem() != furnace.itemHandler.getStackInSlot(furnace.OUTPUT_SLOT).getItem()) return false;
        return true;
    }

    private static void smeltItem(Level level, SoulFurnaceBlockEntity furnace) {
        furnace.itemHandler.extractItem(furnace.INPUT_SLOT, 1, false);
        Recipe<?> recipe = level.getRecipeManager().getRecipeFor(RecipeType.SMELTING,
                new SimpleContainer(furnace.itemHandler.getStackInSlot(furnace.INPUT_SLOT)), level).orElse(null);
        if (recipe != null) {
            furnace.itemHandler.setStackInSlot(furnace.OUTPUT_SLOT, new ItemStack(recipe.getResultItem().getItem(),
                    furnace.itemHandler.getStackInSlot(furnace.OUTPUT_SLOT).getCount() + 1));
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private static boolean hasEnoughFuel(SoulFurnaceBlockEntity furnace) {
        return furnace.fluidTank.getFluidAmount() >= furnace.soulLavaConsumed;
    }

    private static boolean canRefillFluidTank(FluidTank fluidTank) {
        return fluidTank.getSpace() >= 1000;
    }

    private static void refillFluidTank(SoulFurnaceBlockEntity furnace) {
        furnace.itemHandler.setStackInSlot(furnace.BUCKET_SLOT, Items.BUCKET.getDefaultInstance());
        FluidStack fluidStack = new FluidStack(ModFluids.SOUL_LAVA_SOURCE.get(), 1000);
        furnace.fluidTank.fill(fluidStack, IFluidHandler.FluidAction.EXECUTE);
    }

    private static boolean isLit(SoulFurnaceBlockEntity furnace) {
        return furnace.litTime > 0;
    }

    private SimpleContainer getInventory() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        return inventory;
    }

    public void setFluid(FluidStack fluid) {
        this.fluidTank.setFluid(fluid);
    }

    public FluidStack getFluid() {
        return this.fluidTank.getFluid();
    }

    public int getMaxFluidCapacity() {
        return this.fluidTank.getCapacity();
    }

    @SuppressWarnings("ConstantConditions")
    public void dropItemsOnBreak() {
        SimpleContainer inventory = getInventory();
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    // Gui stuff
    @Override
    public Component getDisplayName() {
        return Component.translatable("telluriumsrandomstuff.blockentity.soul_furnace");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new SoulFurnaceMenu(pContainerId, pPlayerInventory, this, this.containerData);
    }

    // NBT stuff
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        } else if (cap == ForgeCapabilities.FLUID_HANDLER) {
            return lazyFluidHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        super.deserializeNBT(nbt);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyFluidHandler = LazyOptional.of(() -> fluidTank);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyFluidHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.put("inventory", itemHandler.serializeNBT());
        tag.putInt("soul_furnace.progress", this.progress);
        tag.putInt("soul_furnace.litTime", this.litTime);
        tag = fluidTank.writeToNBT(tag);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        itemHandler.deserializeNBT(tag.getCompound("inventory"));
        this.progress = tag.getInt("soul_furnace.progress");
        this.litTime = tag.getInt("soul_furnace.litTime");
        this.fluidTank.readFromNBT(tag);
    }

}
