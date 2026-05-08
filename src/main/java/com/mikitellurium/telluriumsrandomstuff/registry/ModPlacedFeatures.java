package com.mikitellurium.telluriumsrandomstuff.registry;

import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.BiasedToBottomInt;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> OPAL_CLUSTER_PLACED = createKey("opal_cluster_placed");
    public static final ResourceKey<PlacedFeature> BLUE_GLOWSTONE_PLACED = createKey("blue_glowstone_placed");

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> lookup = context.lookup(Registries.CONFIGURED_FEATURE);
        context.register(OPAL_CLUSTER_PLACED,
                new PlacedFeature(lookup.getOrThrow(ModConfiguredFeatures.OVERWORLD_OPAL_CLUSTER),
                        List.of(
                                RarityFilter.onAverageOnceEvery(20),
                                InSquarePlacement.spread(),
                                HeightRangePlacement.uniform(VerticalAnchor.absolute(-60), VerticalAnchor.absolute(50)),
                                BiomeFilter.biome()
                        )));
        context.register(BLUE_GLOWSTONE_PLACED,
                new PlacedFeature(lookup.getOrThrow(ModConfiguredFeatures.NETHER_BLUE_GLOWSTONE),
                        List.of(
                                CountPlacement.of(BiasedToBottomInt.of(0, 7)),
                                InSquarePlacement.spread(),
                                PlacementUtils.RANGE_4_4,
                                BiomeFilter.biome()
                        )));
    }

    private static ResourceKey<PlacedFeature> createKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, FastLoc.modLoc(name));
    }
}
