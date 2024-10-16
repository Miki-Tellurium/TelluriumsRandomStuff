package com.mikitellurium.telluriumsrandomstuff.networking.packets;

import com.mikitellurium.telluriumsrandomstuff.common.blockentity.ItemPedestalBlockEntity;
import com.mikitellurium.telluriumsrandomstuff.lib.ModPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class DisplayNameSyncS2CPacket implements ModPacket {
        private final boolean displayName;
        private final BlockPos pos;

        public DisplayNameSyncS2CPacket(boolean displayName, BlockPos pos) {
            this.displayName = displayName;
            this.pos = pos;
        }

        public DisplayNameSyncS2CPacket(FriendlyByteBuf buf) {
            this.displayName = buf.readBoolean();
            this.pos = buf.readBlockPos();
        }

        @Override
        public void write(FriendlyByteBuf buf) {
            buf.writeBoolean(displayName);
            buf.writeBlockPos(pos);
        }

        @Override
        public boolean handle(Supplier<NetworkEvent.Context> supplier) {
            NetworkEvent.Context context = supplier.get();
            context.enqueueWork(() -> {
                // Client
                if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof ItemPedestalBlockEntity itemPedestal) {
                    itemPedestal.setAlwaysDisplayName(displayName);
                }
            });

            return true;
        }

}
