package com.mikitellurium.telluriumsrandomstuff.client.gui.screen;

import com.mikitellurium.telluriumsrandomstuff.client.gui.menu.SoulAnchorMenu;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SoulAnchorScreen extends AbstractContainerScreen<SoulAnchorMenu> {

    private static final ResourceLocation GUI_TEXTURE = FastLoc.modLoc("textures/gui/soul_anchor_gui.png");

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
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        int xPos = this.leftPos;
        int yPos = this.topPos - 25;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        graphics.blit(GUI_TEXTURE, xPos, yPos, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void render(GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(graphics);
        super.render(graphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(graphics, pMouseX, pMouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int pMouseX, int pMouseY) {
        super.renderLabels(graphics, pMouseX, pMouseY);
    }

}
