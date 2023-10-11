package com.mikitellurium.telluriumsrandomstuff.client.gui.render;

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

public class BlockStateRenderer implements IIngredientRenderer<Block> {

    @Override
    public void render(GuiGraphics graphics, Block ingredient) {
        BlockRendering.renderBlock(ingredient.defaultBlockState(), graphics.pose(), graphics.bufferSource(),
                13, 13, 10, 10);
    }

    @Override
    public List<Component> getTooltip(Block ingredient, TooltipFlag tooltipFlag) {
        return List.of(ingredient.getName());
    }

}