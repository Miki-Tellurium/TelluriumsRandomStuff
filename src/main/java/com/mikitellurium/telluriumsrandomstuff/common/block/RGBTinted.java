package com.mikitellurium.telluriumsrandomstuff.common.block;

import com.mikitellurium.telluriumsrandomstuff.util.ColorsUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public interface RGBTinted {
    int HUE_RANGE = 32;
    IntegerProperty HUE = IntegerProperty.create("hue", 0, HUE_RANGE - 1);

    default int getColor(BlockState state) {
        return state.getBlock() instanceof RGBTinted ? ColorsUtil.getOpalColor(state.getValue(HUE)) : ColorsUtil.BLANK;
    }

    static void setStackHue(ItemStack stack, int hue) {
        if (stack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof RGBTinted) {
            CompoundTag tag = stack.getOrCreateTag();
            tag.putInt("hue", hue);
        }
    }
}
