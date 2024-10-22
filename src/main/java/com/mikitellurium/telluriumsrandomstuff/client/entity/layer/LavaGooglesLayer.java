package com.mikitellurium.telluriumsrandomstuff.client.entity.layer;

import com.mikitellurium.telluriumsrandomstuff.client.entity.model.LavaGooglesModel;
import com.mikitellurium.telluriumsrandomstuff.common.item.LavaGooglesItem;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import com.mikitellurium.telluriumsrandomstuff.util.ColorsUtil;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

public class LavaGooglesLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {

    private static final ResourceLocation GOOGLES_FRAME_TEXTURE = FastLoc.modLoc("textures/models/armor/lava_googles_frame_model.png");
    private static final ResourceLocation GOOGLES_NO_COLOR_TEXTURE = FastLoc.modLoc("textures/models/armor/lava_googles_nocolor_layer_model.png");
    private static final ResourceLocation GOOGLES_COLORED_TEXTURE = FastLoc.modLoc("textures/models/armor/lava_googles_color_layer_model.png");
    private final LavaGooglesModel<T> model;

    public LavaGooglesLayer(RenderLayerParent<T, M> parent, EntityModelSet modelSet) {
        super(parent);
        this.model = new LavaGooglesModel<>(modelSet.bakeLayer(LavaGooglesModel.LAYER_LOCATION));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, T livingEntity,
                       float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks,
                       float netHeadYaw, float headPitch) {
        ItemStack itemStack = livingEntity.getItemBySlot(EquipmentSlot.HEAD);
        if (itemStack.is(ModItems.LAVA_GOOGLES.get())) {
            float[] rgb = new float[] {1.0f, 1.0f, 1.0f};
            ResourceLocation glassTexture = GOOGLES_NO_COLOR_TEXTURE;
            DyeColor dyeColor = LavaGooglesItem.getColor(itemStack);
            if (dyeColor != null) {
                rgb = dyeColor.getTextureDiffuseColors();
                glassTexture = GOOGLES_COLORED_TEXTURE;
            }
            if (itemStack.getHoverName().getString().equals("tellurio_")) {
                int i = livingEntity.tickCount / 25 + livingEntity.getId();
                int colors = DyeColor.values().length;
                int j = i % colors;
                int k = (i + 1) % colors;
                float f3 = ((float) (livingEntity.tickCount % 25) + partialTick) / 25.0F;
                int color1 = ColorsUtil.getDyeColorAsInt(DyeColor.byId(j));
                int color2 = ColorsUtil.getDyeColorAsInt(DyeColor.byId(k));
                int finalColor = FastColor.ARGB32.lerp(f3, color1, color2);
                rgb = ColorsUtil.getRgbComponents(finalColor);
            }

            poseStack.pushPose();
            if (livingEntity instanceof AbstractPiglin || livingEntity instanceof ZombifiedPiglin) {
                poseStack.scale(1.1f, 1.0f, 1.05f); // Handle piglin larger head
            }
            this.model.prepareMobModel(livingEntity, limbSwing, limbSwingAmount, partialTick);
            this.getParentModel().copyPropertiesTo(this.model);
            this.model.setupAnim(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            this.renderFrame(poseStack, bufferSource, packedLight);
            this.renderGlass(poseStack, glassTexture, rgb, bufferSource, packedLight);
            if (itemStack.hasFoil()) {
                this.renderGlint(poseStack, bufferSource, packedLight);
            }
            poseStack.popPose();
        }
    }

    private void renderFrame(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityTranslucent(GOOGLES_FRAME_TEXTURE));
        this.model.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    private void renderGlass(PoseStack poseStack, ResourceLocation texture, float[] rgb, MultiBufferSource bufferSource, int packedLight) {
        VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityTranslucent(texture));
        this.model.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, rgb[0], rgb[1], rgb[2], 1.0F);
    }

    private void renderGlint(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityGlintDirect());
        this.model.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }

}
