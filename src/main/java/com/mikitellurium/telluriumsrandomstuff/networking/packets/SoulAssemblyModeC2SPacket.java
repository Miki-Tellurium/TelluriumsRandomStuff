package com.mikitellurium.telluriumsrandomstuff.networking.packets;

import com.mikitellurium.telluriumsrandomstuff.client.gui.menu.SoulAssemblyMenu;
import com.mikitellurium.telluriumsrandomstuff.common.blockentity.ItemPedestalBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.PacketListener;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SoulAssemblyModeC2SPacket {

        private final SoulAssemblyMenu.Mode mode;

        public SoulAssemblyModeC2SPacket(SoulAssemblyMenu.Mode mode) {
            this.mode = mode;
        }

        public SoulAssemblyModeC2SPacket(FriendlyByteBuf buf) {
            this.mode = buf.readEnum(SoulAssemblyMenu.Mode.class);
        }

        public void toBytes(FriendlyByteBuf buf) {
            buf.writeEnum(this.mode);
        }

        public boolean handle(Supplier<NetworkEvent.Context> supplier) {
            NetworkEvent.Context context = supplier.get();
            context.enqueueWork(() -> {
                // Server
                PacketListener listener = context.getNetworkManager().getPacketListener();
                if (listener instanceof ServerGamePacketListenerImpl server) {
                    if (server.player.containerMenu instanceof SoulAssemblyMenu menu) {
                        menu.setMode(this.mode);
                    }
                }
            });

            return true;
        }

}
