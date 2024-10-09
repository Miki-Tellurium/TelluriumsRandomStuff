package com.mikitellurium.telluriumsrandomstuff.integration.rei.util;

import com.mikitellurium.telluriumsrandomstuff.client.hud.render.BlockRendering;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.entry.renderer.EntryRenderer;
import me.shedaniel.rei.api.client.gui.widgets.Tooltip;
import me.shedaniel.rei.api.client.gui.widgets.TooltipContext;
import me.shedaniel.rei.api.common.entry.EntryStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.Nullable;

public class FluidBlockRenderer implements EntryRenderer<Fluid> {

    @Override
    public void render(EntryStack<Fluid> entry, GuiGraphics graphics, Rectangle bounds, int mouseX, int mouseY, float delta) {
        BlockRendering.renderFluid(graphics, entry.getValue(), bounds.x, bounds.y);
    }

    @Override
    public @Nullable Tooltip getTooltip(EntryStack<Fluid> entry, TooltipContext context) {
        return Tooltip.create(entry.getValue().getFluidType().getDescription());
    }

}
