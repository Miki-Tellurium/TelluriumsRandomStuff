package com.mikitellurium.telluriumsrandomstuff.common.potionmixing;

import com.mikitellurium.telluriumsrandomstuff.api.potionmixing.PotionMixingFunction;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.function.BiFunction;

public class IncreaseDurationFunction implements PotionMixingFunction {

    @Override
    public BiFunction<MobEffectInstance, MobEffectInstance, MobEffectInstance> mixPotion() {
        return (instance, instance1) -> {
            int firstDuration = instance.getDuration();
            int secondDuration = instance1.getDuration();
            int duration = Math.max(firstDuration, secondDuration) + (Math.min(firstDuration, secondDuration) / 2);
            int amplifier = Math.max(instance.getAmplifier(), instance1.getAmplifier());
            return new MobEffectInstance(instance.getEffect(), duration, amplifier);
        };
    }

}
