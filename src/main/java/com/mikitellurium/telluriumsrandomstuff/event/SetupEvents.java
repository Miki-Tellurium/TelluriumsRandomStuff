package com.mikitellurium.telluriumsrandomstuff.event;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.particle.ModParticles;
import com.mikitellurium.telluriumsrandomstuff.particle.custom.SoulLavaDripParticle;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TelluriumsRandomStuffMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SetupEvents {

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.register(ModParticles.SOUL_LAVA_FALL.get(), SoulLavaDripParticle.SoulLavaFallProvider::new);
        event.register(ModParticles.SOUL_LAVA_HANG.get(), SoulLavaDripParticle.SoulLavaHangProvider::new);
        event.register(ModParticles.SOUL_LAVA_LAND.get(), SoulLavaDripParticle.SoulLavaLandProvider::new);
        event.register(ModParticles.DRIPSTONE_SOUL_LAVA_FALL.get(), SoulLavaDripParticle.DripstoneLavaFallProvider::new);
        event.register(ModParticles.DRIPSTONE_SOUL_LAVA_HANG.get(), SoulLavaDripParticle.DripstoneLavaHangProvider::new);
    }

}
