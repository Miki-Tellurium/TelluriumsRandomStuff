package com.mikitellurium.telluriumsrandomstuff.mixin;

import com.mikitellurium.telluriumsrandomstuff.common.fluid.SoulLavaFluid;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PathNavigation.class)
public class PathNavigationMixin {

    @Shadow @Final protected Mob mob;

    @Inject(method = "isInLiquid", at = @At(value = "HEAD"), cancellable = true)
    private void isInSoulLava(CallbackInfoReturnable<Boolean> cir) {
        if (SoulLavaFluid.isEntityInSoulLava(this.mob)) {
            cir.setReturnValue(true);
        }
    }

}
