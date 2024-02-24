package com.mikitellurium.telluriumsrandomstuff.client.gui.screen;

import com.mikitellurium.telluriumsrandomstuff.client.gui.menu.SoulFurnaceMenu;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SoulFurnaceScreen extends AbstractSoulFuelScreen<SoulFurnaceMenu> {

    public static final ResourceLocation GUI_TEXTURE = FastLoc.modLoc("textures/gui/soul_furnace_gui.png");

    public SoulFurnaceScreen(SoulFurnaceMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, GUI_TEXTURE, title, 176, 166, 8, 8);
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
            graphics.blit(AbstractSoulFuelScreen.ELEMENT_TEXTURE, leftPos + 57, topPos + 61 - time, 176,
                    12 - time, 14, time + 1);
        }
    }

    private void renderProgressArrow(GuiGraphics graphics) {
        if (this.menu.isCrafting()) {
            int progress = this.menu.getScaledProgress(24);
            graphics.blit(AbstractSoulFuelScreen.ELEMENT_TEXTURE, leftPos + 79, topPos + 29, 176, 14,
                    progress + 1, 16);
        }
    }

}
