package com.mikitellurium.telluriumsrandomstuff.block.custom;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;

public class OpalCobblestoneStairBlock extends StairBlock {

    public OpalCobblestoneStairBlock() {
        super(Blocks.COBBLESTONE::defaultBlockState, Properties.copy(Blocks.COBBLESTONE));
    }

}
