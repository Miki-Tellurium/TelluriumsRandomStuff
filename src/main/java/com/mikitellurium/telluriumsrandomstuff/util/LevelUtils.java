package com.mikitellurium.telluriumsrandomstuff.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class LevelUtils {

    public static boolean isInsideWaterCauldron(Level level, Entity entity) {
        BlockPos blockPos = entity.blockPosition();
        return level.getBlockState(blockPos).is(Blocks.WATER_CAULDRON);
    }

    public static int getHighestLightLevel(BlockAndTintGetter level, BlockPos pos) {
        int highestLightLevel = 0;

        for (Direction direction : Direction.values()) {
            BlockPos sidePos = pos.relative(direction);
            BlockState adjacentBlock = level.getBlockState(sidePos);

            int lightLevel;
            if (adjacentBlock.getLightEmission() > 0) {
                lightLevel = adjacentBlock.getLightEmission();
            } else if (adjacentBlock.isFaceSturdy(level, pos, direction.getOpposite()) && adjacentBlock.isViewBlocking(level, pos)) {
                continue; // If this face face is obstructed skip this direction
            } else {
                lightLevel = level.getBrightness(LightLayer.BLOCK, sidePos);
            }

            if (lightLevel > highestLightLevel) {
                highestLightLevel = lightLevel;
            }
        }

        return highestLightLevel;
    }

}
