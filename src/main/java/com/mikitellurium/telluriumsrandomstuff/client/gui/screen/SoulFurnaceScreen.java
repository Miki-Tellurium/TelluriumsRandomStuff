package com.mikitellurium.telluriumsrandomstuff.client.gui.screen;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.client.gui.menu.SoulFurnaceMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SoulFurnaceScreen extends AbstractSoulFurnaceScreen<SoulFurnaceMenu> {

    public static final ResourceLocation GUI_TEXTURE =
            new ResourceLocation(TelluriumsRandomStuffMod.MOD_ID, "textures/gui/soul_furnace_gui.png");

    public SoulFurnaceScreen(SoulFurnaceMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, GUI_TEXTURE, title);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        super.renderBg(graphics, partialTick, mouseX, mouseY);
        renderFire(graphics);
        renderProgressArrow(graphics);
    }

    private void renderFire(GuiGraphics graphics) {
        if (this.menu.isLit()) {
            int time = this.menu.getScaledLitTime();
            graphics.blit(AbstractSoulFurnaceScreen.ELEMENT_TEXTURE, leftPos + 57, topPos + 61 - time, 176,
                    12 - time, 14, time + 1);
        }
    }

    private void renderProgressArrow(GuiGraphics graphics) {
        if (this.menu.isCrafting()) {
            int progress = this.menu.getScaledProgress(24);
            graphics.blit(AbstractSoulFurnaceScreen.ELEMENT_TEXTURE, leftPos + 79, topPos + 29, 176, 14,
                    progress + 1, 16);
        }
    }

}
