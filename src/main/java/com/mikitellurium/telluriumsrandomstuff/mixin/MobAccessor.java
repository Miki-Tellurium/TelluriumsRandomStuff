package com.mikitellurium.telluriumsrandomstuff.mixin;

import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Mob.class)
public interface MobAccessor {

    @Invoker("populateDefaultEquipmentSlots")
    void invokePopulateDefaultEquipmentSlots(RandomSource pRandom, DifficultyInstance pDifficulty);

}
