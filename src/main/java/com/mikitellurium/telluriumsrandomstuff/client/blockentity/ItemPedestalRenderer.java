package com.mikitellurium.telluriumsrandomstuff.client.blockentity;

import com.mikitellurium.telluriumsrandomstuff.common.blockentity.ItemPedestalBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class ItemPedestalRenderer implements BlockEntityRenderer<ItemPedestalBlockEntity> {

    private static final int ITEM_NAME_TEXT_MAX_WIDTH = 60;
    private final ItemRenderer itemRenderer;
    private final EntityRenderDispatcher entityRenderer;
    private final Font font;

    public ItemPedestalRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
        this.entityRenderer = context.getEntityRenderer();
        this.font = context.getFont();
    }

    @Override
    public void render(ItemPedestalBlockEntity itemPedestal, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        if (!itemPedestal.isEmpty()) {
            ItemStack itemStack = itemPedestal.getItem();
            float itemScale = itemRenderer.getModel(itemStack, null, null, 0).isGui3d() ? 0.8F : 0.65F;
            // Item
            float f = (partialTick + (float) itemPedestal.getRotTick()) / 20.0F + itemPedestal.getRotOffset();
            poseStack.pushPose();

            poseStack.translate(0.5F, 1.3F, 0.5F);
            poseStack.scale(itemScale, itemScale, itemScale);
            poseStack.mulPose(Axis.YP.rotation(f));

            itemRenderer.renderStatic(itemStack, ItemDisplayContext.FIXED, packedLight, packedOverlay, poseStack,
                    bufferSource, itemPedestal.getLevel(), 0);

            poseStack.popPose();
            // Name display
            BlockPos pos = itemPedestal.getBlockPos();
            boolean isInDisplayDistance = pos.closerToCenterThan(
                    Minecraft.getInstance().player.blockPosition().getCenter(), 6.0D) && itemStack.hasCustomHoverName();
            if (isInDisplayDistance || itemPedestal.alwaysDisplayName()) {
                poseStack.pushPose();

                Component text = itemStack.getHoverName();
                int textColor = itemStack.getRarity().getStyleModifier().apply(text.getStyle()).getColor().getValue();
                float backgroundOpacity = Minecraft.getInstance().options.getBackgroundOpacity(0.25F);
                int backgroundColor = (int) (backgroundOpacity * 255.0F) << 24;

                poseStack.translate(0.5F, 1.9F, 0.5F);
                poseStack.mulPose(entityRenderer.cameraOrientation());
                poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
                poseStack.scale(0.02F, 0.02F, 0.02F);

                this.renderItemName(poseStack, bufferSource, text, textColor, backgroundColor, packedLight);

                poseStack.popPose();
            }
        }
    }

    private void renderItemName(PoseStack poseStack, MultiBufferSource bufferSource, Component component,
                                int textColor, int backGroundColor, int packedLight) {
        List<FormattedCharSequence> charSequenceList = font.split(component, ITEM_NAME_TEXT_MAX_WIDTH);

        int yPos = (-charSequenceList.size() + 1) * 10;
        for (FormattedCharSequence text : charSequenceList) {
            float xPos = (float) (-font.width(text) / 2);
            font.drawInBatch(text, xPos, yPos, textColor, false, poseStack.last().pose(), bufferSource,
                    Font.DisplayMode.NORMAL, backGroundColor, packedLight);
            yPos += 10;
        }
    }

}
