package com.mikitellurium.telluriumsrandomstuff.worldgen;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.util.OrePlacementUtils;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ModPlacedFeatures {

    public static final ResourceKey<PlacedFeature> OPAL_CLUSTER_PLACED = createKey("opal_cluster_placed");

    public static final ResourceKey<PlacedFeature> OPALIUM_ORE_SMALL_PLACED = createKey("opalium_ore_small_placed");
    public static final ResourceKey<PlacedFeature> OPALIUM_ORE_LARGE_PLACED = createKey("opalium_ore_large_placed");

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, OPAL_CLUSTER_PLACED, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_OPAL_CLUSTER),
                OrePlacementUtils.rareOrePlacement(10, HeightRangePlacement.uniform(
                        VerticalAnchor.absolute(-60), VerticalAnchor.absolute(128))));

        register(context, OPALIUM_ORE_SMALL_PLACED, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_OPALIUM_ORE_SMALL),
                OrePlacementUtils.commonOrePlacement(15, HeightRangePlacement.uniform(
                        VerticalAnchor.absolute(-60), VerticalAnchor.absolute(128))));
        register(context, OPALIUM_ORE_LARGE_PLACED, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_OPALIUM_ORE_LARGE),
                OrePlacementUtils.commonOrePlacement(15, HeightRangePlacement.uniform(
                        VerticalAnchor.absolute(-60), VerticalAnchor.absolute(128))));
    }

    private static ResourceKey<PlacedFeature> createKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(TelluriumsRandomStuffMod.MOD_ID, name));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 PlacementModifier... modifiers) {
        register(context, key, configuration, List.of(modifiers));
    }

}
