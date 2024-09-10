package com.mikitellurium.telluriumsrandomstuff.common.block.interaction.dispenserbehavior;

import com.mikitellurium.telluriumsrandomstuff.api.ModDispenserBehaviours;
import com.mikitellurium.telluriumsrandomstuff.util.CachedObject;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;

public class DispenseEmptyBucketToCauldron extends BucketToCauldronDispenseBehavior {

    private CachedObject<BlockState> cachedBlockState = CachedObject.empty();

    @Override
    protected ItemStack dispenseCauldron(BlockSource blockSource, ItemStack itemStack) {
        Level level = blockSource.getLevel();
        BlockPos blockPos = blockSource.getPos().relative(blockSource.getBlockState().getValue(DispenserBlock.FACING));
        BlockState blockState = level.getBlockState(blockPos);
        BlockState resultCauldron = this.getResultingCauldron(blockState);
        if (blockState.is(Blocks.CAULDRON)) {
            this.cachedBlockState = CachedObject.of(resultCauldron);
            return this.vanillaDispense.dispense(blockSource, itemStack);
        } else {
            this.cachedBlockState = CachedObject.of(blockState);
            return super.dispenseCauldron(blockSource, itemStack);
        }
    }

    @Override
    protected BlockState getResultingCauldron(BlockState blockState) {
        return Blocks.CAULDRON.defaultBlockState();
    }

    @Override
    protected SoundEvent getDispenseSound(BlockState blockState) {
        return ModDispenserBehaviours.getDispenseSound(this.cachedBlockState.get());
    }

    @Override
    protected ItemStack getRemainderItem(BlockState blockState, ItemStack itemStack) {
        return ModDispenserBehaviours.getFilledBucket((AbstractCauldronBlock) blockState.getBlock(), itemStack);
    }

}
