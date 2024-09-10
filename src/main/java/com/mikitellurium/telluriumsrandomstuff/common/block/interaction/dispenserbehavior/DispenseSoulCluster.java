package com.mikitellurium.telluriumsrandomstuff.common.block.interaction.dispenserbehavior;

import com.mikitellurium.telluriumsrandomstuff.api.ModDispenserBehaviours;
import com.mikitellurium.telluriumsrandomstuff.common.blockentity.AwakenedSculkShriekerBlockEntity;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.SculkShriekerBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

public class DispenseSoulCluster extends DefaultDispenseItemBehavior {

    @Override
    public ItemStack execute(BlockSource blockSource, ItemStack itemStack) {
        BlockPos blockpos = blockSource.getPos().relative(blockSource.getBlockState().getValue(DispenserBlock.FACING));
        ServerLevel level = blockSource.getLevel();
        BlockState blockState = level.getBlockState(blockpos);
        if (blockState.getValue(SculkShriekerBlock.CAN_SUMMON) && !blockState.getValue(SculkShriekerBlock.SHRIEKING)) {
            Optional<AwakenedSculkShriekerBlockEntity> shriekerBlockEntity = level.getBlockEntity(blockpos, ModBlockEntities.AWAKENED_SCULK_SHRIEKER.get());
            if (shriekerBlockEntity.isPresent()) {
                shriekerBlockEntity.get().tryShriek(level, null);
                itemStack.shrink(1);
                return itemStack;
            } else {
                return ModDispenserBehaviours.DEFAULT_DISPENSE.dispense(blockSource, itemStack);
            }
        } else {
            return ModDispenserBehaviours.DEFAULT_DISPENSE.dispense(blockSource, itemStack);
        }
    }

}
