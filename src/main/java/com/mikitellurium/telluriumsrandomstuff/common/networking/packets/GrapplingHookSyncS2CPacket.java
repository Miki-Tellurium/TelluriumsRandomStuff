package com.mikitellurium.telluriumsrandomstuff.common.networking.packets;

import com.mikitellurium.telluriumsrandomstuff.common.capability.GrapplingHookCapabilityProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.level.NoteBlockEvent;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class GrapplingHookSyncS2CPacket {
        private final boolean b;

        public GrapplingHookSyncS2CPacket(boolean b) {
            this.b = b;
        }

        public GrapplingHookSyncS2CPacket(FriendlyByteBuf buf) {
            this.b = buf.readBoolean();
        }

        public void toBytes(FriendlyByteBuf buf) {
            buf.writeBoolean(b);
        }

        public boolean handle(Supplier<NetworkEvent.Context> supplier) {
            NetworkEvent.Context context = supplier.get();
            context.enqueueWork(() -> {
                // Client
                Minecraft.getInstance().player.getCapability(GrapplingHookCapabilityProvider.INSTANCE).ifPresent((hook) -> {
                    hook.setUsing(b);
                });
            });

            return true;
        }

}
