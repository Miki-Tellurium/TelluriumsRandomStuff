package com.mikitellurium.telluriumsrandomstuff.networking.packets;

import com.mikitellurium.telluriumsrandomstuff.common.particle.ColoredParticleOption;
import com.mikitellurium.telluriumsrandomstuff.lib.ModPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SpiritedAllaySpawnParticlePacket implements ModPacket {

    private final double xPos;
    private final double yPos;
    private final double zPos;
    private final DyeColor color;

    public SpiritedAllaySpawnParticlePacket(Vec3 pos, DyeColor color) {
        this.xPos = pos.x;
        this.yPos = pos.y;
        this.zPos = pos.z;
        this.color = color;
    }

    public SpiritedAllaySpawnParticlePacket(FriendlyByteBuf buf) {
        this.xPos = buf.readDouble();
        this.yPos = buf.readDouble();
        this.zPos = buf.readDouble();
        this.color = buf.readEnum(DyeColor.class);
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeDouble(this.xPos);
        buf.writeDouble(this.yPos);
        buf.writeDouble(this.zPos);
        buf.writeEnum(this.color);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Client
            PacketListener listener = context.getNetworkManager().getPacketListener();
            if (listener instanceof ClientGamePacketListener) {
                ClientLevel level = Minecraft.getInstance().level;
                RandomSource random = level.random;
                int amount = random.nextInt(10, 15);
                for (int i = 0; i < amount; i++) {
                    double x = random.triangle(-0.25, 0.25D);
                    double y = random.triangle(-0.25, 0.25D);
                    double z = random.triangle(-0.25, 0.25D);
                    double xSpeed = random.nextGaussian() * 0.1D;
                    double ySpeed = random.nextGaussian() * 0.1D;
                    double zSpeed = random.nextGaussian() * 0.1D;
                    level.addParticle(new ColoredParticleOption(color), xPos + x, yPos + y, zPos + z, xSpeed, ySpeed, zSpeed);
                }
            }
        });
        return true;
    }

}
