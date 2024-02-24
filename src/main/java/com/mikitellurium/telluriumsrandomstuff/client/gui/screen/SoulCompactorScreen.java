package com.mikitellurium.telluriumsrandomstuff.client.gui.screen;

import com.mikitellurium.telluriumsrandomstuff.client.gui.menu.SoulCompactorMenu;
import com.mikitellurium.telluriumsrandomstuff.client.gui.menu.SoulInfuserMenu;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SoulCompactorScreen extends AbstractSoulFuelScreen<SoulCompactorMenu> {

    public static final ResourceLocation GUI_TEXTURE = FastLoc.modLoc("textures/gui/soul_compactor_gui.png");

    public SoulCompactorScreen(SoulCompactorMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, GUI_TEXTURE, title, 176, 195, 8, 22);
    }

    @Override
    protected void init() {
        super.init();
        this.imageHeight = 195;
        this.topPos = (this.height - this.imageHeight) / 2;
        this.titleLabelX = 7;
        this.inventoryLabelY = 100;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        super.renderBg(graphics, partialTick, mouseX, mouseY);
        renderProgressArrow(graphics);
    }

    private void renderProgressArrow(GuiGraphics graphics) {
        if (this.menu.isCrafting()) {

        }
    }

}
