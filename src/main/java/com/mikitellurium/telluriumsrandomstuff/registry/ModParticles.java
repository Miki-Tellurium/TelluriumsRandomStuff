package com.mikitellurium.telluriumsrandomstuff.registry;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModParticles {

    public static final RegistryObject<SimpleParticleType> SOUL_LAVA_FALL =
            registerParticle("soul_lava_fall", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> SOUL_LAVA_HANG =
            registerParticle("soul_lava_hang", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> SOUL_LAVA_LAND =
            registerParticle("soul_lava_land", () -> new SimpleParticleType(true));

    private static <T extends ParticleType<?>> RegistryObject<T> registerParticle(String name, Supplier<T> particle) {
        return ModRegistries.PARTICLE_TYPES.register(name, particle);
    }

    protected static void register(IEventBus eventBus) {
        ModRegistries.PARTICLE_TYPES.register(eventBus);
    }

}
