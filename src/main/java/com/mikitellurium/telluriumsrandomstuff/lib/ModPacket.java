package com.mikitellurium.telluriumsrandomstuff.lib;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public interface ModPacket {

    void write(FriendlyByteBuf buf);

    boolean handle(Supplier<NetworkEvent.Context> supplier);

}
