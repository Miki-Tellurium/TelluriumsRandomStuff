package com.mikitellurium.telluriumsrandomstuff.block.custom;

import com.mikitellurium.telluriumsrandomstuff.util.LevelUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class SoulMagmaBricksBlock extends Block {

    public SoulMagmaBricksBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.NETHER_BRICKS)
                .isValidSpawn((blockState, blockGetter, blockPos, entityType) -> LevelUtils.isSoulBlockValidSpawn(entityType))
                .lightLevel((blockState) -> 2)
                .emissiveRendering((blockState, blockGetter, blockPos) -> true));
    }

}
