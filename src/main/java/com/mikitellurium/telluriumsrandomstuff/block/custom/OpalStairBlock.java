package com.mikitellurium.telluriumsrandomstuff.block.custom;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class OpalStairBlock extends StairBlock {

    public OpalStairBlock() {
        super(Blocks.STONE::defaultBlockState, Properties.copy(Blocks.STONE));
    }

}
