package com.mikitellurium.telluriumsrandomstuff.datagen.providers;

import com.mikitellurium.telluriumsrandomstuff.lib.PotionMixingFunctionProvider;
import com.mikitellurium.telluriumsrandomstuff.registry.ModPotionMixingFunctions;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import net.minecraft.data.PackOutput;
import net.minecraft.world.effect.MobEffects;

public class ModPotionMixingFunctionProvider extends PotionMixingFunctionProvider {
    
    public ModPotionMixingFunctionProvider(PackOutput output) {
        super(output, FastLoc.modId());
    }

    @Override
    protected void start() {
        this.addFunction(MobEffects.MOVEMENT_SPEED, ModPotionMixingFunctions.INCREASE_AMPLIFIER.get());
        this.addFunction(MobEffects.MOVEMENT_SLOWDOWN, ModPotionMixingFunctions.INCREASE_AMPLIFIER.get());
        this.addFunction(MobEffects.DIG_SPEED, ModPotionMixingFunctions.INCREASE_AMPLIFIER.get());
        this.addFunction(MobEffects.DIG_SLOWDOWN, ModPotionMixingFunctions.INCREASE_AMPLIFIER.get());
        this.addFunction(MobEffects.DAMAGE_BOOST, ModPotionMixingFunctions.INCREASE_AMPLIFIER.get());
        this.addFunction(MobEffects.HEAL, ModPotionMixingFunctions.INCREASE_AMPLIFIER.get());
        this.addFunction(MobEffects.HARM, ModPotionMixingFunctions.INCREASE_AMPLIFIER.get());
        this.addFunction(MobEffects.JUMP, ModPotionMixingFunctions.INCREASE_AMPLIFIER.get());
        this.addFunction(MobEffects.CONFUSION, ModPotionMixingFunctions.INCREASE_DURATION.get());
        this.addFunction(MobEffects.REGENERATION, ModPotionMixingFunctions.INCREASE_AMPLIFIER.get());
        this.addFunction(MobEffects.DAMAGE_RESISTANCE, ModPotionMixingFunctions.INCREASE_AMPLIFIER.get());
        this.addFunction(MobEffects.FIRE_RESISTANCE, ModPotionMixingFunctions.INCREASE_DURATION.get());
        this.addFunction(MobEffects.WATER_BREATHING, ModPotionMixingFunctions.INCREASE_DURATION.get());
        this.addFunction(MobEffects.INVISIBILITY, ModPotionMixingFunctions.INCREASE_DURATION.get());
        this.addFunction(MobEffects.BLINDNESS, ModPotionMixingFunctions.INCREASE_DURATION.get());
        this.addFunction(MobEffects.NIGHT_VISION, ModPotionMixingFunctions.INCREASE_DURATION.get());
        this.addFunction(MobEffects.HUNGER, ModPotionMixingFunctions.INCREASE_AMPLIFIER.get());
        this.addFunction(MobEffects.WEAKNESS, ModPotionMixingFunctions.INCREASE_AMPLIFIER.get());
        this.addFunction(MobEffects.POISON, ModPotionMixingFunctions.INCREASE_AMPLIFIER.get());
        this.addFunction(MobEffects.WITHER, ModPotionMixingFunctions.INCREASE_AMPLIFIER.get());
        this.addFunction(MobEffects.HEALTH_BOOST, ModPotionMixingFunctions.INCREASE_AMPLIFIER.get());
        this.addFunction(MobEffects.ABSORPTION, ModPotionMixingFunctions.INCREASE_AMPLIFIER.get());
        this.addFunction(MobEffects.SATURATION, ModPotionMixingFunctions.INCREASE_AMPLIFIER.get());
        this.addFunction(MobEffects.GLOWING, ModPotionMixingFunctions.INCREASE_DURATION.get());
        this.addFunction(MobEffects.LEVITATION, ModPotionMixingFunctions.INCREASE_DURATION.get());
        this.addFunction(MobEffects.LUCK, ModPotionMixingFunctions.INCREASE_AMPLIFIER.get());
        this.addFunction(MobEffects.UNLUCK, ModPotionMixingFunctions.INCREASE_AMPLIFIER.get());
        this.addFunction(MobEffects.SLOW_FALLING, ModPotionMixingFunctions.INCREASE_DURATION.get());
        this.addFunction(MobEffects.CONDUIT_POWER, ModPotionMixingFunctions.INCREASE_AMPLIFIER.get());
        this.addFunction(MobEffects.DOLPHINS_GRACE, ModPotionMixingFunctions.INCREASE_DURATION.get());
        this.addFunction(MobEffects.BAD_OMEN, ModPotionMixingFunctions.INCREASE_AMPLIFIER.get());
        this.addFunction(MobEffects.HERO_OF_THE_VILLAGE, ModPotionMixingFunctions.INCREASE_DURATION.get());
        this.addFunction(MobEffects.DARKNESS, ModPotionMixingFunctions.INCREASE_DURATION.get());
    }
    
}
