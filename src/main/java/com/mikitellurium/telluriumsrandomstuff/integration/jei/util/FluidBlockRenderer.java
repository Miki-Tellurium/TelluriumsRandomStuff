package com.mikitellurium.telluriumsrandomstuff.integration.jei.util;

import java.util.List;

import com.mikitellurium.telluriumsrandomstuff.client.gui.render.BlockRendering;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.fluids.FluidStack;

import mezz.jei.api.ingredients.IIngredientRenderer;

public class FluidBlockRenderer implements IIngredientRenderer<FluidStack> {

    @Override
    public void render(GuiGraphics graphics, FluidStack ingredient) {
        BlockRendering.renderLiquid(graphics, ingredient);
    }

    @Override
    public List<Component> getTooltip(FluidStack ingredient, TooltipFlag tooltipFlag) {
        return List.of(ingredient.getDisplayName());
    }

}
