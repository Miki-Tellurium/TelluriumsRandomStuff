package com.mikitellurium.telluriumsrandomstuff.mixin;

import com.mikitellurium.telluriumsrandomstuff.fluid.ModFluidTypes;
import com.mikitellurium.telluriumsrandomstuff.fluid.ModFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @ModifyVariable(method = "travel", at = @At("STORE"), ordinal = 0)
    private FluidState injected(FluidState fluidState) {
        LivingEntity entity = (LivingEntity) (Object) this;

        if (entity.getMaxHeightFluidType() != ModFluidTypes.SOUL_LAVA_TYPE) {
            return fluidState; // Vanilla behaviour
        } else {
            BlockPos pos = entity.blockPosition();
            if (entity.level().getFluidState(pos).getType() == Fluids.EMPTY) { // If fluidstate is wrong return custom
                return ModFluids.SOUL_LAVA_SOURCE.get().defaultFluidState();
            } else { // If fluidstate is correct return vanilla
                return fluidState;
            }
        }
    }
}
