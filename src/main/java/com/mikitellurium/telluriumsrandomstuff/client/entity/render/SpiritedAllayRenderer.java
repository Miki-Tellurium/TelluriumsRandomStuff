package com.mikitellurium.telluriumsrandomstuff.client.entity.render;

import com.mikitellurium.telluriumsrandomstuff.client.entity.model.SpiritedAllayModel;
import com.mikitellurium.telluriumsrandomstuff.common.entity.SpiritedAllay;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;

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
