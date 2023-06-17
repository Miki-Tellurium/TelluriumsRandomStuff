package com.mikitellurium.telluriumsrandomstuff.common.content.block;

import com.mikitellurium.telluriumsrandomstuff.registry.ModTags;
import com.mikitellurium.telluriumsrandomstuff.util.ParticleUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MagmaBlock;
import net.minecraft.world.level.block.state.BlockState;

public class SoulMagmaBlock extends MagmaBlock {

    public SoulMagmaBlock() {
        super(Properties.copy(Blocks.MAGMA_BLOCK)
                .isValidSpawn((blockState, blockGetter, blockPos, entityType) -> entityType.is(ModTags.EntityTypes.SOUL_LAVA_IMMUNE))
                .lightLevel((blockState) -> 2)
                .emissiveRendering((blockState, blockGetter, blockPos) -> true));
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState blockState, Entity entity) {
        if (entity.getType().is(ModTags.EntityTypes.SOUL_LAVA_IMMUNE)) {
            super.stepOn(level, pos, blockState, entity);
        }
    }

    @Override
    public void tick(BlockState blockState, ServerLevel level, BlockPos pos, RandomSource random) {
        CustomBubbleColumnBlock.updateColumn(level, pos.above(), blockState);
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos pos, RandomSource random) {
        if (level.isRaining()) {
            ParticleUtils.handleRainParticles(level, pos, blockState, random);
        }
    }

}
