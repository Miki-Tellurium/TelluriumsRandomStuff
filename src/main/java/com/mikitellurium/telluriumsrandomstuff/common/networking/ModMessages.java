package com.mikitellurium.telluriumsrandomstuff.common.networking;

import com.mikitellurium.telluriumsrandomstuff.common.networking.packets.*;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModMessages {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(FastLoc.modLoc("messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(FluidSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(FluidSyncS2CPacket::new)
                .encoder(FluidSyncS2CPacket::toBytes)
                .consumerMainThread(FluidSyncS2CPacket::handle)
                .add();
        net.messageBuilder(ItemStackSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ItemStackSyncS2CPacket::new)
                .encoder(ItemStackSyncS2CPacket::toBytes)
                .consumerMainThread(ItemStackSyncS2CPacket::handle)
                .add();
        net.messageBuilder(RotOffsetSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(RotOffsetSyncS2CPacket::new)
                .encoder(RotOffsetSyncS2CPacket::toBytes)
                .consumerMainThread(RotOffsetSyncS2CPacket::handle)
                .add();
        net.messageBuilder(DisplayNameSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(DisplayNameSyncS2CPacket::new)
                .encoder(DisplayNameSyncS2CPacket::toBytes)
                .consumerMainThread(DisplayNameSyncS2CPacket::handle)
                .add();
        net.messageBuilder(GrapplingHookSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(GrapplingHookSyncS2CPacket::new)
                .encoder(GrapplingHookSyncS2CPacket::toBytes)
                .consumerMainThread(GrapplingHookSyncS2CPacket::handle)
                .add();
    }
    
    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <MSG> void sendToClients(MSG message) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }

}
