package com.mikitellurium.telluriumsrandomstuff.integration.rei.guielement;

import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.widgets.BaseWidget;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;

import java.util.List;

public class SoulLavaTankWidget extends BaseWidget<SoulLavaTankWidget> {

    @Override
    public Rectangle getBounds() {
        return null;
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {

    }

    @Override
    public List<? extends GuiEventListener> children() {
        return List.of();
    }

}
