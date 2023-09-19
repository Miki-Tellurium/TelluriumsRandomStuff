package com.mikitellurium.telluriumsrandomstuff.client.blockentity;

import com.mikitellurium.telluriumsrandomstuff.common.content.blockentity.ItemPedestalBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class ItemPedestalRenderer implements BlockEntityRenderer<ItemPedestalBlockEntity> {

    private final ItemRenderer itemRenderer;

    public ItemPedestalRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(ItemPedestalBlockEntity itemPedestal, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        if (!itemPedestal.isEmpty()) {
            ItemStack itemStack = itemPedestal.getItem();
            float itemScale = itemRenderer.getModel(itemStack, null, null, 0).isGui3d() ? 0.8F : 0.65F;

            poseStack.pushPose();

            poseStack.translate(0.5F, 1.3F, 0.5F);
            poseStack.scale(itemScale, itemScale, itemScale);
            float f = (partialTick + (float) itemPedestal.getRotTick()) / 20.0F + itemPedestal.getRotOffset();
            poseStack.mulPose(Axis.YP.rotation(f));
            itemRenderer.renderStatic(itemStack, ItemDisplayContext.FIXED, packedLight, packedOverlay, poseStack,
                    bufferSource, itemPedestal.getLevel(), 0);

            poseStack.popPose();
        }
    }

}
