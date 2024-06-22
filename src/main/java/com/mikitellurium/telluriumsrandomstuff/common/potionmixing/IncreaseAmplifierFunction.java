package com.mikitellurium.telluriumsrandomstuff.common.potionmixing;

import com.mikitellurium.telluriumsrandomstuff.api.potionmixing.PotionMixingFunction;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.function.BiFunction;

public class IncreaseAmplifierFunction implements PotionMixingFunction {

    @Override
    public BiFunction<MobEffectInstance, MobEffectInstance, MobEffectInstance> mixPotion() {
        return (instance, instance1) -> {
            int amplifier = instance.getAmplifier() == instance1.getAmplifier() ?
                    instance.getAmplifier() + 1 : Math.max(instance.getAmplifier(), instance1.getAmplifier());
            int duration = Math.min(instance.getDuration(), instance1.getDuration());
            return new MobEffectInstance(instance.getEffect(), duration, amplifier);
        };
    }

}
