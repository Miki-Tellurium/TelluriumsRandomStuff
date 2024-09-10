package com.mikitellurium.telluriumsrandomstuff.common.block.interaction.dispenserbehavior;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;

public class DispensePowderSnowBucketToCauldron extends BucketToCauldronDispenseBehavior {

    @Override
    protected BlockState getResultingCauldron(BlockState blockState) {
        return Blocks.POWDER_SNOW_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, LayeredCauldronBlock.MAX_FILL_LEVEL);
    }

    @Override
    protected SoundEvent getDispenseSound(BlockState blockState) {
        return SoundEvents.BUCKET_EMPTY_POWDER_SNOW;
    }

}
