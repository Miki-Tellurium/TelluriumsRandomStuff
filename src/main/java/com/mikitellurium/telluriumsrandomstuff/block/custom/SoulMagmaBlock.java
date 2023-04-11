package com.mikitellurium.telluriumsrandomstuff.block.custom;

import com.mikitellurium.telluriumsrandomstuff.util.LevelUtils;
import com.mikitellurium.telluriumsrandomstuff.util.ParticleUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MagmaBlock;
import net.minecraft.world.level.block.state.BlockState;

public class SoulMagmaBlock extends MagmaBlock {

    public SoulMagmaBlock() {
        super(Properties.copy(Blocks.MAGMA_BLOCK)
                .isValidSpawn((blockState, blockGetter, blockPos, entityType) -> LevelUtils.isSoulMagmaBlockValidSpawn(entityType))
                .lightLevel((blockState) -> 2)
                .emissiveRendering((blockState, blockGetter, blockPos) -> true));
    }

    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        CustomBubbleColumnBlock.updateColumn(pLevel, pPos.above(), pState);
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pLevel.isRaining()) {
            ParticleUtils.handleRainParticles(pLevel, pPos, pState, pRandom);
        }
    }

}
