package com.mikitellurium.telluriumsrandomstuff.registry;

import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final RegistryObject<SoundEvent> FLOATING_WALKWAY_SUNK = registerSoundEvent("block.floating_walkway_sunk");

    public static final SoundType GRATE_SOUL_SAND_TYPE = new ForgeSoundType(1.0F, 1.0F, () ->
            SoundEvents.SOUL_SAND_BREAK, () -> SoundEvents.METAL_STEP, () -> SoundEvents.SOUL_SAND_PLACE,
            () -> SoundEvents.SOUL_SAND_HIT, () -> SoundEvents.SOUL_SAND_FALL);

    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return ModRegistries.SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(FastLoc.modLoc(name)));
    }

    public static void register(IEventBus eventBus) {
        ModRegistries.SOUND_EVENTS.register(eventBus);
    }
}