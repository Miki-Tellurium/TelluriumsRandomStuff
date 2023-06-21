package com.mikitellurium.telluriumsrandomstuff.client.renderer;

import com.google.common.collect.Maps;
import com.mikitellurium.telluriumsrandomstuff.common.content.item.LavaGooglesItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;

import javax.annotation.Nullable;
import java.util.Map;

//public class LavaGooglesLayer<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> extends RenderLayer<T, M> {

//    private static final Map<String, ResourceLocation> ARMOR_LOCATION_CACHE = Maps.newHashMap();
//    private final A model;
//
//    public LavaGooglesLayer(RenderLayerParent<T, M> parent, A model) {
//        super(parent);
//        this.model = model;
//    }
//
//    @Override
//    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int i, T livingEntity, float f,
//                       float f1, float f2, float f3, float f4, float f5) {
//        this.renderTranslucentArmor(poseStack, bufferSource, livingEntity, EquipmentSlot.HEAD, i, this.model);
//    }
//
//    private void renderTranslucentArmor(PoseStack poseStack, MultiBufferSource bufferSource, T entity,
//                                        EquipmentSlot slot, int i, A humanoidModel) {
//        ItemStack itemstack = entity.getItemBySlot(slot);
//        Item equippedItem = itemstack.getItem();
//        if (equippedItem instanceof LavaGooglesItem lavaGoogles) {
//            if (lavaGoogles.getEquipmentSlot() == slot) {
//                this.getParentModel().copyPropertiesTo(humanoidModel);
//                this.setPartVisibility(humanoidModel);
//                Model model = ForgeHooksClient.getArmorModel(entity, itemstack, slot, humanoidModel);
//                this.renderModel(poseStack, bufferSource, i, model, false, 1.0F, 1.0F, 1.0F,
//                        this.getArmorResource(entity, itemstack, slot, null));
//                if (itemstack.hasFoil()) {
//                    this.renderGlint(poseStack, bufferSource, i, model);
//                }
//            }
//        }
//
//    }
//
//    private void renderModel(PoseStack poseStack, MultiBufferSource bufferSource, int i,
//                             Model model, boolean b, float f, float f2, float f3, ResourceLocation armorResource) {
//        VertexConsumer vertexconsumer = ItemRenderer.getArmorFoilBuffer(bufferSource,
//                RenderType.entityTranslucent(armorResource), false, b);
//        model.renderToBuffer(poseStack, vertexconsumer, i, OverlayTexture.NO_OVERLAY,
//                f, f2, f3, 1.0F);
//    }
//
//    private void renderGlint(PoseStack poseStack, MultiBufferSource bufferSource, int i, Model model) {
//        model.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.armorEntityGlint()), i,
//                OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
//    }
//
//    protected void setPartVisibility(A model) {
//        model.setAllVisible(true);
//    }
//
//    public ResourceLocation getArmorResource(Entity entity, ItemStack stack, EquipmentSlot slot, @Nullable String type) {
//        ArmorItem item = (ArmorItem)stack.getItem();
//        String texture = item.getMaterial().getName();
//        String domain = "minecraft";
//        int idx = texture.indexOf(':');
//        if (idx != -1) {
//            domain = texture.substring(0, idx);
//            texture = texture.substring(idx + 1);
//        }
//        String s1 = String.format(java.util.Locale.ROOT, "%s:textures/models/armor/%s_layer_%d%s.png",
//                domain, texture, 1, type == null ? "" : String.format(java.util.Locale.ROOT, "_%s", type));
//
//        s1 = net.minecraftforge.client.ForgeHooksClient.getArmorTexture(entity, stack, s1, slot, type);
//        ResourceLocation resourcelocation = ARMOR_LOCATION_CACHE.get(s1);
//
//        if (resourcelocation == null) {
//            resourcelocation = new ResourceLocation(s1);
//            ARMOR_LOCATION_CACHE.put(s1, resourcelocation);
//        }
//
//        return resourcelocation;
//    }

//}
