package com.mikitellurium.telluriumsrandomstuff.util;

import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.fluids.FluidStack;

import mezz.jei.api.ingredients.IIngredientRenderer;

public class FluidBlockRenderer implements IIngredientRenderer<FluidStack> {
    @Override
    public void render(PoseStack stack, FluidStack ingredient) {
        var fluid = ingredient.getFluid();
        BlockRendering.renderFluid(stack, fluid, 3, 1, 10, 11);
    }

    @Override
    public List<Component> getTooltip(FluidStack ingredient, TooltipFlag tooltipFlag) {
        return List.of(ingredient.getDisplayName());
    }
}
