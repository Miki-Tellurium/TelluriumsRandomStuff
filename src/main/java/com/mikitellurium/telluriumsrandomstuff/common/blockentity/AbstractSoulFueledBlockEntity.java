package com.mikitellurium.telluriumsrandomstuff.common.blockentity;

import com.mikitellurium.telluriumsrandomstuff.networking.ModMessages;
import com.mikitellurium.telluriumsrandomstuff.networking.packets.FluidSyncS2CPacket;
import com.mikitellurium.telluriumsrandomstuff.registry.ModFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AbstractSoulFueledBlockEntity extends BlockEntity {

    private final Fluid soulLava = ModFluids.SOUL_LAVA_SOURCE.get();

    private final FluidTank fluidTank = new FluidTank(0) {
        @Override
        protected void onContentsChanged() {
            setChanged();
            ModMessages.sendToClients(new FluidSyncS2CPacket(this.fluid, getBlockPos()));
        }

        @Override
        public boolean isFluidValid(FluidStack fluidStack) {
            return fluidStack.getFluid().isSame(AbstractSoulFueledBlockEntity.this.soulLava);
        }
    };
    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();

    public AbstractSoulFueledBlockEntity(BlockEntityType<? extends AbstractSoulFueledBlockEntity> entityType, BlockPos pos,
                                         BlockState blockState, int tankCapacity) {
        super(entityType, pos, blockState);
        this.fluidTank.setCapacity(tankCapacity);
    }

    public boolean isFluidHandlerValid(ItemStack itemStack) {
        LazyOptional<IFluidHandlerItem> optional = FluidUtil.getFluidHandler(itemStack);
        if (!optional.isPresent()) return false;
        AtomicBoolean isValid = new AtomicBoolean();
        optional.ifPresent((handler) -> {
            for (int i = 0; i < handler.getTanks(); i++) {
                FluidStack fluidStack = handler.getFluidInTank(i);
                if (fluidStack.getFluid() == this.soulLava) {
                    isValid.set(true);
                    break;
                }
            }
        });
        return isValid.get();
    }

    public boolean canRefillFluidTank(int amount) {
        return this.fluidTank.getSpace() >= amount;
    }

    public FluidTank getFluidTank() {
        return this.fluidTank;
    }

    public FluidStack getFluidStack() {
        return this.fluidTank.getFluid();
    }

    public int getFluidTankCapacity() {
        return this.fluidTank.getCapacity();
    }

    public void setFluidStack(FluidStack fluid) {
        this.fluidTank.setFluid(fluid);
    }

    public void fillTank(int amount) {
        this.fluidTank.fill(new FluidStack(this.soulLava, amount), IFluidHandler.FluidAction.EXECUTE);
    }

    public void drainTank(int amount) {
        this.fluidTank.drain(new FluidStack(this.soulLava, amount), IFluidHandler.FluidAction.EXECUTE);
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
