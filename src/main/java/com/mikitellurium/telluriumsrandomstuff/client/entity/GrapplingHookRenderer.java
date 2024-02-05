package com.mikitellurium.telluriumsrandomstuff.client.entity;

import com.mikitellurium.telluriumsrandomstuff.common.entity.GrapplingHookEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class GrapplingHookRenderer extends EntityRenderer<GrapplingHookEntity> {

    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("textures/item/trident.png");
    private static final RenderType RENDER_TYPE = RenderType.entityCutout(TEXTURE_LOCATION);

    public GrapplingHookRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(GrapplingHookEntity grapplingHook, float yaw, float partialTicks, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        Player player = grapplingHook.getPlayerOwner();
        if (player != null) {
            poseStack.pushPose();
            poseStack.pushPose();
            poseStack.scale(0.5F, 0.5F, 0.5F);
            poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
            PoseStack.Pose posestack$pose = poseStack.last();
            Matrix4f matrix4f = posestack$pose.pose();
            Matrix3f matrix3f = posestack$pose.normal();
            VertexConsumer vertexconsumer = bufferSource.getBuffer(RENDER_TYPE);
            vertex(vertexconsumer, matrix4f, matrix3f, packedLight, 0.0F, 0, 0, 1);
            vertex(vertexconsumer, matrix4f, matrix3f, packedLight, 1.0F, 0, 1, 1);
            vertex(vertexconsumer, matrix4f, matrix3f, packedLight, 1.0F, 1, 1, 0);
            vertex(vertexconsumer, matrix4f, matrix3f, packedLight, 0.0F, 1, 0, 0);
            poseStack.popPose();
            int i = player.getMainArm() == HumanoidArm.RIGHT ? 1 : -1;
            float f = player.getAttackAnim(partialTicks);
            float f1 = Mth.sin(Mth.sqrt(f) * (float)Math.PI);
            float f2 = Mth.lerp(partialTicks, player.yBodyRotO, player.yBodyRot) * ((float)Math.PI / 180F);
            double d0 = Mth.sin(f2);
            double d1 = Mth.cos(f2);
            double d2 = (double)i * 0.35D;
            double d4;
            double d5;
            double d6;
            float f3;
            if (this.entityRenderDispatcher.options.getCameraType().isFirstPerson() && player == Minecraft.getInstance().player) {
                double d7 = 960.0D / (double) this.entityRenderDispatcher.options.fov().get();
                Vec3 vec3 = this.entityRenderDispatcher.camera.getNearPlane().getPointOnPlane((float)i * 0.525F, -0.1F);
                vec3 = vec3.scale(d7);
                vec3 = vec3.yRot(f1 * 0.5F);
                vec3 = vec3.xRot(-f1 * 0.7F);
                d4 = Mth.lerp(partialTicks, player.xo, player.getX()) + vec3.x;
                d5 = Mth.lerp(partialTicks, player.yo, player.getY()) + vec3.y;
                d6 = Mth.lerp(partialTicks, player.zo, player.getZ()) + vec3.z;
                f3 = player.getEyeHeight();
            } else {
                d4 = Mth.lerp(partialTicks, player.xo, player.getX()) - d1 * d2 - d0 * 0.8D;
                d5 = player.yo + (double)player.getEyeHeight() + (player.getY() - player.yo) * (double)partialTicks - 0.45D;
                d6 = Mth.lerp(partialTicks, player.zo, player.getZ()) - d0 * d2 + d1 * 0.8D;
                f3 = player.isCrouching() ? -0.1875F : 0.0F;
            }

            double d9 = Mth.lerp(partialTicks, grapplingHook.xo, grapplingHook.getX());
            double d10 = Mth.lerp(partialTicks, grapplingHook.yo, grapplingHook.getY()) + 0.25D;
            double d8 = Mth.lerp(partialTicks, grapplingHook.zo, grapplingHook.getZ());
            float f4 = (float)(d4 - d9);
            float f5 = (float)(d5 - d10) + f3;
            float f6 = (float)(d6 - d8);
            VertexConsumer vertexconsumer1 = bufferSource.getBuffer(RenderType.lineStrip());
            PoseStack.Pose posestack$pose1 = poseStack.last();

            final int segments = 16;
            for(int k = 0; k <= segments; ++k) {
                stringVertex(f4, f5, f6, vertexconsumer1, posestack$pose1,
                        fraction(k, segments), fraction(k + 1, segments));
            }

            poseStack.popPose();
            super.render(grapplingHook, yaw, partialTicks, poseStack, bufferSource, packedLight);
        }
    }

    private static float fraction(int pNumerator, int pDenominator) {
        return (float)pNumerator / (float)pDenominator;
    }

    private static void vertex(VertexConsumer vertexConsumer, Matrix4f matrix4f, Matrix3f normal, int lightmapUV,
                               float pX, int pY, int pU, int pV) {
        vertexConsumer.vertex(matrix4f, pX - 0.5F, (float)pY - 0.5F, 0.0F).color(255, 255, 255, 255).uv((float)pU, (float)pV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lightmapUV).normal(normal, 0.0F, 1.0F, 0.0F).endVertex();
    }

    private static void stringVertex(float pX, float pY, float pZ, VertexConsumer vertexConsumer, PoseStack.Pose pose,
                                     float segmentStart, float segmentEnd) {
        float f = pX * segmentStart;
        float f1 = pY * (segmentStart * segmentStart + segmentStart) * 0.5F + 0.25F;
        float f2 = pZ * segmentStart;
        float f3 = pX * segmentEnd - f;
        float f4 = pY * (segmentEnd * segmentEnd + segmentEnd) * 0.5F + 0.0F - f1;
        float f5 = pZ * segmentEnd - f2;
        float f6 = Mth.sqrt(f3 * f3 + f4 * f4 + f5 * f5);
        f3 /= f6;
        f4 /= f6;
        f5 /= f6;
        vertexConsumer.vertex(pose.pose(), f, f1, f2).color(25, 25, 25, 255).normal(pose.normal(), f3, f4, f5).endVertex();
    }

    @Override
    public ResourceLocation getTextureLocation(GrapplingHookEntity entity) {
        return TEXTURE_LOCATION;
    }

}
