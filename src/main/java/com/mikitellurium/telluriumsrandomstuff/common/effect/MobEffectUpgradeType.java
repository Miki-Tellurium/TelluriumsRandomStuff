package com.mikitellurium.telluriumsrandomstuff.common.effect;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.common.IExtensibleEnum;

import java.util.function.BiFunction;

public enum MobEffectUpgradeType implements IExtensibleEnum {
    /* Increase the duration of the potion effect */
    DURATION((firstInstance, secondInstance) -> {
        int firstDuration = firstInstance.getDuration();
        int secondDuration = secondInstance.getDuration();
        int duration = Math.max(firstDuration, secondDuration) + Math.min(firstDuration, secondDuration) / 2;
        return new MobEffectInstance(firstInstance.getEffect(), duration,
                Math.max(firstInstance.getAmplifier(), secondInstance.getAmplifier()));
    }),
    /* Increase the amplifier of the effect */
    AMPLIFIER((firstInstance, secondInstance) -> {
        int amplifier = firstInstance.getAmplifier() == secondInstance.getAmplifier() ?
                firstInstance.getAmplifier() + 1 : Math.max(firstInstance.getAmplifier(), secondInstance.getAmplifier());
        return new MobEffectInstance(firstInstance.getEffect(),
                Math.min(firstInstance.getDuration(), secondInstance.getDuration()), amplifier);
    });

    private final BiFunction<MobEffectInstance, MobEffectInstance, MobEffectInstance> getMixedInstance;

    MobEffectUpgradeType(BiFunction<MobEffectInstance, MobEffectInstance, MobEffectInstance> getMixedInstance) {
        this.getMixedInstance = getMixedInstance;
    }

    public MobEffectInstance getUpgradedInstance(MobEffectInstance firstInstance, MobEffectInstance secondInstance) {
        return this.getMixedInstance.apply(firstInstance, secondInstance);
    }

    public static MobEffectUpgradeType create(String name, BiFunction<MobEffectInstance, MobEffectInstance, MobEffectInstance> getMixedInstance) {
        throw new IllegalArgumentException("Enum not extended");
    }

}
