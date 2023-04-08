package com.mikitellurium.telluriumsrandomstuff.mixin;

import com.mikitellurium.telluriumsrandomstuff.particle.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LiquidBlock.class)
public abstract class LiquidBlockMixin {

    @Shadow public abstract FlowingFluid getFluid();

    @Inject(method = "randomTick", at = @At(value = "TAIL"))
    private void randomConvertLavaToSoulLava(BlockState state, ServerLevel level, BlockPos pos,
                                             RandomSource random, CallbackInfo ci) {
        if (this.getFluid().is(FluidTags.LAVA)) {
            if (level.getBlockState(pos.below()).is(Blocks.SOUL_SAND)) {
                //System.out.println("SOUL_SAND");
            }
        }
    }

}
