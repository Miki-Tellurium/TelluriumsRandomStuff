package com.mikitellurium.telluriumsrandomstuff.common.mixin;

import com.mikitellurium.telluriumsrandomstuff.common.config.ModCommonConfig;
import com.mikitellurium.telluriumsrandomstuff.registry.ModTags;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Allay.class)
public class AllayMixin {

    @Inject(method = "isDuplicationItem", at = @At(value = "HEAD"), cancellable = true)
    private void onCheckDuplicationItem(ItemStack itemStack, CallbackInfoReturnable<Boolean> cir) {
        if (ModCommonConfig.ALLAY_DUPLICATE_WITH_ECHO_SHARD.get() && itemStack.is(ModTags.Items.ALLAY_DUPLICATION_ITEMS)) {
            cir.setReturnValue(true);
        }
    }

}
