package com.mikitellurium.telluriumsrandomstuff.client.item;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class GrapplingHookHandRenderer extends BlockEntityWithoutLevelRenderer {

    public static final GrapplingHookHandRenderer INSTANCE = new GrapplingHookHandRenderer(Minecraft.getInstance());
    private Minecraft minecraft;

    public GrapplingHookHandRenderer(Minecraft minecraft) {
        super(minecraft.getBlockEntityRenderDispatcher(), minecraft.getEntityModels());
        this.minecraft = minecraft;
    }

    @Override
    public void renderByItem(ItemStack itemStack, ItemDisplayContext context, PoseStack poseStack,
                             MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        if (context == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND || context == ItemDisplayContext.FIRST_PERSON_LEFT_HAND) {
            HumanoidArm arm = context == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND ? HumanoidArm.RIGHT : HumanoidArm.LEFT;
            this.renderHand(poseStack, bufferSource, minecraft.player, arm, packedLight);
        }
    }
    // todo add push/ pop pose calls
    private void renderHand(PoseStack poseStack, MultiBufferSource bufferSource, LocalPlayer player, HumanoidArm arm, int packedLight) {
        boolean isRightArm = arm == HumanoidArm.RIGHT;
        float f = isRightArm ? 1.0F : -1.0F;
        poseStack.translate(f * 0.64F, -0.6F, -0.72F);
        poseStack.mulPose(Axis.YP.rotationDegrees(f * 45.0F));

        poseStack.translate(f * -1.0F, 3.6F, 3.5F);
        poseStack.mulPose(Axis.ZP.rotationDegrees(f * 120.0F));
        poseStack.mulPose(Axis.XP.rotationDegrees(200.0F));
        poseStack.mulPose(Axis.YP.rotationDegrees(f * -135.0F));
        poseStack.translate(f * 5.6F, 0.0F, 0.0F);

        this.applyHandTransform(poseStack, isRightArm);
        RenderSystem.setShaderTexture(0, player.getSkinTextureLocation());
        PlayerRenderer playerRenderer = (PlayerRenderer) minecraft.getEntityRenderDispatcher().getRenderer(minecraft.player);
        if (isRightArm) {
            playerRenderer.renderRightHand(poseStack, bufferSource, packedLight, player);
        } else {
            playerRenderer.renderLeftHand(poseStack, bufferSource, packedLight, player);
        }
    }

    private void applyHandTransform(PoseStack poseStack, boolean isRightArm) {
        if (isRightArm) {
            poseStack.translate(-1.44F, -0.56F, -0.49F);
        } else {
            poseStack.translate(1.05F, -0.86F, -1.32F);
        }
    }

}
