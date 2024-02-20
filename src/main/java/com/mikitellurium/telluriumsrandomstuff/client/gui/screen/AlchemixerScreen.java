package com.mikitellurium.telluriumsrandomstuff.client.gui.screen;

import com.mikitellurium.telluriumsrandomstuff.client.gui.menu.AlchemixerMenu;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class AlchemixerScreen extends AbstractSoulFuelScreen<AlchemixerMenu>{

    public static final ResourceLocation GUI_TEXTURE = FastLoc.modLoc("textures/gui/alchemixer_gui.png");

    public AlchemixerScreen(AlchemixerMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, GUI_TEXTURE, title);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float pPartialTick, int pMouseX, int pMouseY) {
        super.renderBg(graphics, pPartialTick, pMouseX, pMouseY);
        this.renderProgress(graphics);
    }
    // todo write recipe cost in gui
    private void renderProgress(GuiGraphics graphics) {
        if (this.menu.isCrafting()) {
            int progress = this.menu.getScaledProgress(18);
            graphics.blit(AbstractSoulFuelScreen.ELEMENT_TEXTURE, leftPos + 93, topPos + 38, 192, 31,
                    12, progress + 1);
        }
    }

}
