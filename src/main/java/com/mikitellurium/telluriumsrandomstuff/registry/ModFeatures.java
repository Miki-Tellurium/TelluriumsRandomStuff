package com.mikitellurium.telluriumsrandomstuff.registry;

import com.mikitellurium.telluriumsrandomstuff.common.worldgen.feature.BlueGlowstoneFeature;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFeatures {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, FastLoc.modId());

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> BLUE_GLOWSTONE_BLOB = FEATURES.register("blue_glowstone_blob",
            () -> new BlueGlowstoneFeature(NoneFeatureConfiguration.CODEC));

    public static void register(IEventBus eventBus) {
        FEATURES.register(eventBus);
    }

}
