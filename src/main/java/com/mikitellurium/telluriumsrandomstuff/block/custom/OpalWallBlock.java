package com.mikitellurium.telluriumsrandomstuff.block.custom;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class OpalWallBlock extends WallBlock {

    public OpalWallBlock() {
        super(Properties.copy(Blocks.STONE));
    }

}
