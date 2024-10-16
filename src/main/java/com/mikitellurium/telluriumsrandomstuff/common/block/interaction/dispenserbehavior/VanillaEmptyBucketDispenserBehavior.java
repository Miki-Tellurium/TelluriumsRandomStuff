package com.mikitellurium.telluriumsrandomstuff.common.block.interaction.dispenserbehavior;

import com.mikitellurium.telluriumsrandomstuff.api.ModDispenserBehaviours;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class VanillaEmptyBucketDispenserBehavior extends DefaultDispenseItemBehavior {

    @Override
    public ItemStack execute(BlockSource blockSource, ItemStack itemStack) {
        LevelAccessor levelaccessor = blockSource.getLevel();
        BlockPos blockpos = blockSource.getPos().relative(blockSource.getBlockState().getValue(DispenserBlock.FACING));
        BlockState blockstate = levelaccessor.getBlockState(blockpos);
        Block block = blockstate.getBlock();
        if (block instanceof BucketPickup) {
            ItemStack itemstack = ((BucketPickup)block).pickupBlock(levelaccessor, blockpos, blockstate);
            if (itemstack.isEmpty()) {
                return super.execute(blockSource, itemStack);
            } else {
                levelaccessor.gameEvent(null, GameEvent.FLUID_PICKUP, blockpos);
                Item item = itemstack.getItem();
                itemStack.shrink(1);
                if (itemStack.isEmpty()) {
                    return new ItemStack(item);
                } else {
                    if (((DispenserBlockEntity)blockSource.getEntity()).addItem(new ItemStack(item)) < 0) {
                        ModDispenserBehaviours.DEFAULT_DISPENSE.dispense(blockSource, new ItemStack(item));
                    }

                    return itemStack;
                }
            }
        } else {
            return super.execute(blockSource, itemStack);
        }
    }

}
