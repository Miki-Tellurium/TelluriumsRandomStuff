package com.mikitellurium.telluriumsrandomstuff.api.potionmixing;

import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Supplier;

public class CustomRegistries {

    public static final DeferredRegister<PotionMixingFunction> DEFERRED_POTION_MIXING_FUNCTIONS = DeferredRegister.create(Keys.POTION_MIXING, FastLoc.modId());
    public static final Supplier<IForgeRegistry<PotionMixingFunction>> POTION_MIXING_FUNCTIONS = DEFERRED_POTION_MIXING_FUNCTIONS.makeRegistry(() ->
            new RegistryBuilder<PotionMixingFunction>().setName(Keys.POTION_MIXING.location()).setMaxID(Integer.MAX_VALUE - 1).disableSaving().disableSync());

    public static void init(IEventBus eventBus) {
        DEFERRED_POTION_MIXING_FUNCTIONS.register(eventBus);
    }

    public static class Keys {
        public static final ResourceKey<Registry<PotionMixingFunction>> POTION_MIXING =
                ResourceKey.createRegistryKey(FastLoc.modLoc("potion_mixing_function"));
    }

}
