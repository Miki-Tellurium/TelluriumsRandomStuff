package com.mikitellurium.telluriumsrandomstuff.integration.rei.guielement;

import com.mikitellurium.telluriumsrandomstuff.integration.util.TickTimer;
import com.mikitellurium.telluriumsrandomstuff.lib.ITimer;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import me.shedaniel.math.Dimension;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.widgets.BurningFire;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.util.Mth;

import java.util.Collections;
import java.util.List;

public class SoulBrewingBubblesWidget extends BurningFire {

    private static final int[] BUBBLE_LENGTHS = new int[]{29, 23, 18, 13, 9, 5, 0};
    private final Rectangle bounds;
    private final ITimer internalTimer;

    public SoulBrewingBubblesWidget(int xPos, int yPos) {
        this.bounds = new Rectangle(new Point(xPos, yPos), new Dimension(11, 29));
        this.internalTimer = new TickTimer(14, BUBBLE_LENGTHS.length - 1, false);
    }

    @Override
    public Rectangle getBounds() {
        return this.bounds;
    }

    @Override
    public double getAnimationDuration() {
        return -1;
    }

    @Override
    public void setAnimationDuration(double animationDurationMS) {
    }

    private int getBubbleAnimationTick() {
        return this.internalTimer.getValue();
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        int tick = this.getBubbleAnimationTick();
        int length = BUBBLE_LENGTHS[tick];
        graphics.blit(FastLoc.GUI_ELEMENTS_TEXTURE, bounds.x, bounds.y + length, 89, length, bounds.width, bounds.height - length);
    }

    @Override
    public List<? extends GuiEventListener> children() {
        return Collections.emptyList();
    }

}
