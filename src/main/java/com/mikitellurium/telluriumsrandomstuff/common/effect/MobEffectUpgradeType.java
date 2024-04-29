package com.mikitellurium.telluriumsrandomstuff.common.effect;

import net.minecraft.Util;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.common.IExtensibleEnum;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public enum MobEffectUpgradeType implements IExtensibleEnum {
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

    private static final Map<MobEffect, MobEffectUpgradeType> MOB_EFFECT_CATEGORIES = Util.make(new HashMap<>(), (map) -> {
        map.put(MobEffects.MOVEMENT_SPEED, MobEffectUpgradeType.AMPLIFIER);
        map.put(MobEffects.MOVEMENT_SLOWDOWN, MobEffectUpgradeType.AMPLIFIER);
        map.put(MobEffects.DIG_SPEED, MobEffectUpgradeType.AMPLIFIER);
        map.put(MobEffects.DIG_SLOWDOWN, MobEffectUpgradeType.AMPLIFIER);
        map.put(MobEffects.DAMAGE_BOOST, MobEffectUpgradeType.AMPLIFIER);
        map.put(MobEffects.HEAL, MobEffectUpgradeType.AMPLIFIER);
        map.put(MobEffects.HARM, MobEffectUpgradeType.AMPLIFIER);
        map.put(MobEffects.JUMP, MobEffectUpgradeType.AMPLIFIER);
        map.put(MobEffects.CONFUSION, MobEffectUpgradeType.DURATION);
        map.put(MobEffects.REGENERATION, MobEffectUpgradeType.AMPLIFIER);
        map.put(MobEffects.DAMAGE_RESISTANCE, MobEffectUpgradeType.AMPLIFIER);
        map.put(MobEffects.FIRE_RESISTANCE, MobEffectUpgradeType.DURATION);
        map.put(MobEffects.WATER_BREATHING, MobEffectUpgradeType.DURATION);
        map.put(MobEffects.INVISIBILITY, MobEffectUpgradeType.DURATION);
        map.put(MobEffects.BLINDNESS, MobEffectUpgradeType.DURATION);
        map.put(MobEffects.NIGHT_VISION, MobEffectUpgradeType.DURATION);
        map.put(MobEffects.HUNGER, MobEffectUpgradeType.AMPLIFIER);
        map.put(MobEffects.WEAKNESS, MobEffectUpgradeType.AMPLIFIER);
        map.put(MobEffects.POISON, MobEffectUpgradeType.AMPLIFIER);
        map.put(MobEffects.WITHER, MobEffectUpgradeType.AMPLIFIER);
        map.put(MobEffects.HEALTH_BOOST, MobEffectUpgradeType.AMPLIFIER);
        map.put(MobEffects.ABSORPTION, MobEffectUpgradeType.AMPLIFIER);
        map.put(MobEffects.SATURATION, MobEffectUpgradeType.AMPLIFIER);
        map.put(MobEffects.GLOWING, MobEffectUpgradeType.DURATION);
        map.put(MobEffects.LEVITATION, MobEffectUpgradeType.DURATION);
        map.put(MobEffects.LUCK, MobEffectUpgradeType.AMPLIFIER);
        map.put(MobEffects.UNLUCK, MobEffectUpgradeType.AMPLIFIER);
        map.put(MobEffects.SLOW_FALLING, MobEffectUpgradeType.DURATION);
        map.put(MobEffects.CONDUIT_POWER, MobEffectUpgradeType.AMPLIFIER);
        map.put(MobEffects.DOLPHINS_GRACE, MobEffectUpgradeType.AMPLIFIER);
        map.put(MobEffects.BAD_OMEN, MobEffectUpgradeType.AMPLIFIER);
        map.put(MobEffects.HERO_OF_THE_VILLAGE, MobEffectUpgradeType.AMPLIFIER);
        map.put(MobEffects.DARKNESS, MobEffectUpgradeType.DURATION);
    });

    /**
     * Adds a new category for a mob effect.
     *
     * @param mobEffect the mob effect
     * @param category the category corresponding to the effect
     */
    public static void addCategory(MobEffect mobEffect, MobEffectUpgradeType category) {
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
    public static MobEffectUpgradeType getCategory(MobEffect mobEffect) {
        MobEffectUpgradeType category = MOB_EFFECT_CATEGORIES.get(mobEffect);
        return category != null ? category : AMPLIFIER;
    }

    private final BiFunction<MobEffectInstance, MobEffectInstance, MobEffectInstance> getMixedInstance;

    MobEffectUpgradeType(BiFunction<MobEffectInstance, MobEffectInstance, MobEffectInstance> getMixedInstance) {
        this.getMixedInstance = getMixedInstance;
    }

    public MobEffectInstance getMixedInstance(MobEffectInstance firstInstance, MobEffectInstance secondInstance) {
        return this.getMixedInstance.apply(firstInstance, secondInstance);
    }

    public static MobEffectUpgradeType create(String name, BiFunction<MobEffectInstance, MobEffectInstance, MobEffectInstance> getMixedInstance) {
        throw new IllegalArgumentException("Enum not extended");
    }

}
