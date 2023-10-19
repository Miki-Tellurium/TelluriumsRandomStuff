package com.mikitellurium.telluriumsrandomstuff.common.networking.packets;

import com.mikitellurium.telluriumsrandomstuff.common.blockentity.ItemPedestalBlockEntity;
import com.mikitellurium.telluriumsrandomstuff.common.blockentity.SoulInfuserBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class BlockEntityS2CPacket {
        private final BlockPos firstPos;
        private final BlockPos secondPos;

        public BlockEntityS2CPacket(BlockPos firstPos, BlockPos secondPos) {
            this.firstPos = firstPos;
            this.secondPos = secondPos;
        }

        public BlockEntityS2CPacket(FriendlyByteBuf buf) {
            this.firstPos = buf.readBlockPos();
            this.secondPos = buf.readBlockPos();
        }

        public void toBytes(FriendlyByteBuf buf) {
            buf.writeBlockPos(this.firstPos);
            buf.writeBlockPos(this.secondPos);
        }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Client
            ClientLevel level = Minecraft.getInstance().level;
            if (level.getBlockEntity(firstPos) instanceof SoulInfuserBlockEntity soulInfuser) {
                if (level.getBlockEntity(secondPos) instanceof ItemPedestalBlockEntity itemPedestal) {
                    soulInfuser.setItemPedestal(itemPedestal);
                }
            }
        });

        return true;
    }

}
