package com.mikitellurium.telluriumsrandomstuff.sounds;

import net.minecraft.sounds.SoundEvents;

import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.common.util.ForgeSoundType;

public class ModSoundTypes {

    public static final SoundType GRATE_SOUL_SAND = new ForgeSoundType(1.0F, 1.0F, () -> SoundEvents.SOUL_SAND_BREAK,
            () -> SoundEvents.METAL_STEP, () -> SoundEvents.SOUL_SAND_PLACE, () -> SoundEvents.SOUL_SAND_HIT, () -> SoundEvents.SOUL_SAND_FALL);

}