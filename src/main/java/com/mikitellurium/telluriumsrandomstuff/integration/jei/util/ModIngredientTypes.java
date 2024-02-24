package com.mikitellurium.telluriumsrandomstuff.integration.jei.util;

import mezz.jei.api.ingredients.IIngredientTypeWithSubtypes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class ModIngredientTypes {
    public static final IIngredientTypeWithSubtypes<Block, BlockState> BLOCK_STATE = new IIngredientTypeWithSubtypes<>() {
        @Override
        public Class<? extends BlockState> getIngredientClass() {
            return BlockState.class;
        }

        @Override
        public Class<? extends Block> getIngredientBaseClass() {
            return Block.class;
        }

        @Override
        public Block getBase(BlockState ingredient) {
            return ingredient.getBlock();
        }
    };
}
