package com.mikitellurium.telluriumsrandomstuff.integration.rei.guielement;

import com.mikitellurium.telluriumsrandomstuff.client.gui.util.GuiFluidRenderer;
import com.mikitellurium.telluriumsrandomstuff.registry.ModFluids;
import me.shedaniel.math.Dimension;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.widgets.BurningFire;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.util.Mth;
import net.minecraftforge.fluids.FluidStack;

import java.util.Collections;
import java.util.List;

public class SoulLavaBuretteWidget extends BurningFire {

    private final Rectangle bounds;
    private double animationDuration = -1;

    public SoulLavaBuretteWidget(int xPos, int yPos) {
        this.bounds = new Rectangle(new Point(xPos, yPos), new Dimension(8, 36));
    }

    @Override
    public Rectangle getBounds() {
        return this.bounds;
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
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        if (this.getAnimationDuration() > 0) {
            int amount = bounds.height - Mth.ceil((System.currentTimeMillis() / (animationDuration / bounds.height) % (double)bounds.height));
            GuiFluidRenderer.drawBackground(graphics, bounds.x, bounds.y, new FluidStack(ModFluids.SOUL_LAVA_SOURCE.get(), amount),
                    bounds.height, bounds.width, bounds.height);
        }
    }

    @Override
    public List<? extends GuiEventListener> children() {
        return Collections.emptyList();
    }

}
