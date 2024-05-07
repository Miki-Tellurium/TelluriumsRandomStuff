package com.mikitellurium.telluriumsrandomstuff.integration.rei.util;

import com.mikitellurium.telluriumsrandomstuff.client.gui.util.BlockRendering;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.entry.renderer.EntryRenderer;
import me.shedaniel.rei.api.client.gui.widgets.Tooltip;
import me.shedaniel.rei.api.client.gui.widgets.TooltipContext;
import me.shedaniel.rei.api.common.entry.EntryStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

public class FluidBlockRenderer implements EntryRenderer<FluidStack> {

    @Override
    public void render(EntryStack<FluidStack> entry, GuiGraphics graphics, Rectangle bounds, int mouseX, int mouseY, float delta) {
        BlockRendering.renderFluid(graphics, entry.getValue(), bounds.x, bounds.y);
    }

    @Override
    public @Nullable Tooltip getTooltip(EntryStack<FluidStack> entry, TooltipContext context) {
        return Tooltip.create(entry.getValue().getDisplayName());
    }

}
