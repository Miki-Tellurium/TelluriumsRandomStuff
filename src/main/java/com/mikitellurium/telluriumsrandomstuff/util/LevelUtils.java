package com.mikitellurium.telluriumsrandomstuff.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

public class LevelUtils {

    public static void handleRainParticles(Level level, BlockPos pos, FluidState state, RandomSource random) {
        if (level.canSeeSky(pos.above())) {
            double d0 = random.nextDouble();
            double d1 = random.nextDouble();
            double d2 = state.getShape(level, pos).max(Direction.Axis.Y, d0, d1);
            double d3 = state.getHeight(level, pos);
            double d4 = Math.max(d2, d3);
            level.addParticle(ParticleTypes.SMOKE, (double) pos.getX() + d0, (double) pos.getY() + d4, (double) pos.getZ() + d1, 0.0D, 0.0D, 0.0D);
        }
    }

    public static void handleRainParticles(Level level, BlockPos pos, BlockState state, RandomSource random) {
        if (level.canSeeSky(pos.above())) {
            FluidState fluidState = level.getFluidState(pos.above());
            double d0 = random.nextDouble();
            double d1 = random.nextDouble();
            double d2 = state.getShape(level, pos).max(Direction.Axis.Y, d0, d1);
            double d3 = fluidState.getHeight(level, pos);
            double d4 = Math.max(d2, d3);
            level.addParticle(ParticleTypes.SMOKE, (double) pos.getX() + d0, (double) pos.getY() + d4, (double) pos.getZ() + d1, 0.0D, 0.0D, 0.0D);
        }
    }

}
