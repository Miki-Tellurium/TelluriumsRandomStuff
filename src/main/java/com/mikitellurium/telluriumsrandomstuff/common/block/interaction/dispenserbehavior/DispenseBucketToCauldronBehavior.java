package com.mikitellurium.telluriumsrandomstuff.common.block.interaction.dispenserbehavior;

import com.mikitellurium.telluriumsrandomstuff.api.DispenserCauldronInteractionManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class DispenseBucketToCauldronBehavior extends DefaultDispenseItemBehavior {

    private final SoundEvent bucketEmptySound;

    public DispenseBucketToCauldronBehavior(SoundEvent bucketEmptySound) {
        this.bucketEmptySound = bucketEmptySound;
    }

    // Anonymous class copied from vanilla code
    private final DefaultDispenseItemBehavior vanillaDispenseItemBehavior = new DefaultDispenseItemBehavior() {
        private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();
        private final VanillaEmptyBucketDispenserBehavior vanillaEmptyBucketDispenserBehavior = new VanillaEmptyBucketDispenserBehavior();

        public ItemStack execute(BlockSource blockSource, ItemStack itemStack) {
            DispensibleContainerItem dispensiblecontaineritem = (DispensibleContainerItem)itemStack.getItem();
            BlockPos blockpos = blockSource.getPos().relative(blockSource.getBlockState().getValue(DispenserBlock.FACING));
            Level level = blockSource.getLevel();
            // Handling empty bucket behavior
            if (itemStack.is(Items.BUCKET)) {
                return this.vanillaEmptyBucketDispenserBehavior.execute(blockSource, itemStack);
            }
            if (dispensiblecontaineritem.emptyContents(null, level, blockpos, null, itemStack)) {
                dispensiblecontaineritem.checkExtraContent(null, level, itemStack, blockpos);
                return new ItemStack(Items.BUCKET);
            } else {
                return this.defaultDispenseItemBehavior.dispense(blockSource, itemStack);
            }
        }
    };

    @Override
    protected ItemStack execute(BlockSource blockSource, ItemStack itemStack) {

        BlockPos blockpos = blockSource.getPos().relative(blockSource.getBlockState().getValue(DispenserBlock.FACING));
        Level level = blockSource.getLevel();
        BlockState blockState = level.getBlockState(blockpos);

        if (blockState.getBlock() instanceof AbstractCauldronBlock &&
                (itemStack.getItem() instanceof BucketItem || itemStack.getItem() instanceof SolidBucketItem)) {
            Block resultCauldron = DispenserCauldronInteractionManager.getResultingCauldron(itemStack.getItem());

            if (resultCauldron instanceof CauldronBlock) {
                ItemStack resultBucket = DispenserCauldronInteractionManager.getRemainderBucket(blockState.getBlock());
                // If the cauldron is already empty do normal dispense
                if (resultBucket.is(Items.BUCKET)) {
                    return this.vanillaDispenseItemBehavior.dispense(blockSource, itemStack);
                }
                level.setBlock(blockpos, Blocks.CAULDRON.defaultBlockState(), 3);
                itemStack.shrink(1);
                if (itemStack.isEmpty()) {
                    return new ItemStack(resultBucket.getItem());
                } else {
                    if (((DispenserBlockEntity)blockSource.getEntity()).addItem(new ItemStack(resultBucket.getItem())) < 0) {
                        this.vanillaDispenseItemBehavior.dispense(blockSource, new ItemStack(resultBucket.getItem()));
                    }

                    return itemStack;
                }
            } else if (resultCauldron instanceof LayeredCauldronBlock) {
                level.setBlock(blockpos, resultCauldron.defaultBlockState()
                        .setValue(LayeredCauldronBlock.LEVEL, LayeredCauldronBlock.MAX_FILL_LEVEL), 3);
                return new ItemStack(Items.BUCKET);
            }

            level.setBlock(blockpos, resultCauldron.defaultBlockState(), 3);
            return new ItemStack(Items.BUCKET);
        } else {
            return this.vanillaDispenseItemBehavior.dispense(blockSource, itemStack);
        }
    }

    @Override
    protected void playSound(BlockSource blockSource) {
        blockSource.getLevel().playSound(null, blockSource.getPos(), this.bucketEmptySound,
                SoundSource.BLOCKS, 1.0f, 1.0f);
    }

}
