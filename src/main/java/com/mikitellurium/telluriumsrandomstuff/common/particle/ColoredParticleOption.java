package com.mikitellurium.telluriumsrandomstuff.common.particle;

import com.mikitellurium.telluriumsrandomstuff.registry.ModParticles;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.DyeColor;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Locale;

public record ColoredParticleOption(DyeColor color) implements ParticleOptions {

    public static final Codec<ColoredParticleOption> CODEC = RecordCodecBuilder.create((instance) ->
            instance.group(DyeColor.CODEC.fieldOf("color").forGetter((option) -> option.color))
                    .apply(instance, ColoredParticleOption::new));

    public static final ParticleOptions.Deserializer<ColoredParticleOption> DESERIALIZER = new ParticleOptions.Deserializer<>() {
        public ColoredParticleOption fromCommand(ParticleType<ColoredParticleOption> type, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            int id = reader.readInt();
            return new ColoredParticleOption(DyeColor.byId(id));
        }

        public ColoredParticleOption fromNetwork(ParticleType<ColoredParticleOption> type, FriendlyByteBuf buf) {
            int id = buf.readInt();
            return new ColoredParticleOption(DyeColor.byId(id));
        }
    };

    @Override
    public ParticleType<?> getType() {
        return ModParticles.SPIRITED_ALLAY_SPAWN.get();
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf buf) {
        buf.writeInt(this.color.getId());
    }

    @Override
    public String writeToString() {
        String s = String.format(Locale.ROOT, "%s", this.color.getName());
        return ForgeRegistries.PARTICLE_TYPES.getKey(this.getType()) + " " + s;
    }

}
