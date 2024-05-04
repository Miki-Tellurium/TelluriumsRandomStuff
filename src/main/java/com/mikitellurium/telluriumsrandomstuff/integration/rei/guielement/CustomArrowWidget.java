package com.mikitellurium.telluriumsrandomstuff.integration.rei.guielement;

import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.widgets.Arrow;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CustomArrowWidget extends Arrow {

    private double animationDuration = -1;
    private final Rectangle bounds;
    private final ResourceLocation sprite;
    private final Point pos;

    public CustomArrowWidget(ResourceLocation sprite, Point pos, Rectangle bounds) {
        this.sprite = sprite;
        this.pos = new Point(Objects.requireNonNull(pos));
        this.bounds = new Rectangle(Objects.requireNonNull(bounds));
    }

    @Override
    public double getAnimationDuration() {
        return this.animationDuration;
    }

    @Override
    public void setAnimationDuration(double milliSeconds) {
        this.animationDuration = milliSeconds;
        if (this.animationDuration <= 0)
            this.animationDuration = -1;
    }

    public ResourceLocation getSprite() {
        return sprite;
    }

    public Point getUV() {
        return pos;
    }

    @Override
    public Rectangle getBounds() {
        return this.bounds;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        if (getAnimationDuration() > 0) {
            int width = Mth.ceil((System.currentTimeMillis() / (animationDuration / bounds.width) % (double)bounds.width));
            graphics.blit(this.sprite, pos.x, pos.y, bounds.x, bounds.y, width, bounds.height);
        }
    }

    @Override
    public List<? extends GuiEventListener> children() {
        return Collections.emptyList();
    }

}
