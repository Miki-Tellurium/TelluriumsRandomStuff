package com.mikitellurium.telluriumsrandomstuff.common.block.interaction.dispenserbehavior;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class DispenseLavaBucketToCauldron extends BucketToCauldronDispenseBehavior {

    @Override
    protected BlockState getResultingCauldron(BlockState blockState) {
        return Blocks.LAVA_CAULDRON.defaultBlockState();
    }

    @Override
    protected SoundEvent getDispenseSound(BlockState blockState) {
        return SoundEvents.BUCKET_EMPTY_LAVA;
    }

}
