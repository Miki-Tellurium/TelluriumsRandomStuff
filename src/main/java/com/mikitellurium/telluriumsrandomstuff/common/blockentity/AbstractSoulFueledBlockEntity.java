package com.mikitellurium.telluriumsrandomstuff.common.blockentity;

import com.mikitellurium.telluriumsrandomstuff.common.networking.ModMessages;
import com.mikitellurium.telluriumsrandomstuff.common.networking.packets.FluidSyncS2CPacket;
import com.mikitellurium.telluriumsrandomstuff.registry.ModFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractSoulFueledBlockEntity extends BlockEntity {

    private final Fluid SOUL_LAVA = ModFluids.SOUL_LAVA_SOURCE.get();

    private final FluidTank fluidTank = new FluidTank(0) {
        @Override
        protected void onContentsChanged() {
            setChanged();
            ModMessages.sendToClients(new FluidSyncS2CPacket(this.fluid, getBlockPos()));
        }

        @Override
        public boolean isFluidValid(FluidStack fluidStack) {
            return fluidStack.getFluid().isSame(SOUL_LAVA);
        }
    };
    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();

    public AbstractSoulFueledBlockEntity(BlockEntityType<? extends AbstractSoulFueledBlockEntity> entityType, BlockPos pos,
                                         BlockState blockState, int tankCapacity) {
        super(entityType, pos, blockState);
        this.fluidTank.setCapacity(tankCapacity);
    }

    public void handleTankRefill(ItemStackHandler itemHandler, int bucketSlot) {
        if (itemHandler.getStackInSlot(bucketSlot).is(SOUL_LAVA.getBucket()) &&
                this.canRefillFluidTank()) {
            this.refillFluidTank(itemHandler, bucketSlot);
        }
    }

    private boolean canRefillFluidTank() {
        return this.fluidTank.getSpace() >= 1000;
    }

    private void refillFluidTank(ItemStackHandler itemHandler, int bucketSlot) {
        itemHandler.setStackInSlot(bucketSlot, Items.BUCKET.getDefaultInstance());
        this.fillTank(1000);
    }

    public FluidTank getFluidTank() {
        return fluidTank;
    }

    public FluidStack getFluid() {
        return this.fluidTank.getFluid();
    }

    public int getFluidTankCapacity() {
        return this.fluidTank.getCapacity();
    }

    public void setFluid(FluidStack fluid) {
        this.fluidTank.setFluid(fluid);
    }

    public void fillTank(int amount) {
        this.fluidTank.fill(new FluidStack(SOUL_LAVA, amount), IFluidHandler.FluidAction.EXECUTE);
    }

    public void drainTank(int amount) {
        this.fluidTank.drain(new FluidStack(SOUL_LAVA, amount), IFluidHandler.FluidAction.EXECUTE);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction side) {
        if (capability == ForgeCapabilities.FLUID_HANDLER) {
            return lazyFluidHandler.cast();
        }

        return super.getCapability(capability, side);
    }

    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyFluidHandler = LazyOptional.of(() -> fluidTank);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyFluidHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag = this.fluidTank.writeToNBT(tag);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.fluidTank.readFromNBT(tag);
    }

}
