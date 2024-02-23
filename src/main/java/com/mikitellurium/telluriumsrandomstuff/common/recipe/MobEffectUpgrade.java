package com.mikitellurium.telluriumsrandomstuff.common.recipe;

import net.minecraft.Util;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.common.IExtensibleEnum;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public enum MobEffectUpgrade implements IExtensibleEnum {
    DURATION((firstInstance, secondInstance) -> {
        int firstDuration = firstInstance.getDuration();
        int secondDuration = secondInstance.getDuration();
        int duration = Math.max(firstDuration, secondDuration) + Math.min(firstDuration, secondDuration) / 2;
        return new MobEffectInstance(firstInstance.getEffect(), duration,
                Math.max(firstInstance.getAmplifier(), secondInstance.getAmplifier()));
    }),
    AMPLIFIER((firstInstance, secondInstance) -> {
        int amplifier = firstInstance.getAmplifier() == secondInstance.getAmplifier() ?
                firstInstance.getAmplifier() + 1 : Math.max(firstInstance.getAmplifier(), secondInstance.getAmplifier());
        return new MobEffectInstance(firstInstance.getEffect(),
                Math.min(firstInstance.getDuration(), secondInstance.getDuration()), amplifier);
    });

    private static final Map<MobEffect, MobEffectUpgrade> MOB_EFFECT_CATEGORIES = Util.make(new HashMap<>(), (map) -> {
        map.put(MobEffects.MOVEMENT_SPEED, MobEffectUpgrade.AMPLIFIER);
        map.put(MobEffects.MOVEMENT_SLOWDOWN, MobEffectUpgrade.AMPLIFIER);
        map.put(MobEffects.DIG_SPEED, MobEffectUpgrade.AMPLIFIER);
        map.put(MobEffects.DIG_SLOWDOWN, MobEffectUpgrade.AMPLIFIER);
        map.put(MobEffects.DAMAGE_BOOST, MobEffectUpgrade.AMPLIFIER);
        map.put(MobEffects.HEAL, MobEffectUpgrade.AMPLIFIER);
        map.put(MobEffects.HARM, MobEffectUpgrade.AMPLIFIER);
        map.put(MobEffects.JUMP, MobEffectUpgrade.AMPLIFIER);
        map.put(MobEffects.CONFUSION, MobEffectUpgrade.DURATION);
        map.put(MobEffects.REGENERATION, MobEffectUpgrade.AMPLIFIER);
        map.put(MobEffects.DAMAGE_RESISTANCE, MobEffectUpgrade.AMPLIFIER);
        map.put(MobEffects.FIRE_RESISTANCE, MobEffectUpgrade.DURATION);
        map.put(MobEffects.WATER_BREATHING, MobEffectUpgrade.DURATION);
        map.put(MobEffects.INVISIBILITY, MobEffectUpgrade.DURATION);
        map.put(MobEffects.BLINDNESS, MobEffectUpgrade.DURATION);
        map.put(MobEffects.NIGHT_VISION, MobEffectUpgrade.DURATION);
        map.put(MobEffects.HUNGER, MobEffectUpgrade.AMPLIFIER);
        map.put(MobEffects.WEAKNESS, MobEffectUpgrade.AMPLIFIER);
        map.put(MobEffects.POISON, MobEffectUpgrade.AMPLIFIER);
        map.put(MobEffects.WITHER, MobEffectUpgrade.AMPLIFIER);
        map.put(MobEffects.HEALTH_BOOST, MobEffectUpgrade.AMPLIFIER);
        map.put(MobEffects.ABSORPTION, MobEffectUpgrade.AMPLIFIER);
        map.put(MobEffects.SATURATION, MobEffectUpgrade.AMPLIFIER);
        map.put(MobEffects.GLOWING, MobEffectUpgrade.DURATION);
        map.put(MobEffects.LEVITATION, MobEffectUpgrade.DURATION);
        map.put(MobEffects.LUCK, MobEffectUpgrade.AMPLIFIER);
        map.put(MobEffects.UNLUCK, MobEffectUpgrade.AMPLIFIER);
        map.put(MobEffects.SLOW_FALLING, MobEffectUpgrade.DURATION);
        map.put(MobEffects.CONDUIT_POWER, MobEffectUpgrade.AMPLIFIER);
        map.put(MobEffects.DOLPHINS_GRACE, MobEffectUpgrade.AMPLIFIER);
        map.put(MobEffects.BAD_OMEN, MobEffectUpgrade.AMPLIFIER);
        map.put(MobEffects.HERO_OF_THE_VILLAGE, MobEffectUpgrade.AMPLIFIER);
        map.put(MobEffects.DARKNESS, MobEffectUpgrade.DURATION);
    });

    /**
     * Adds a new category for a mob effect.
     *
     * @param mobEffect the mob effect
     * @param category the category corresponding to the effect
     */
    public static void addCategory(MobEffect mobEffect, MobEffectUpgrade category) {
        MOB_EFFECT_CATEGORIES.put(mobEffect, category);
    }

    /**
     * Return the {@code MobCategory} corresponding to the provided
     * mob effect.
     * If the category is not registered for the mob effect returns
     * {@link #AMPLIFIER} by default.
     *
     * @param mobEffect The MobEffect for which the category is to be retrieved
     * @return The category associated with the specified effect,
     *         or the default category {@code AMPLIFIER} if a category is not present
     */
    public static MobEffectUpgrade getCategory(MobEffect mobEffect) {
        MobEffectUpgrade category = MOB_EFFECT_CATEGORIES.get(mobEffect);
        return category != null ? category : AMPLIFIER;
    }

    private final BiFunction<MobEffectInstance, MobEffectInstance, MobEffectInstance> getMixedInstance;

    MobEffectUpgrade(BiFunction<MobEffectInstance, MobEffectInstance, MobEffectInstance> getMixedInstance) {
        this.getMixedInstance = getMixedInstance;
    }

    public MobEffectInstance getMixedInstance(MobEffectInstance firstInstance, MobEffectInstance secondInstance) {
        return this.getMixedInstance.apply(firstInstance, secondInstance);
    }

    public static MobEffectUpgrade create(String name, BiFunction<MobEffectInstance, MobEffectInstance, MobEffectInstance> getMixedInstance) {
        throw new IllegalArgumentException("Enum not extended");
    }

}
