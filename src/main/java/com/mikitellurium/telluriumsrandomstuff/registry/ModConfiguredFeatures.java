package com.mikitellurium.telluriumsrandomstuff.registry;

import com.mikitellurium.telluriumsrandomstuff.common.worldgen.feature.OpalClusterConfiguration;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_OPAL_CLUSTER = registerKey("opal_cluster");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NETHER_BLUE_GLOWSTONE = registerKey("blue_glowstone");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        context.register(OVERWORLD_OPAL_CLUSTER, new ConfiguredFeature<>(ModFeatures.OPAL_CLUSTER.get(),
                new OpalClusterConfiguration(64, new TagMatchTest(BlockTags.BASE_STONE_OVERWORLD))));
        context.register(NETHER_BLUE_GLOWSTONE, new ConfiguredFeature<>(ModFeatures.BLUE_GLOWSTONE_BLOB.get(), FeatureConfiguration.NONE));
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, FastLoc.modLoc(name));
    }
}
