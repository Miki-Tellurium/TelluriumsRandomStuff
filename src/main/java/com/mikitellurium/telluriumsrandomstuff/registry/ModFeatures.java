package com.mikitellurium.telluriumsrandomstuff.registry;

import com.mikitellurium.telluriumsrandomstuff.common.worldgen.feature.BlueGlowstoneFeature;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModFeatures {


    public static final RegistryObject<Feature<NoneFeatureConfiguration>> BLUE_GLOWSTONE_BLOB = registerFeature("blue_glowstone_blob",
            () -> new BlueGlowstoneFeature(NoneFeatureConfiguration.CODEC));

    private static <C extends FeatureConfiguration, T extends Feature<C>> RegistryObject<T> registerFeature(String name, Supplier<T> feature) {
        return ModRegistries.FEATURES.register(name, feature);
    }

    protected static void register(IEventBus eventBus) {
        ModRegistries.FEATURES.register(eventBus);
    }

}
