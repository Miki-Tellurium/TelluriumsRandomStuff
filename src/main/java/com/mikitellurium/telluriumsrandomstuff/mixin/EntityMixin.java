package com.mikitellurium.telluriumsrandomstuff.mixin;

import com.mikitellurium.telluriumsrandomstuff.common.fluid.SoulLavaFluid;
import com.mikitellurium.telluriumsrandomstuff.registry.ModTags;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow public abstract boolean equals(Object pObject);

    @Inject(method = "displayFireAnimation", at = @At(value = "HEAD"), cancellable = true)
    private void displaySoulLavaFireAnimation(CallbackInfoReturnable<Boolean> cir) {
        Entity entity = (Entity) (Object) this;
        boolean isImmune = SoulLavaFluid.isImmuneToSoulLava(entity) || entity.fireImmune();
        boolean inSoulLava = SoulLavaFluid.isInSoulLava(entity);
        if (inSoulLava && !isImmune && !entity.isSpectator()) {
            cir.setReturnValue(true);
        }
    }

}
