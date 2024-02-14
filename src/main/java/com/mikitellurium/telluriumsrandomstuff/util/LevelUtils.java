package com.mikitellurium.telluriumsrandomstuff.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class LevelUtils {

    public static boolean isInsideWaterCauldron(Level level, Entity entity) {
        BlockPos blockPos = entity.blockPosition();
        return level.getBlockState(blockPos).is(Blocks.WATER_CAULDRON);
    }

}
