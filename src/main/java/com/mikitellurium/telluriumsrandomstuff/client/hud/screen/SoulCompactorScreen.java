package com.mikitellurium.telluriumsrandomstuff.client.hud.screen;

import com.mikitellurium.telluriumsrandomstuff.client.hud.menu.SoulCompactorMenu;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SoulCompactorScreen extends AbstractSoulFuelScreen<SoulCompactorMenu> {

    private static final ResourceLocation GUI_TEXTURE = FastLoc.modLoc("textures/gui/soul_compactor_gui.png");

    public SoulCompactorScreen(SoulCompactorMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, GUI_TEXTURE, title, 176, 166, 8, 8);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        super.renderBg(graphics, partialTick, mouseX, mouseY);
        renderProgressArrow(graphics);
    }

    private void renderProgressArrow(GuiGraphics graphics) {
        if (this.menu.isCrafting()) {
            int progress = this.menu.getScaledProgress(32);
            graphics.blit(FastLoc.GUI_ELEMENTS_TEXTURE, leftPos + 76, topPos + 26, 0, 53,
                    progress + 1, 23);
        }
    }

}
