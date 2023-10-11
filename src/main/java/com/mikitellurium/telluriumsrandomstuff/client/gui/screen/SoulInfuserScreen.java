package com.mikitellurium.telluriumsrandomstuff.client.gui.screen;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.client.gui.menu.SoulFurnaceMenu;
import com.mikitellurium.telluriumsrandomstuff.client.gui.menu.SoulInfuserMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SoulInfuserScreen extends AbstractSoulFurnaceScreen<SoulInfuserMenu> {

    public static final ResourceLocation GUI_TEXTURE =
            new ResourceLocation(TelluriumsRandomStuffMod.MOD_ID, "textures/gui/soul_infuser_gui.png");

    public SoulInfuserScreen(SoulInfuserMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, GUI_TEXTURE, title);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        int xPos = this.leftPos;
        int yPos = this.topPos + 2;
        super.renderBg(graphics, partialTick, mouseX, mouseY);
        renderProgressArrow(graphics, xPos, yPos);
    }

    private void renderProgressArrow(GuiGraphics graphics, int xPos, int yPos) {
        if (this.menu.isCrafting()) {
            int progress = this.menu.getScaledProgress(55);
            graphics.blit(AbstractSoulFurnaceScreen.ELEMENT_TEXTURE, xPos + 54, yPos + 33, 176, 79,
                    progress + 1, 18);
        }
    }

}
