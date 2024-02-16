package com.mikitellurium.telluriumsrandomstuff.mixin;

import com.mikitellurium.telluriumsrandomstuff.common.fluid.SoulLavaFluid;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ScreenEffectRenderer;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.client.event.RenderBlockScreenEffectEvent;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ScreenEffectRenderer.class)
public abstract class ScreenEffectRendererMixin {

    @Shadow
    private static void renderFire(Minecraft pMinecraft, PoseStack pPoseStack) {}

    @Inject(method = "renderScreenEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isOnFire()Z"))
    private static void renderFireOverlayInSoulLava(Minecraft minecraft, PoseStack poseStack, CallbackInfo ci) {
        if (SoulLavaFluid.isEntityInSoulLava(minecraft.player)) {
            if(!MinecraftForge.EVENT_BUS.post(new RenderBlockScreenEffectEvent(minecraft.player, poseStack, RenderBlockScreenEffectEvent.OverlayType.FIRE, Blocks.FIRE.defaultBlockState(), minecraft.player.blockPosition()))) {
                renderFire(minecraft, poseStack);
            }
        }
    }

}
