package com.mikitellurium.telluriumsrandomstuff.common.block;

import com.mikitellurium.telluriumsrandomstuff.registry.ModTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class SoulMagmaBricksBlock extends Block {

    public SoulMagmaBricksBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.NETHER_BRICKS)
                .isValidSpawn((blockState, blockGetter, blockPos, entityType) -> entityType.is(ModTags.EntityTypes.SOUL_LAVA_IMMUNE))
                .lightLevel((blockState) -> 2)
                .emissiveRendering((blockState, blockGetter, blockPos) -> true));
    }

}
