package com.mikitellurium.telluriumsrandomstuff.integration.jei.util;

import com.mikitellurium.telluriumsrandomstuff.client.hud.render.GuiFluidRenderer;
import mezz.jei.api.ingredients.IIngredientRenderer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

/* This class isn't used anywhere. I don't know why this exists. */
public class FluidTankRenderer implements IIngredientRenderer<FluidStack> {

    private final int xPos;
    private final int yPos;
    private final int width;
    private final int height;
    private final int capacity;

    public FluidTankRenderer(int xPos, int yPos, int width, int height, int capacity) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.capacity = capacity;
    }

    @Override
    public void render(GuiGraphics graphics, FluidStack ingredient) {
        GuiFluidRenderer.drawBackground(graphics, xPos, yPos, ingredient, capacity, width, height);
    }

    @Override
    public List<Component> getTooltip(FluidStack ingredient, TooltipFlag tooltipFlag) {
        int amount = ingredient.getAmount();

        List<Component> toolTips = this.getFluidTooltips(ingredient, tooltipFlag);
        MutableComponent amountString = Component.literal(amount + " mB");
        toolTips.add(amountString.withStyle(ChatFormatting.GRAY));

        return toolTips;
    }

    private List<Component> getFluidTooltips(FluidStack ingredient, TooltipFlag tooltipFlag) {
        List<Component> tooltip = new ArrayList<>();
        Fluid fluid = ingredient.getFluid();

        Component displayName = ingredient.getDisplayName();
        tooltip.add(displayName);

        if (tooltipFlag.isAdvanced()) {
            ResourceLocation resourceLocation = ForgeRegistries.FLUIDS.getKey(fluid);
            if (resourceLocation != null) {
                MutableComponent advancedId = Component.literal(resourceLocation.toString())
                        .withStyle(ChatFormatting.DARK_GRAY);
                tooltip.add(advancedId);
            }
        }

        return tooltip;
    }

    @Override
    public int getWidth() {
        return 16;
    }

    @Override
    public int getHeight() {
        return 48;
    }

}
