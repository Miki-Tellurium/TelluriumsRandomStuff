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
        addCategory(MobEffects.MOVEMENT_SPEED, MobEffectUpgrade.AMPLIFIER);
        addCategory(MobEffects.MOVEMENT_SLOWDOWN, MobEffectUpgrade.AMPLIFIER);
        addCategory(MobEffects.DIG_SPEED, MobEffectUpgrade.AMPLIFIER);
        addCategory(MobEffects.DIG_SLOWDOWN, MobEffectUpgrade.AMPLIFIER);
        addCategory(MobEffects.DAMAGE_BOOST, MobEffectUpgrade.AMPLIFIER);
        addCategory(MobEffects.HEAL, MobEffectUpgrade.AMPLIFIER);
        addCategory(MobEffects.HARM, MobEffectUpgrade.AMPLIFIER);
        addCategory(MobEffects.JUMP, MobEffectUpgrade.AMPLIFIER);
        addCategory(MobEffects.CONFUSION, MobEffectUpgrade.DURATION);
        addCategory(MobEffects.REGENERATION, MobEffectUpgrade.AMPLIFIER);
        addCategory(MobEffects.DAMAGE_RESISTANCE, MobEffectUpgrade.AMPLIFIER);
        addCategory(MobEffects.FIRE_RESISTANCE, MobEffectUpgrade.DURATION);
        addCategory(MobEffects.WATER_BREATHING, MobEffectUpgrade.DURATION);
        addCategory(MobEffects.INVISIBILITY, MobEffectUpgrade.DURATION);
        addCategory(MobEffects.BLINDNESS, MobEffectUpgrade.DURATION);
        addCategory(MobEffects.NIGHT_VISION, MobEffectUpgrade.DURATION);
        addCategory(MobEffects.HUNGER, MobEffectUpgrade.AMPLIFIER);
        addCategory(MobEffects.WEAKNESS, MobEffectUpgrade.AMPLIFIER);
        addCategory(MobEffects.POISON, MobEffectUpgrade.AMPLIFIER);
        addCategory(MobEffects.WITHER, MobEffectUpgrade.AMPLIFIER);
        addCategory(MobEffects.HEALTH_BOOST, MobEffectUpgrade.AMPLIFIER);
        addCategory(MobEffects.ABSORPTION, MobEffectUpgrade.AMPLIFIER);
        addCategory(MobEffects.SATURATION, MobEffectUpgrade.AMPLIFIER);
        addCategory(MobEffects.GLOWING, MobEffectUpgrade.DURATION);
        addCategory(MobEffects.LEVITATION, MobEffectUpgrade.DURATION);
        addCategory(MobEffects.LUCK, MobEffectUpgrade.AMPLIFIER);
        addCategory(MobEffects.UNLUCK, MobEffectUpgrade.AMPLIFIER);
        addCategory(MobEffects.SLOW_FALLING, MobEffectUpgrade.DURATION);
        addCategory(MobEffects.CONDUIT_POWER, MobEffectUpgrade.AMPLIFIER);
        addCategory(MobEffects.DOLPHINS_GRACE, MobEffectUpgrade.AMPLIFIER);
        addCategory(MobEffects.BAD_OMEN, MobEffectUpgrade.AMPLIFIER);
        addCategory(MobEffects.HERO_OF_THE_VILLAGE, MobEffectUpgrade.AMPLIFIER);
        addCategory(MobEffects.DARKNESS, MobEffectUpgrade.DURATION);
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
