package com.mikitellurium.telluriumsrandomstuff.common.block.interaction.dispenserbehavior;

import com.mikitellurium.telluriumsrandomstuff.api.ModDispenserBehaviours;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public abstract class BucketToCauldronDispenseBehavior extends DefaultDispenseItemBehavior {

    // Anonymous class copied from vanilla code
    protected final DefaultDispenseItemBehavior vanillaDispense = new DefaultDispenseItemBehavior() {
        private final VanillaEmptyBucketDispenserBehavior emptyBucketDispense = new VanillaEmptyBucketDispenserBehavior();

        public ItemStack execute(BlockSource blockSource, ItemStack itemStack) {
            DispensibleContainerItem dispensiblecontaineritem = (DispensibleContainerItem)itemStack.getItem();
            BlockPos blockpos = blockSource.getPos().relative(blockSource.getBlockState().getValue(DispenserBlock.FACING));
            Level level = blockSource.getLevel();
            // Handling empty bucket behavior
            if (itemStack.is(Items.BUCKET)) {
                return this.emptyBucketDispense.execute(blockSource, itemStack);
            }
            if (dispensiblecontaineritem.emptyContents(null, level, blockpos, null, itemStack)) {
                dispensiblecontaineritem.checkExtraContent(null, level, itemStack, blockpos);
                return new ItemStack(Items.BUCKET);
            } else {
                return ModDispenserBehaviours.DEFAULT_DISPENSE.dispense(blockSource, itemStack);
            }
        }
    };

    @Override
    protected ItemStack execute(BlockSource blockSource, ItemStack itemStack) {
        Level level = blockSource.getLevel();
        BlockPos blockPos = blockSource.getPos().relative(blockSource.getBlockState().getValue(DispenserBlock.FACING));
        BlockState blockState = level.getBlockState(blockPos);
        if (blockState.getBlock() instanceof AbstractCauldronBlock &&
                (itemStack.getItem() instanceof BucketItem || itemStack.getItem() instanceof SolidBucketItem)) {
            return this.dispenseCauldron(blockSource, itemStack);
        } else {
            return this.vanillaDispense.dispense(blockSource, itemStack);
        }
    }

    protected ItemStack dispenseCauldron(BlockSource blockSource, ItemStack itemStack) {
        Level level = blockSource.getLevel();
        BlockPos blockPos = blockSource.getPos().relative(blockSource.getBlockState().getValue(DispenserBlock.FACING));
        BlockState blockState = level.getBlockState(blockPos);
        BlockState resultCauldron = this.getResultingCauldron(blockState);
        ItemStack remainderStack = this.getRemainderItem(blockState, itemStack);
        level.setBlock(blockPos, resultCauldron, 3);
        itemStack.shrink(1);
        if (itemStack.isEmpty()) {
            return new ItemStack(remainderStack.getItem());
        } else {
            if (((DispenserBlockEntity)blockSource.getEntity()).addItem(new ItemStack(remainderStack.getItem())) < 0) {
                this.vanillaDispense.dispense(blockSource, new ItemStack(remainderStack.getItem()));
            }
            return itemStack;
        }
    }

    protected abstract BlockState getResultingCauldron(BlockState blockState);

    protected abstract SoundEvent getDispenseSound(BlockState blockState);

    protected ItemStack getRemainderItem(BlockState blockState, ItemStack itemStack) {
        return Items.BUCKET.getDefaultInstance();
    }

    @Override
    protected void playSound(BlockSource blockSource) {
        blockSource.getLevel().playSound(null, blockSource.getPos(), this.getDispenseSound(blockSource.getBlockState()), SoundSource.BLOCKS, 1.0f, 1.0f);
    }

}
