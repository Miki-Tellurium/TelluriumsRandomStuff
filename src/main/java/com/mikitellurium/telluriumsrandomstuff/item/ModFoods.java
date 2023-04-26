package com.mikitellurium.telluriumsrandomstuff.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {

    public static final FoodProperties MYSTIC_POTATO = (new FoodProperties.Builder()).nutrition(5).saturationMod(0.6F)
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 2400, 1), 1)
            .effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 1200), 1)
            .effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION, 2400), 1)
            .alwaysEat().build();

}
