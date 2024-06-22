package com.mikitellurium.telluriumsrandomstuff.api.potionmixing;

import net.minecraft.world.effect.MobEffectInstance;

import java.util.function.BiFunction;

public interface PotionMixingFunction {

    BiFunction<MobEffectInstance, MobEffectInstance, MobEffectInstance> mixPotion();

    default MobEffectInstance getMixedInstance(MobEffectInstance firstInstance, MobEffectInstance secondInstance) {
        return this.mixPotion().apply(firstInstance, secondInstance);
    }

}
