package com.mikitellurium.telluriumsrandomstuff.integration.jei.util;

import com.mikitellurium.telluriumsrandomstuff.client.hud.render.BlockRendering;
import mezz.jei.api.ingredients.IIngredientRenderer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public class FluidBlockRenderer implements IIngredientRenderer<FluidStack> {

    @Override
    public void render(GuiGraphics graphics, FluidStack ingredient) {
        BlockRendering.renderFluid(graphics, ingredient);
    }

    @Override
    public List<Component> getTooltip(FluidStack ingredient, TooltipFlag tooltipFlag) {
        return List.of(ingredient.getDisplayName());
    }

}
