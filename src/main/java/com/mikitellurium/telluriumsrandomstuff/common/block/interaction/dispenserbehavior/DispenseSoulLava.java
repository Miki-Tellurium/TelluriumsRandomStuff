package com.mikitellurium.telluriumsrandomstuff.common.block.interaction.dispenserbehavior;

import com.mikitellurium.telluriumsrandomstuff.api.ModDispenserBehaviours;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.world.item.DispensibleContainerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

public class DispenseSoulLava extends DefaultDispenseItemBehavior {

    @Override
    public ItemStack execute(BlockSource blockSource, ItemStack itemStack) {
        DispensibleContainerItem containerItem = (DispensibleContainerItem) itemStack.getItem();
        BlockPos blockpos = blockSource.getPos().relative(blockSource.getBlockState().getValue(DispenserBlock.FACING));
        Level level = blockSource.getLevel();
        if (containerItem.emptyContents(null, level, blockpos, null, itemStack)) {
            containerItem.checkExtraContent(null, level, itemStack, blockpos);
            return new ItemStack(Items.BUCKET);
        } else {
            return ModDispenserBehaviours.DEFAULT_DISPENSE.dispense(blockSource, itemStack);
        }
    }

}
