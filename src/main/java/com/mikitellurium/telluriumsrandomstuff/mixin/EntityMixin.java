package com.mikitellurium.telluriumsrandomstuff.mixin;

import com.mikitellurium.telluriumsrandomstuff.common.fluid.SoulLavaFluid;
import com.mikitellurium.telluriumsrandomstuff.registry.ModTags;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {

    @Inject(method = "displayFireAnimation", at = @At(value = "HEAD"), cancellable = true)
    private void displaySoulLavaFireAnimation(CallbackInfoReturnable<Boolean> cir) {
        Entity entity = (Entity) (Object) this;
        boolean isImmune = entity.getType().is(ModTags.EntityTypes.SOUL_LAVA_IMMUNE) || entity.fireImmune();
        boolean inSoulLava = SoulLavaFluid.isEntityInSoulLava(entity);
        if (inSoulLava && !isImmune && !entity.isSpectator()) {
            cir.setReturnValue(true);
        }
    }

}
