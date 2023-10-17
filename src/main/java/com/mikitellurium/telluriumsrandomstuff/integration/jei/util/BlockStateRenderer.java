package com.mikitellurium.telluriumsrandomstuff.integration.jei.util;

import com.mikitellurium.telluriumsrandomstuff.client.gui.render.BlockRendering;
import mezz.jei.api.ingredients.IIngredientRenderer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public class BlockStateRenderer implements IIngredientRenderer<BlockState> {

    @Override
    public void render(GuiGraphics graphics, BlockState ingredient) {
        BlockRendering.renderBlock(graphics, ingredient, 8, 8, 10);
    }

    @Override
    public List<Component> getTooltip(BlockState ingredient, TooltipFlag tooltipFlag) {
        return List.of(ingredient.getBlock().getName());
    }

}
