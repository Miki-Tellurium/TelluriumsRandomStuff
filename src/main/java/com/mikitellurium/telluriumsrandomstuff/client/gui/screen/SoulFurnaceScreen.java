package com.mikitellurium.telluriumsrandomstuff.client.gui.screen;

import com.mikitellurium.telluriumsrandomstuff.client.gui.menu.SoulFurnaceMenu;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.ArrayList;
import java.util.List;

public class SoulFurnaceScreen extends AbstractSoulFuelScreen<SoulFurnaceMenu> {

    private static final ResourceLocation GUI_TEXTURE = FastLoc.modLoc("textures/gui/soul_furnace_gui.png");
    private final List<ProgressBar> progressBars = Util.make(new ArrayList<>(), (list) -> {
        list.add(new ProgressBar(1, 62, 33));
        list.add(new ProgressBar(2, 85, 33));
        list.add(new ProgressBar(3, 62, 51));
        list.add(new ProgressBar(4, 85, 51));
    });

    public SoulFurnaceScreen(SoulFurnaceMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, GUI_TEXTURE, title, 176, 166, 8, 8);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        super.renderBg(graphics, partialTick, mouseX, mouseY);
        renderFire(graphics);
        renderProgress(graphics);
    }

    private void renderFire(GuiGraphics graphics) {
        if (this.menu.isLit()) {
            int time = this.menu.getScaledLitTime();
            graphics.blit(FastLoc.GUI_ELEMENTS_TEXTURE, leftPos + 58, topPos + 66 - time, 24,
                    12 - time, 14, time + 1);
        }
    }

    private void renderProgress(GuiGraphics graphics) {
        for (ProgressBar bar : progressBars) {
            if (this.menu.isCrafting(bar.dataSlot)) {
                int progress = this.menu.getScaledProgress(bar.dataSlot);
                graphics.blit(FastLoc.GUI_ELEMENTS_TEXTURE, leftPos + bar.xPos, topPos + bar.yPos - progress, 39,
                        15 - progress, 2, progress + 1);
            }
        }
    }

    private record ProgressBar(int dataSlot, int xPos, int yPos) {}

}
