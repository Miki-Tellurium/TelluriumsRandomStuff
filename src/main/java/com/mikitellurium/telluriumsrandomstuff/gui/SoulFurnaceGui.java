package com.mikitellurium.telluriumsrandomstuff.gui;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.gui.render.FluidRenderer;
import com.mikitellurium.telluriumsrandomstuff.util.MouseUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.List;
import java.util.Optional;

public class SoulFurnaceGui extends AbstractContainerScreen<SoulFurnaceMenu> {

    public static final ResourceLocation GUI_TEXTURE =
            new ResourceLocation(TelluriumsRandomStuffMod.MOD_ID, "textures/gui/soul_furnace_gui.png");

    private Rect2i soulLavaStorage;

    public SoulFurnaceGui(SoulFurnaceMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        int xPos = this.leftPos;
        int yPos = this.topPos + 2;
        soulLavaStorage = new Rect2i(xPos + 8, yPos + 8, 16, 48);
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
        this.inventoryLabelX = 28;
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        int xPos = this.leftPos;
        int yPos = this.topPos + 2;
        int textureWidth = 176;
        int textureHeight = 166;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        blit(pPoseStack, xPos, yPos, 0, 0, textureWidth, textureHeight);

        renderFire(pPoseStack, xPos, yPos);
        renderProgressArrow(pPoseStack, xPos, yPos);
        renderSoulLavaStorage(soulLavaStorage.getX(), soulLavaStorage.getY());
        renderGlass(pPoseStack, xPos, yPos);
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
        int xPos = this.leftPos;
        int yPos = this.topPos + 2;
        renderFluidAreaTooltips(pPoseStack, pMouseX, pMouseY, xPos, yPos);
    }

    private void renderFire(PoseStack pPoseStack, int xPos, int yPos) {
        if (this.menu.isLit()) {
            int time = this.menu.getScaledLitTime();
            blit(pPoseStack, xPos + 58, yPos + 62 - time, 176, 12 - time, 14, time + 1);
        }
    }

    private void renderProgressArrow(PoseStack poseStack, int xPos, int yPos) {
        if (this.menu.isCrafting()) {
            int progress = this.menu.getScaledProgress();
            blit(poseStack, xPos + 79, yPos + 29, 176, 14, progress + 1, 16);
        }
    }

    private void renderSoulLavaStorage(int xPos, int yPos) {
        FluidRenderer.drawBackground(xPos, yPos, this.menu.getFluidStack(), this.menu.getBlockEntity().getMaxFluidCapacity(),
                soulLavaStorage.getWidth(), soulLavaStorage.getHeight());
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
    }

    private void renderGlass(PoseStack poseStack, int xPos, int yPos) {
        blit(poseStack, soulLavaStorage.getX(), soulLavaStorage.getY(), 176, 31,
                soulLavaStorage.getWidth(), soulLavaStorage.getHeight());
    }

    private void renderFluidAreaTooltips(PoseStack pPoseStack, int pMouseX, int pMouseY, int x, int y) {
        if(MouseUtils.isAboveArea(pMouseX, pMouseY, soulLavaStorage.getX(), soulLavaStorage.getY(),
                soulLavaStorage.getWidth(), soulLavaStorage.getHeight())) {
            renderTooltip(pPoseStack, this.getTooltips(),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }

    public List<Component> getTooltips() {
        return List.of(Component.translatable("fluid_type.telluriumsrandomstuff.soul_lava_fluid"),
                Component.literal( menu.getFluidStack().getAmount() + "/" + menu.getBlockEntity().getMaxFluidCapacity()));
    }

    public Rect2i getSoulLavaStorage() {
        return soulLavaStorage;
    }

}
