package com.mikitellurium.telluriumsrandomstuff.client.blockentity;

import com.mikitellurium.telluriumsrandomstuff.common.content.blockentity.ItemPedestalBlockEntity;
import com.mikitellurium.telluriumsrandomstuff.util.LogUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemDisplayContext;

public class ItemPedestalRenderer implements BlockEntityRenderer<ItemPedestalBlockEntity> {

    private final ItemRenderer itemRenderer;

    public ItemPedestalRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(ItemPedestalBlockEntity itemPedestal, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        //LogUtils.consoleLogMessage("Item:" + itemPedestal.getItem().getDisplayName().getString());
        if (!itemPedestal.isEmpty()) {
            poseStack.pushPose();

            poseStack.translate(0.5F, 1.275F, 0.5F);
            poseStack.scale(0.65F, 0.65F, 0.65F);
            itemRenderer.renderStatic(itemPedestal.getItem(), ItemDisplayContext.FIXED, packedLight, packedOverlay,
                    poseStack, bufferSource, itemPedestal.getLevel(), 0);

            poseStack.popPose();
        }
    }

}
