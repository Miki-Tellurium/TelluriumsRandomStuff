package com.mikitellurium.telluriumsrandomstuff.client.entity.render;

import com.mikitellurium.telluriumsrandomstuff.common.entity.DummyPlayer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class DummyPlayerRenderer extends EntityRenderer<DummyPlayer> {

    private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/player/wide/steve.png");
    private final PlayerModel<?> model;

    public DummyPlayerRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new PlayerModel<>(context.bakeLayer(ModelLayers.PLAYER), false);
        this.model.young = false;
    }

    @Override
    public void render(DummyPlayer dummyPlayer, float yaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
        poseStack.translate(0.0F, -1.501F, 0.0F);
        RenderType renderType = this.model.renderType(this.getTextureLocation(dummyPlayer));
        VertexConsumer vertexConsumer = bufferSource.getBuffer(renderType);
        this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F , 1.0F , 1.0F);
        poseStack.popPose();
        super.render(dummyPlayer, yaw, partialTick, poseStack, bufferSource, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(DummyPlayer dummyPlayer) {
        return TEXTURE;
    }

}
