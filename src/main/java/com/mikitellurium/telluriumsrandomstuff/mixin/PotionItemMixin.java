package com.mikitellurium.telluriumsrandomstuff.mixin;

import com.mikitellurium.telluriumsrandomstuff.common.recipe.PotionMixingRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PotionItem.class)
public class PotionItemMixin {

    @Inject(method = "getDescriptionId", at = @At(value = "HEAD"), cancellable = true)
    private void getMixedPotionId(ItemStack itemStack, CallbackInfoReturnable<String> cir) {
        if (itemStack.hasTag() && itemStack.getTag().contains(PotionMixingRecipe.TAG_MIXED)) {
            String potionId = ForgeRegistries.ITEMS.getDelegateOrThrow(itemStack.getItem()).key().location().getPath();
            cir.setReturnValue("item.telluriumsrandomstuff.mixed_" + potionId + ".name");
        }
    }

}
