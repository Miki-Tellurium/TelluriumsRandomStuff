package com.mikitellurium.telluriumsrandomstuff.common.mixin;

import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(CustomHeadLayer.class)
public class CustomHeadLayerMixin {

    @Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V"
    , at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getItemBySlot(Lnet/minecraft/world/entity/EquipmentSlot;)Lnet/minecraft/world/item/ItemStack;",
            shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void cancelRenderingOfGoogles(PoseStack pMatrixStack, MultiBufferSource pBuffer,
                                                 int pPackedLight, LivingEntity pLivingEntity, float pLimbSwing,
                                                 float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks,
                                                 float pNetHeadYaw, float pHeadPitch, CallbackInfo ci) {
        if (pLivingEntity.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.LAVA_GOOGLES.get())) {
            ci.cancel(); // Stop CustomHeadLayer from rendering lava googles item over player head
        }
    }

}
