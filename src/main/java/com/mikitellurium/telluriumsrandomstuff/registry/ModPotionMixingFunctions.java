package com.mikitellurium.telluriumsrandomstuff.registry;

import com.mikitellurium.telluriumsrandomstuff.api.potionmixing.PotionMixingFunction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModPotionMixingFunctions {



    private static <T extends PotionMixingFunction> RegistryObject<T> registerFunction(String name, Supplier<T> function) {
        return ModRegistries.POTION_MIXING_FUNCTIONS.register(name, function);
    }

    protected static void register(IEventBus eventBus) {
        ModRegistries.POTION_MIXING_FUNCTIONS.register(eventBus);
    }

}
