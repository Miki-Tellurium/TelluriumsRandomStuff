package com.mikitellurium.telluriumsrandomstuff.networking.packets;

import com.mikitellurium.telluriumsrandomstuff.common.blockentity.ItemPedestalBlockEntity;
import com.mikitellurium.telluriumsrandomstuff.lib.ModPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class RotOffsetSyncS2CPacket implements ModPacket {
        private final float f;
        private final BlockPos pos;

        public RotOffsetSyncS2CPacket(float f, BlockPos pos) {
            this.f = f;
            this.pos = pos;
        }

        public RotOffsetSyncS2CPacket(FriendlyByteBuf buf) {
            this.f = buf.readFloat();
            this.pos = buf.readBlockPos();
        }

        @Override
        public void write(FriendlyByteBuf buf) {
            buf.writeFloat(f);
            buf.writeBlockPos(pos);
        }

        @Override
        public boolean handle(Supplier<NetworkEvent.Context> supplier) {
            NetworkEvent.Context context = supplier.get();
            context.enqueueWork(() -> {
                // Client
                if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof ItemPedestalBlockEntity itemPedestal) {
                    itemPedestal.setRotOffset(this.f);
                }
            });

            return true;
        }

}
