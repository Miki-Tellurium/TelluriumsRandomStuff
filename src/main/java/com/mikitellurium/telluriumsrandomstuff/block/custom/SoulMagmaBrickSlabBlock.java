package com.mikitellurium.telluriumsrandomstuff.block.custom;

import com.mikitellurium.telluriumsrandomstuff.block.ModBlocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class SoulMagmaBrickSlabBlock extends SlabBlock {

    public SoulMagmaBrickSlabBlock() {
        super(BlockBehaviour.Properties.copy(ModBlocks.SOUL_MAGMA_BRICKS.get())
                .isValidSpawn((blockState, blockGetter, blockPos, entityType) -> false));
    }

}
