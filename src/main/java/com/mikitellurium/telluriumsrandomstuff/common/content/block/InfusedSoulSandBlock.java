package com.mikitellurium.telluriumsrandomstuff.common.content.block;

import com.mikitellurium.telluriumsrandomstuff.registry.ModParticles;
import com.mikitellurium.telluriumsrandomstuff.util.ParticleUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoulSandBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;

public class InfusedSoulSandBlock extends SoulSandBlock {

    public InfusedSoulSandBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.SOUL_SAND));
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos pos, RandomSource random) {
        if (level.getFluidState(pos.above()).is(Fluids.LAVA)) {
            if (random.nextInt(12) == 0) {
                double d0 = pos.getX() + random.nextDouble();
                double d1 = pos.getY() - 0.05d;
                double d2 = pos.getZ() + random.nextDouble();
                level.addParticle(ModParticles.SOUL_LAVA_HANG.get(), d0, d1, d2, 0.0D, 0.0D, 0.0D);
            }
        }

        if (level.isRaining()) {
            ParticleUtils.spawnRainParticles(level, pos, blockState, random);
        }
    }

    @Override
    public void tick(BlockState blockState, ServerLevel level, BlockPos pos, RandomSource random) {
        CustomBubbleColumnBlock.updateColumn(level, pos.above(), blockState);
    }

}
