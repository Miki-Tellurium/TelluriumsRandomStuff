package com.mikitellurium.telluriumsrandomstuff.mixin;

import com.mikitellurium.telluriumsrandomstuff.common.fluid.SoulLavaFluid;
import com.mikitellurium.telluriumsrandomstuff.registry.ModFluids;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @ModifyVariable(method = "travel", at = @At("STORE"), ordinal = 0)
    private FluidState getCorrectSoulLavaState(FluidState fluidState) {
        LivingEntity entity = (LivingEntity) (Object) this;
        return SoulLavaFluid.isInSoulLava(entity) ? ModFluids.SOUL_LAVA_SOURCE.get().defaultFluidState() : fluidState;
    }

}
