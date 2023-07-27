package com.mikitellurium.telluriumsrandomstuff.common.content.worldgen;


import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.registry.ModFeatures;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ModConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_OPAL_CLUSTER =
            registerKey("opal_cluster");

    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_OPAL_CRYSTAL_ORE_SMALL =
            registerKey("opal_crystal_ore_small");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_OPAL_CRYSTAL_ORE_LARGE =
            registerKey("opal_crystal_ore_large");

    public static final ResourceKey<ConfiguredFeature<?, ?>> NETHER_BLUE_GLOWSTONE =
            registerKey("blue_glowstone");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceables = new TagMatchTest(BlockTags.BASE_STONE_OVERWORLD);
        RuleTest opalClusterReplaceable = new BlockMatchTest(ModBlocks.OPAL.get());

        List<OreConfiguration.TargetBlockState> overworldOpalCluster = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.OPAL.get().defaultBlockState()));
        List<OreConfiguration.TargetBlockState> overworldOpalCrystalOre = List.of(
                OreConfiguration.target(opalClusterReplaceable, ModBlocks.OPAL_CRYSTAL_ORE.get().defaultBlockState()));

        register(context, OVERWORLD_OPAL_CLUSTER, Feature.ORE, new OreConfiguration(overworldOpalCluster,64));
        register(context, OVERWORLD_OPAL_CRYSTAL_ORE_SMALL, Feature.ORE, new OreConfiguration(overworldOpalCrystalOre,4));
        register(context, OVERWORLD_OPAL_CRYSTAL_ORE_LARGE, Feature.ORE, new OreConfiguration(overworldOpalCrystalOre,8));
        register(context, NETHER_BLUE_GLOWSTONE, ModFeatures.BLUE_GLOWSTONE_BLOB.get(), FeatureConfiguration.NONE);
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(TelluriumsRandomStuffMod.MOD_ID, name));
    }

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
                                                                                         ResourceKey<ConfiguredFeature<?, ?>> key,
                                                                                         F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
