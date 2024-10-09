package com.mikitellurium.telluriumsrandomstuff.networking.packets;

import com.mikitellurium.telluriumsrandomstuff.client.hud.menu.SoulAssemblyMenu;
import com.mikitellurium.telluriumsrandomstuff.lib.ModPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.PacketListener;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SoulAssemblyModeC2SPacket implements ModPacket {

        private final SoulAssemblyMenu.Mode mode;

        public SoulAssemblyModeC2SPacket(SoulAssemblyMenu.Mode mode) {
            this.mode = mode;
        }

        public SoulAssemblyModeC2SPacket(FriendlyByteBuf buf) {
            this.mode = buf.readEnum(SoulAssemblyMenu.Mode.class);
        }

        @Override
        public void write(FriendlyByteBuf buf) {
            buf.writeEnum(this.mode);
        }

        @Override
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
