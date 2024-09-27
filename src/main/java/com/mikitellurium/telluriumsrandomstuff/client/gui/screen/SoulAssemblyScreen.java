package com.mikitellurium.telluriumsrandomstuff.client.gui.screen;

import com.mikitellurium.telluriumsrandomstuff.client.gui.menu.SoulAssemblyMenu;
import com.mikitellurium.telluriumsrandomstuff.networking.ModMessages;
import com.mikitellurium.telluriumsrandomstuff.networking.packets.SoulAssemblyModeC2SPacket;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SoulAssemblyScreen extends AbstractContainerScreen<SoulAssemblyMenu> {

    private static final ResourceLocation TEXTURE = FastLoc.modLoc("textures/gui/soul_assembly_table.png");

    public SoulAssemblyScreen(SoulAssemblyMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
        this.addRenderableWidget(new SwapButton(() -> {
            SoulAssemblyMenu.Mode mode = this.getMenu().getOppositeMode();
            this.getMenu().setMode(mode);
            ModMessages.sendToServer(new SoulAssemblyModeC2SPacket(mode));
        }, this.leftPos + 96, this.topPos + 59, 11, 11));
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        graphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        this.renderArrow(graphics);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);
        this.renderTooltip(graphics, mouseX, mouseY);
    }

    private void renderArrow(GuiGraphics graphics) {
        int yOffset = this.menu.getMode() == SoulAssemblyMenu.Mode.ASSEMBLE ? 0 : 17;
        graphics.blit(TEXTURE, this.leftPos + 90, this.topPos + 34, 176, yOffset, 24, 17);
    }

    private static class SwapButton extends AbstractButton {

        private final Runnable onPress;

        public SwapButton(Runnable onPress, int x, int y, int width, int height) {
            super(x, y, width, height, Component.empty());
            this.onPress = onPress;
        }

        @Override
        public void onPress() {
            this.onPress.run();
        }

        @Override
        protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
            int i = 34;
            if (this.isHovered()) {
                i += this.width;
            }
            graphics.blit(SoulAssemblyScreen.TEXTURE, this.getX(), this.getY(), 176, i, this.width, this.height);
        }

        @Override
        protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
        }

    }

}
