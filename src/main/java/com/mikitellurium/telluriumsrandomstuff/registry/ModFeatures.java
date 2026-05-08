package com.mikitellurium.telluriumsrandomstuff.registry;

import com.mikitellurium.telluriumsrandomstuff.common.worldgen.feature.BlueGlowstoneFeature;
import com.mikitellurium.telluriumsrandomstuff.common.worldgen.feature.OpalClusterConfiguration;
import com.mikitellurium.telluriumsrandomstuff.common.worldgen.feature.OpalClusterFeature;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModFeatures {


    public static final RegistryObject<Feature<NoneFeatureConfiguration>> BLUE_GLOWSTONE_BLOB = registerFeature("blue_glowstone_blob",
            () -> new BlueGlowstoneFeature(NoneFeatureConfiguration.CODEC));

    public static final RegistryObject<Feature<OpalClusterConfiguration>> OPAL_CLUSTER = registerFeature("opal_cluster",
            () -> new OpalClusterFeature(OpalClusterConfiguration.CODEC));

    private static <C extends FeatureConfiguration, T extends Feature<C>> RegistryObject<T> registerFeature(String name, Supplier<T> feature) {
        return ModRegistries.FEATURES.register(name, feature);
    }

    protected static void register(IEventBus eventBus) {
        ModRegistries.FEATURES.register(eventBus);
    }

}
