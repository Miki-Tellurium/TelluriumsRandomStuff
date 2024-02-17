package com.mikitellurium.telluriumsrandomstuff.mixin;

import com.mikitellurium.telluriumsrandomstuff.common.fluid.SoulLavaFluid;
import com.mikitellurium.telluriumsrandomstuff.registry.ModFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.monster.Strider;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Strider.class)
public class StriderMixin {

    @Inject(method = "canStandOnFluid", at = @At(value = "HEAD"), cancellable = true)
    private void canStandOnSoulLava(FluidState fluidState, CallbackInfoReturnable<Boolean> cir) {
        if (fluidState.is(ModFluids.SOUL_LAVA_SOURCE.get())) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "checkFallDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/monster/Strider;isInLava()Z"))
    private void canStandOnSoulLava(double pY, boolean onGround, BlockState blockState, BlockPos pos, CallbackInfo ci) {
        Strider strider = (Strider) (Object) this;
        if (SoulLavaFluid.isEntityInSoulLava(strider)) {
            strider.resetFallDistance();
        }
    }

    @Inject(method = "getWalkTargetValue", at = @At(value = "HEAD"), cancellable = true)
    private void getSoulLavaTargetValue(BlockPos pos, LevelReader level, CallbackInfoReturnable<Float> cir) {
        if (level.getBlockState(pos).getFluidState().is(ModFluids.SOUL_LAVA_SOURCE.get())) {
            cir.setReturnValue(8.0F);
        }
    }

}
