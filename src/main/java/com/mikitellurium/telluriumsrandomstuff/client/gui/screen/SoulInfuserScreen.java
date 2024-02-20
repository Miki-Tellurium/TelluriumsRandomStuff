package com.mikitellurium.telluriumsrandomstuff.client.gui.screen;

import com.mikitellurium.telluriumsrandomstuff.client.gui.menu.SoulInfuserMenu;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SoulInfuserScreen extends AbstractSoulFuelScreen<SoulInfuserMenu> {

    public static final ResourceLocation GUI_TEXTURE = FastLoc.modLoc("textures/gui/soul_infuser_gui.png");

    public SoulInfuserScreen(SoulInfuserMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, GUI_TEXTURE, title);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        super.renderBg(graphics, partialTick, mouseX, mouseY);
        renderProgressArrow(graphics);
    }

    private void renderProgressArrow(GuiGraphics graphics) {
        if (this.menu.isCrafting()) {
            int progress = this.menu.getScaledProgress(55);
            graphics.blit(AbstractSoulFuelScreen.ELEMENT_TEXTURE, leftPos + 54, topPos + 32, 176, 79,
                    progress + 1, 18);
        }
    }

}
