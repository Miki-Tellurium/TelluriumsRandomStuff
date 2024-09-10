package com.mikitellurium.telluriumsrandomstuff.common.block.interaction.dispenserbehavior;

import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.state.BlockState;

public class DispenseSoulLavaBucketToCauldron extends BucketToCauldronDispenseBehavior {

    @Override
    protected BlockState getResultingCauldron(BlockState blockState) {
        return ModBlocks.SOUL_LAVA_CAULDRON.get().defaultBlockState();
    }

    @Override
    protected SoundEvent getDispenseSound(BlockState blockState) {
        return SoundEvents.BUCKET_EMPTY_LAVA;
    }

}
