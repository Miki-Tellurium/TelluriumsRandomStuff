package com.mikitellurium.telluriumsrandomstuff.networking.packets;

import com.mikitellurium.telluriumsrandomstuff.common.blockentity.ItemPedestalBlockEntity;
import com.mikitellurium.telluriumsrandomstuff.lib.ModPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PedestalItemSyncS2CPacket implements ModPacket {
        private final ItemStack itemStack;
        private final BlockPos pos;

        public PedestalItemSyncS2CPacket(ItemStack itemHandler, BlockPos pos) {
            this.itemStack = itemHandler;
            this.pos = pos;
        }

        public PedestalItemSyncS2CPacket(FriendlyByteBuf buf) {
            this.itemStack = buf.readItem();
            this.pos = buf.readBlockPos();
        }

        @Override
        public void write(FriendlyByteBuf buf) {
            buf.writeItem(itemStack);
            buf.writeBlockPos(pos);
        }

        @Override
        public boolean handle(Supplier<NetworkEvent.Context> supplier) {
            NetworkEvent.Context context = supplier.get();
            context.enqueueWork(() -> {
                // Client
                if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof ItemPedestalBlockEntity itemPedestal) {
                    itemPedestal.setItem(itemStack);
                }
            });

            return true;
        }

}
