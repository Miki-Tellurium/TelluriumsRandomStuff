package com.mikitellurium.telluriumsrandomstuff.common.networking.packets;

import com.mikitellurium.telluriumsrandomstuff.common.blockentity.ItemPedestalBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class RotOffsetSyncS2CPacket {
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

        public void toBytes(FriendlyByteBuf buf) {
            buf.writeFloat(f);
            buf.writeBlockPos(pos);
        }

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
