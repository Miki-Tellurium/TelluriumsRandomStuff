package com.mikitellurium.telluriumsrandomstuff.networking.packets;

import com.mikitellurium.telluriumsrandomstuff.client.gui.menu.AbstractSoulFuelMenu;
import com.mikitellurium.telluriumsrandomstuff.common.blockentity.AbstractSoulFueledBlockEntity;
import com.mikitellurium.telluriumsrandomstuff.lib.ModPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class FluidSyncS2CPacket implements ModPacket {
        private final FluidStack fluidStack;
        private final BlockPos pos;

        public FluidSyncS2CPacket(FluidStack fluidStack, BlockPos pos) {
            this.fluidStack = fluidStack;
            this.pos = pos;
        }

        public FluidSyncS2CPacket(FriendlyByteBuf buf) {
            this.fluidStack = buf.readFluidStack();
            this.pos = buf.readBlockPos();
        }

        @Override
        public void write(FriendlyByteBuf buf) {
            buf.writeFluidStack(fluidStack);
            buf.writeBlockPos(pos);
        }

        @Override
        public boolean handle(Supplier<NetworkEvent.Context> supplier) {
            NetworkEvent.Context context = supplier.get();
            context.enqueueWork(() -> {
                // Client
                if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof AbstractSoulFueledBlockEntity blockEntity) {
                    blockEntity.setFluidStack(this.fluidStack);

                    if(Minecraft.getInstance().player.containerMenu instanceof AbstractSoulFuelMenu menu &&
                            menu.getBlockEntity().getBlockPos().equals(pos)) {
                        menu.setFluid(this.fluidStack);
                    }
                }
            });

            return true;
        }

}
