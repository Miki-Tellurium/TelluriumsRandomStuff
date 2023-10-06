package com.mikitellurium.telluriumsrandomstuff.common.block;

import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.registry.ModParticles;
import com.mikitellurium.telluriumsrandomstuff.util.LevelUtils;
import com.mikitellurium.telluriumsrandomstuff.util.ParticleUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoulSandBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluids;

public class InfusedSoulSandBlock extends SoulSandBlock {

    private final float soulLavaDripChance = 0.032f;
    private final int depletionChance = 10;

    public InfusedSoulSandBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.SOUL_SAND)
                .randomTicks());
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

    @Override
    public void randomTick(BlockState blockState, ServerLevel level, BlockPos pos, RandomSource random) {
        if (level.getFluidState(pos.above()).is(Fluids.LAVA)) {
            if (random.nextFloat() < soulLavaDripChance) {
                BlockPos maybeCauldronPos = LevelUtils.findFillableCauldronBelow(level, pos.below());
                if (maybeCauldronPos != null) {
                    BlockState soulLavaCauldron = ModBlocks.SOUL_LAVA_CAULDRON.get().defaultBlockState();
                    level.setBlockAndUpdate(pos.above(), Blocks.AIR.defaultBlockState());
                    level.setBlockAndUpdate(maybeCauldronPos, soulLavaCauldron);
                    level.gameEvent(GameEvent.BLOCK_CHANGE, maybeCauldronPos, GameEvent.Context.of(soulLavaCauldron));
                    level.playSound(null, maybeCauldronPos, SoundEvents.SOUL_ESCAPE, SoundSource.BLOCKS, 0.8f, 1.0f);
                    if (random.nextInt(100) < depletionChance) {
                        level.setBlockAndUpdate(pos, Blocks.SOUL_SAND.defaultBlockState());
                        level.playSound(null, pos, SoundEvents.SOUL_ESCAPE, SoundSource.BLOCKS, 0.8f, 1.5f);
                    }
                }
            }
        }
    }

}
