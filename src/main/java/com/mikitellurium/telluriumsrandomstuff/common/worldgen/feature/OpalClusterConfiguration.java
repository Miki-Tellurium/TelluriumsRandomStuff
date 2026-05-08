package com.mikitellurium.telluriumsrandomstuff.common.worldgen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;

public record OpalClusterConfiguration(int size, RuleTest target) implements FeatureConfiguration {
    public static final Codec<OpalClusterConfiguration> CODEC = RecordCodecBuilder.create((instance) ->
            instance.group(Codec.intRange(0, 64).fieldOf("size").forGetter(OpalClusterConfiguration::size),
                            RuleTest.CODEC.fieldOf("target").forGetter(OpalClusterConfiguration::target))
                    .apply(instance, OpalClusterConfiguration::new));
}
