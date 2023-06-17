package com.mikitellurium.telluriumsrandomstuff.common.mixin;

import com.mikitellurium.telluriumsrandomstuff.registry.ModFluidTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PointedDripstoneBlock.class)
public class PointedDripstoneBlockMixin {

    @Inject(method = "spawnDripParticle(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/material/Fluid;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V"),
            cancellable = true)
    private static void addSoulLavaParticle(Level pLevel, BlockPos pPos, BlockState pState, Fluid pFluid, CallbackInfo ci) {
        if (pFluid.getFluidType() == ModFluidTypes.SOUL_LAVA_FLUID_TYPE.get()) {
            ci.cancel(); // Don't spawn particles if the fluid is soul lava
        }
    }

}
