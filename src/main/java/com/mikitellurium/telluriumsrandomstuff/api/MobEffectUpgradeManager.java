package com.mikitellurium.telluriumsrandomstuff.api;

import com.mikitellurium.telluriumsrandomstuff.common.effect.MobEffectUpgradeType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;

import java.util.HashMap;
import java.util.Map;

/**
 * This class manages the potion upgrades types used by the alchemixer block.
 */
public class MobEffectUpgradeManager {

    private static final Map<MobEffect, MobEffectUpgradeType> MOB_EFFECT_UPGRADE_TYPES = new HashMap<>();

    /**
     * Adds a new category for a mob effect.
     *
     * @param mobEffect the mob effect
     * @param category the category corresponding to the effect
     */
    public static void addCategory(MobEffect mobEffect, MobEffectUpgradeType category) {
        MOB_EFFECT_UPGRADE_TYPES.put(mobEffect, category);
    }

    /**
     * Return the {@code MobEffectUpgradeType} corresponding to the provided
     * mob effect.
     * If an upgrade type is not registered for the mob effect returns
     * {@link MobEffectUpgradeType#AMPLIFIER} by default.
     *
     * @param mobEffect The MobEffect for which the upgrade type is to be retrieved
     * @return The upgrade type associated with the specified effect,
     *         or {@code MobEffectUpgradeType.AMPLIFIER} if an
     *         upgrade type has not be registered
     */
    public static MobEffectUpgradeType getCategory(MobEffect mobEffect) {
        MobEffectUpgradeType category = MOB_EFFECT_UPGRADE_TYPES.get(mobEffect);
        return category != null ? category : MobEffectUpgradeType.AMPLIFIER;
    }

    public static void init() {
        addCategory(MobEffects.MOVEMENT_SPEED, MobEffectUpgradeType.AMPLIFIER);
        addCategory(MobEffects.MOVEMENT_SLOWDOWN, MobEffectUpgradeType.AMPLIFIER);
        addCategory(MobEffects.DIG_SPEED, MobEffectUpgradeType.AMPLIFIER);
        addCategory(MobEffects.DIG_SLOWDOWN, MobEffectUpgradeType.AMPLIFIER);
        addCategory(MobEffects.DAMAGE_BOOST, MobEffectUpgradeType.AMPLIFIER);
        addCategory(MobEffects.HEAL, MobEffectUpgradeType.AMPLIFIER);
        addCategory(MobEffects.HARM, MobEffectUpgradeType.AMPLIFIER);
        addCategory(MobEffects.JUMP, MobEffectUpgradeType.AMPLIFIER);
        addCategory(MobEffects.CONFUSION, MobEffectUpgradeType.DURATION);
        addCategory(MobEffects.REGENERATION, MobEffectUpgradeType.AMPLIFIER);
        addCategory(MobEffects.DAMAGE_RESISTANCE, MobEffectUpgradeType.AMPLIFIER);
        addCategory(MobEffects.FIRE_RESISTANCE, MobEffectUpgradeType.DURATION);
        addCategory(MobEffects.WATER_BREATHING, MobEffectUpgradeType.DURATION);
        addCategory(MobEffects.INVISIBILITY, MobEffectUpgradeType.DURATION);
        addCategory(MobEffects.BLINDNESS, MobEffectUpgradeType.DURATION);
        addCategory(MobEffects.NIGHT_VISION, MobEffectUpgradeType.DURATION);
        addCategory(MobEffects.HUNGER, MobEffectUpgradeType.AMPLIFIER);
        addCategory(MobEffects.WEAKNESS, MobEffectUpgradeType.AMPLIFIER);
        addCategory(MobEffects.POISON, MobEffectUpgradeType.AMPLIFIER);
        addCategory(MobEffects.WITHER, MobEffectUpgradeType.AMPLIFIER);
        addCategory(MobEffects.HEALTH_BOOST, MobEffectUpgradeType.AMPLIFIER);
        addCategory(MobEffects.ABSORPTION, MobEffectUpgradeType.AMPLIFIER);
        addCategory(MobEffects.SATURATION, MobEffectUpgradeType.AMPLIFIER);
        addCategory(MobEffects.GLOWING, MobEffectUpgradeType.DURATION);
        addCategory(MobEffects.LEVITATION, MobEffectUpgradeType.DURATION);
        addCategory(MobEffects.LUCK, MobEffectUpgradeType.AMPLIFIER);
        addCategory(MobEffects.UNLUCK, MobEffectUpgradeType.AMPLIFIER);
        addCategory(MobEffects.SLOW_FALLING, MobEffectUpgradeType.DURATION);
        addCategory(MobEffects.CONDUIT_POWER, MobEffectUpgradeType.AMPLIFIER);
        addCategory(MobEffects.DOLPHINS_GRACE, MobEffectUpgradeType.AMPLIFIER);
        addCategory(MobEffects.BAD_OMEN, MobEffectUpgradeType.AMPLIFIER);
        addCategory(MobEffects.HERO_OF_THE_VILLAGE, MobEffectUpgradeType.AMPLIFIER);
        addCategory(MobEffects.DARKNESS, MobEffectUpgradeType.DURATION);
    }

}
