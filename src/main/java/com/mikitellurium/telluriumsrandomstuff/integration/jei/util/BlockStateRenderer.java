package com.mikitellurium.telluriumsrandomstuff.integration.jei.util;

import com.mikitellurium.telluriumsrandomstuff.client.gui.util.BlockRendering;
import mezz.jei.api.ingredients.IIngredientRenderer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class BlockStateRenderer implements IIngredientRenderer<BlockState> {

    @Override
    public void render(GuiGraphics graphics, BlockState ingredient) {
        BlockRendering.renderBlock(graphics, ingredient, 8, 8, 10, ingredient.is(Blocks.WATER_CAULDRON));
    }

    @Override
    public List<Component> getTooltip(BlockState ingredient, TooltipFlag tooltipFlag) {
        return List.of(ingredient.getBlock().getName());
    }

}
