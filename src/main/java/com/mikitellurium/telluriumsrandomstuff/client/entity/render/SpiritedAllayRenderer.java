package com.mikitellurium.telluriumsrandomstuff.client.entity.render;

import com.mikitellurium.telluriumsrandomstuff.client.entity.model.SpiritedAllayModel;
import com.mikitellurium.telluriumsrandomstuff.common.entity.SpiritedAllay;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import org.joml.Matrix4f;

import java.util.function.Function;

public class SpiritedAllayRenderer extends MobRenderer<SpiritedAllay, SpiritedAllayModel> {

    private static final Function<DyeColor, ResourceLocation> TEXTURE = (dyeColor) ->
        FastLoc.modLoc("textures/entity/spirited_allay/spirited_allay_" + dyeColor.getName() + ".png");
    private final EntityRenderDispatcher entityRenderer;

    public SpiritedAllayRenderer(EntityRendererProvider.Context context) {
        super(context, new SpiritedAllayModel(context.bakeLayer(SpiritedAllayModel.LAYER_LOCATION)), 0.4F);
        this.entityRenderer = context.getEntityRenderDispatcher();
    }

    @Override
    public void render(SpiritedAllay allay, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        super.render(allay, yaw, partialTicks, poseStack, bufferSource, packedLight);
        poseStack.pushPose();
        poseStack.translate(0.0F, 0.9F, 0.0F);
        poseStack.mulPose(entityRenderer.cameraOrientation());
        poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
        poseStack.scale(0.025F, 0.025F, 0.025F);
        Matrix4f matrix4f = poseStack.last().pose();
        float opacity = Minecraft.getInstance().options.getBackgroundOpacity(0.25f);
        int finalOpacity = (int)(opacity * 255.0f) << 24;
        String text = allay.getColor().getName();
        int x = this.getFont().width(text) / 2;
        this.getFont().drawInBatch(text, -x, 0, -1, false, matrix4f, bufferSource,
                Font.DisplayMode.NORMAL, finalOpacity, packedLight);
        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(SpiritedAllay allay) {
        return TEXTURE.apply(allay.getColor());
    }

    @Override
    protected int getBlockLightLevel(SpiritedAllay spiritedAllay, BlockPos pos) {
        return 15;
    }

}
