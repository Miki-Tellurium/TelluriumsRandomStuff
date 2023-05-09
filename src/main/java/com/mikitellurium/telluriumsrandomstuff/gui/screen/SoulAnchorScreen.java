package com.mikitellurium.telluriumsrandomstuff.gui.screen;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.gui.menu.SoulAnchorMenu;
import com.mikitellurium.telluriumsrandomstuff.gui.menu.SoulFurnaceMenu;
import com.mikitellurium.telluriumsrandomstuff.gui.render.FluidRenderer;
import com.mikitellurium.telluriumsrandomstuff.util.MouseUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.List;
import java.util.Optional;

public class SoulAnchorScreen extends AbstractContainerScreen<SoulAnchorMenu> {

    public static final ResourceLocation GUI_TEXTURE =
            new ResourceLocation(TelluriumsRandomStuffMod.MOD_ID, "textures/gui/soul_anchor_gui.png");

    public SoulAnchorScreen(SoulAnchorMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        this.imageWidth = 176;
        this.imageHeight = 205;
        this.titleLabelX = 7;
        this.titleLabelY = -20;
        this.inventoryLabelX = 7;
        this.inventoryLabelY = 86;
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTick, int mouseX, int mouseY) {
        int xPos = this.leftPos;
        int yPos = this.topPos - 25;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        blit(poseStack, xPos, yPos, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pPoseStack, pMouseX, pMouseY);
    }

    @Override
    protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        super.renderLabels(pPoseStack, pMouseX, pMouseY);
    }

}
