package com.mikitellurium.telluriumsrandomstuff.client.gui.render;

import java.util.List;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.fluids.FluidStack;

import mezz.jei.api.ingredients.IIngredientRenderer;

public class FluidBlockRenderer implements IIngredientRenderer<FluidStack> {
    @Override
    public void render(GuiGraphics graphics, FluidStack ingredient) {
        var fluid = ingredient.getFluid();
        BlockRendering.renderFluid(graphics, fluid, 3, 1, 10, 11);
    }

    @Override
    public List<Component> getTooltip(FluidStack ingredient, TooltipFlag tooltipFlag) {
        return List.of(ingredient.getDisplayName());
    }
}
