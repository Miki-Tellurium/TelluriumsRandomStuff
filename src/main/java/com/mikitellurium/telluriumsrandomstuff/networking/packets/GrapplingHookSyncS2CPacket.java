package com.mikitellurium.telluriumsrandomstuff.networking.packets;

import com.mikitellurium.telluriumsrandomstuff.common.capability.GrapplingHookCapabilityProvider;
import com.mikitellurium.telluriumsrandomstuff.lib.ModPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class GrapplingHookSyncS2CPacket implements ModPacket {

        private final boolean isUsing;
        private final ItemStack itemStack;

        public GrapplingHookSyncS2CPacket(boolean isUsing, @Nullable ItemStack itemStack) {
            this.isUsing = isUsing;
            this.itemStack = itemStack;
        }

        public GrapplingHookSyncS2CPacket(FriendlyByteBuf buf) {
            this.isUsing = buf.readBoolean();
            this.itemStack = buf.readItem();
        }

        @Override
        public void write(FriendlyByteBuf buf) {
            buf.writeBoolean(this.isUsing);
            buf.writeItem(this.itemStack);
        }

        @Override
        public boolean handle(Supplier<NetworkEvent.Context> supplier) {
            NetworkEvent.Context context = supplier.get();
            context.enqueueWork(() -> {
                // Client
                Minecraft.getInstance().player.getCapability(GrapplingHookCapabilityProvider.INSTANCE).ifPresent((hook) -> {
                    hook.setUsing(this.isUsing);
                    hook.setStack(this.itemStack);
                });
            });

            return true;
        }

}
