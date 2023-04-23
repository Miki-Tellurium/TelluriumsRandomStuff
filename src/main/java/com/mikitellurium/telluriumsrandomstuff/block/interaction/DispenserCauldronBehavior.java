package com.mikitellurium.telluriumsrandomstuff.block.interaction;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;

public class DispenserCauldronBehavior extends DefaultDispenseItemBehavior {

    private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

    @Override
    protected ItemStack execute(BlockSource blockSource, ItemStack itemStack) {

        BlockPos blockpos = blockSource.getPos().relative(blockSource.getBlockState().getValue(DispenserBlock.FACING));
        Level level = blockSource.getLevel();
        BlockState blockState = level.getBlockState(blockpos);

        if (blockState.getBlock() instanceof AbstractCauldronBlock &&
                (itemStack.getItem() instanceof BucketItem || itemStack.getItem() instanceof SolidBucketItem)) {
            Block cauldron = ModDispenserBehaviours.DISPENSER_CAULDRON_INTERACTIONS.get(itemStack.getItem());

            if (cauldron instanceof LayeredCauldronBlock) {
                return handleLayeredCauldron(level, cauldron.defaultBlockState(), blockpos);
            } else if(itemStack.is(Items.BUCKET)) {
                return handleEmptyBucket(level, blockState, blockpos);
            }

            level.setBlock(blockpos, cauldron.defaultBlockState(), 3);
            return new ItemStack(Items.BUCKET);
        } else {
            return this.defaultDispenseItemBehavior.dispense(blockSource, itemStack);
        }
    }

    // Empty the cauldron and return the correct bucket
    private ItemStack handleEmptyBucket(Level level, BlockState blockState, BlockPos blockpos) {
        ItemStack bucket = ItemStack.EMPTY;
        // Get the correct bucket to return
        for (Map.Entry<Item, Block> entry : ModDispenserBehaviours.DISPENSER_CAULDRON_INTERACTIONS.entrySet()) {
            if (entry.getValue().equals(blockState.getBlock())) {
                bucket = entry.getKey().getDefaultInstance();
            }
        }

        level.setBlock(blockpos, Blocks.CAULDRON.defaultBlockState(), 3);
        return bucket;
    }

    // Fill a layered cauldron completely
    private ItemStack handleLayeredCauldron(Level level, BlockState blockState, BlockPos blockpos) {
        level.setBlock(blockpos, blockState.setValue(LayeredCauldronBlock.LEVEL, LayeredCauldronBlock.MAX_FILL_LEVEL), 3);
        return new ItemStack(Items.BUCKET);
    }

}
