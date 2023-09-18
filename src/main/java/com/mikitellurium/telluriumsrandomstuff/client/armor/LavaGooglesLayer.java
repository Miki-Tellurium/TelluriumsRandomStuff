package com.mikitellurium.telluriumsrandomstuff.client.armor;

import com.mikitellurium.telluriumsrandomstuff.common.content.item.LavaGooglesItem;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
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
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

public class LavaGooglesLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {

    private static final ResourceLocation GOOGLES_FRAME_TEXTURE =
            new ResourceLocation("telluriumsrandomstuff:textures/armor/lava_googles_frame_model.png");
    private static final ResourceLocation GOOGLES_NO_COLOR_TEXTURE =
            new ResourceLocation("telluriumsrandomstuff:textures/armor/lava_googles_nocolor_layer_model.png");
    private static final ResourceLocation GOOGLES_COLORED_TEXTURE =
            new ResourceLocation("telluriumsrandomstuff:textures/armor/lava_googles_color_layer_model.png");
    private final LavaGooglesModel<T> lavaGooglesModel;

    public LavaGooglesLayer(RenderLayerParent<T, M> parent, EntityModelSet modelSet) {
        super(parent);
        lavaGooglesModel = new LavaGooglesModel<>(modelSet.bakeLayer(LavaGooglesModel.LAVA_GOOGLES));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, T livingEntity,
                       float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks,
                       float netHeadYaw, float headPitch) {
        ItemStack itemStack = livingEntity.getItemBySlot(EquipmentSlot.HEAD);
        if (itemStack.is(ModItems.LAVA_GOOGLES.get())) {
            ResourceLocation glassToRender = GOOGLES_NO_COLOR_TEXTURE;
            float[] rgb = new float[] {1.0f, 1.0f, 1.0f};
            DyeColor dyeColor = LavaGooglesItem.getColor(itemStack);
            if (dyeColor != null) {
                rgb = dyeColor.getTextureDiffuseColors();
                glassToRender = GOOGLES_COLORED_TEXTURE;
            }


            poseStack.pushPose();

            if (livingEntity instanceof AbstractPiglin || livingEntity instanceof ZombifiedPiglin) {
                poseStack.scale(1.1f, 1.0f, 1.05f); // Handle piglin larger head
            }

            lavaGooglesModel.prepareMobModel(livingEntity, limbSwing, limbSwingAmount, partialTick);
            this.getParentModel().copyPropertiesTo(this.lavaGooglesModel);
            this.lavaGooglesModel.setupAnim(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            boolean isFoil = itemStack.hasFoil();
            // Render frame
            VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityTranslucent(GOOGLES_FRAME_TEXTURE));
            this.lavaGooglesModel.renderToBuffer(poseStack, vertexConsumer, packedLight,
                    OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            // Render colored glass
            vertexConsumer = bufferSource.getBuffer(RenderType.entityTranslucent(glassToRender));
            this.lavaGooglesModel.renderToBuffer(poseStack, vertexConsumer, packedLight,
                    OverlayTexture.NO_OVERLAY, rgb[0], rgb[1], rgb[2], 1.0F);
            // Render enchantment glint
            if (isFoil) {
                vertexConsumer = bufferSource.getBuffer(RenderType.entityGlintDirect());
                this.lavaGooglesModel.renderToBuffer(poseStack, vertexConsumer, packedLight,
                        OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            }
            poseStack.popPose();
        }
    }

}
