package com.mikitellurium.telluriumsrandomstuff.registry;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(
            ForgeRegistries.PARTICLE_TYPES, TelluriumsRandomStuffMod.MOD_ID);

    public static final RegistryObject<SimpleParticleType> SOUL_LAVA_FALL =
            PARTICLE_TYPES.register("soul_lava_fall", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> SOUL_LAVA_HANG =
            PARTICLE_TYPES.register("soul_lava_hang", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> SOUL_LAVA_LAND =
            PARTICLE_TYPES.register("soul_lava_land", () -> new SimpleParticleType(true));

    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }

}
