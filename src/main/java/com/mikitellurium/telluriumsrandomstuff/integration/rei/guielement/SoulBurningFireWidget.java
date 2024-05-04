package com.mikitellurium.telluriumsrandomstuff.integration.rei.guielement;

import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.widgets.BurningFire;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.util.Mth;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class SoulBurningFireWidget extends BurningFire {

    private Rectangle bounds;
    private double animationDuration = -1;

    public SoulBurningFireWidget(Rectangle bounds) {
        this.bounds = new Rectangle(Objects.requireNonNull(bounds));
    }

    @Override
    public double getAnimationDuration() {
        return animationDuration;
    }

    @Override
    public void setAnimationDuration(double milliSeconds) {
        this.animationDuration = milliSeconds;
        if (this.animationDuration <= 0)
            this.animationDuration = -1;
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        graphics.blit(FastLoc.JEI_GUI_TEXTURE, getX(), getY(), 37, 43, 14, 14);
        if (this.getAnimationDuration() > 0) {
            int height = 14 - Mth.ceil((System.currentTimeMillis() / (animationDuration / 14) % 14D));
            graphics.blit(FastLoc.GUI_ELEMENTS_TEXTURE, getX() - 1, getY() + 14 - height, 24, 14 - height,
                    14, height);
        }
    }

    @Override
    public List<? extends GuiEventListener> children() {
        return Collections.emptyList();
    }

}
