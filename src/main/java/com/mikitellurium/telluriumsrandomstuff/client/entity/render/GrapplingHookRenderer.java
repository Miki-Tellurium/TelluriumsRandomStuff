package com.mikitellurium.telluriumsrandomstuff.client.entity.render;

import com.mikitellurium.telluriumsrandomstuff.client.entity.model.GrapplingHookModel;
import com.mikitellurium.telluriumsrandomstuff.common.entity.GrapplingHookEntity;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;

public class GrapplingHookRenderer extends EntityRenderer<GrapplingHookEntity> {

    private static final ResourceLocation TEXTURE_LOCATION = FastLoc.modLoc("textures/entity/grapplig_hook.png");
    private final GrapplingHookModel model;

    public GrapplingHookRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new GrapplingHookModel(context.bakeLayer(GrapplingHookModel.LAYER_LOCATION));
    }

    @Override
    public void render(GrapplingHookEntity grapplingHook, float yaw, float partialTicks, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        poseStack.mulPose(new Quaternionf().rotationXYZ(0, grapplingHook.getYRot() * ((float) Math.PI / 180), 0));
        poseStack.mulPose(Axis.XN.rotationDegrees(grapplingHook.getViewXRot(partialTicks)));
        poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
        VertexConsumer vertexconsumer = ItemRenderer.getFoilBufferDirect(bufferSource,
                this.model.renderType(this.getTextureLocation(grapplingHook)), false, grapplingHook.isFoil());
        this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();

        Player player = grapplingHook.getPlayerOwner();
        if (player != null) {
            ItemStack itemstack = player.getMainHandItem();
            poseStack.pushPose();
            int i = player.getMainArm() == HumanoidArm.RIGHT ? 1 : -1;
            if (!itemstack.is(ModItems.GRAPPLING_HOOK.get())) {
                i = -i;
            }
            float f = player.getAttackAnim(partialTicks);
            float f1 = Mth.sin(Mth.sqrt(f) * (float) Math.PI);
            float f2 = Mth.lerp(partialTicks, player.yBodyRotO, player.yBodyRot) * ((float) Math.PI / 180F);
            double d0 = Mth.sin(f2);
            double d1 = Mth.cos(f2);
            double d2 = (double) i * 0.35D;
            double x;
            double y;
            double z;
            float height;
            if (this.entityRenderDispatcher.options.getCameraType().isFirstPerson() && player == Minecraft.getInstance().player) {
                double d7 = 960.0D / (double) this.entityRenderDispatcher.options.fov().get();
                Vec3 vec3 = this.entityRenderDispatcher.camera.getNearPlane().getPointOnPlane((float) i * 0.525F, -0.1F);
                vec3 = vec3.scale(d7);
                vec3 = vec3.yRot(f1 * 0.5F);
                vec3 = vec3.xRot(-f1 * 0.7F);
                x = Mth.lerp(partialTicks, player.xo, player.getX()) + vec3.x;
                y = Mth.lerp(partialTicks, player.yo, player.getY()) + vec3.y;
                z = Mth.lerp(partialTicks, player.zo, player.getZ()) + vec3.z;
                height = player.getEyeHeight();
            } else {
                x = Mth.lerp(partialTicks, player.xo, player.getX()) - d1 * d2 - d0 * 0.6D;
                y = player.yo + (double) player.getEyeHeight() + (player.getY() - player.yo) * (double) partialTicks - 0.45D;
                z = Mth.lerp(partialTicks, player.zo, player.getZ()) - d0 * d2 + d1 * 0.6D;
                height = player.isCrouching() ? -0.1875F : 0.0F;
            }

            float angle = (float) Math.sin(player.getXRot() * (Math.PI / 180.0F));
            float angleLerp = Mth.lerp(Math.abs(angle), 0.0F, 0.4F) * Math.signum(angle);

            double lerpX = Mth.lerp(partialTicks, grapplingHook.xo, grapplingHook.getX());
            double lerpY = Mth.lerp(partialTicks, grapplingHook.yo, grapplingHook.getY()) + 0.47D + angleLerp;
            double lerpZ = Mth.lerp(partialTicks, grapplingHook.zo, grapplingHook.getZ());
            float pX = (float) (x - lerpX);
            float pY = (float) (y - lerpY) + height;
            float pZ = (float) (z - lerpZ);
            VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.lineStrip());
            PoseStack.Pose pose = poseStack.last();

            final int segments = 16;
            for (int k = 0; k <= segments; ++k) {
                stringVertex(pX, pY, pZ, vertexConsumer, pose, fraction(k, segments), fraction(k + 1, segments));
            }

            poseStack.popPose();
            super.render(grapplingHook, yaw, partialTicks, poseStack, bufferSource, packedLight);
        }
    }

    private static float fraction(int numerator, int denominator) {
        return (float)numerator / (float)denominator;
    }

    private static void stringVertex(float pX, float pY, float pZ, VertexConsumer vertexConsumer, PoseStack.Pose pose,
                                     float segmentStart, float segmentEnd) {
        float f = pX * segmentStart;
        float f1 = pY * (segmentStart * segmentStart + segmentStart) * 0.5F + 0.25F;
        float f2 = pZ * segmentStart;
        float f3 = pX * segmentEnd - f;
        float f4 = pY * (segmentEnd * segmentEnd + segmentEnd) * 0.5F + 0.25F - f1;
        float f5 = pZ * segmentEnd - f2;
        float f6 = Mth.sqrt(f3 * f3 + f4 * f4 + f5 * f5);
        f3 /= f6;
        f4 /= f6;
        f5 /= f6;
        vertexConsumer.vertex(pose.pose(), f, f1, f2).color(0, 0, 0, 255).normal(pose.normal(), f3, f4, f5).endVertex();
    }

    @Override
    public ResourceLocation getTextureLocation(GrapplingHookEntity entity) {
        return TEXTURE_LOCATION;
    }

}
