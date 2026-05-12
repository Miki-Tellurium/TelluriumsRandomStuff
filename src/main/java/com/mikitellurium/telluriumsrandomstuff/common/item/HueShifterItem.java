package com.mikitellurium.telluriumsrandomstuff.common.item;

import com.mikitellurium.telluriumsrandomstuff.common.block.OpalBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;

public class HueShifterItem extends Item {
    public HueShifterItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        var level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState blockState = level.getBlockState(pos);
        if (blockState.getBlock() instanceof OpalBlock) {
            level.setBlockAndUpdate(pos, blockState.cycle(OpalBlock.HUE));
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return InteractionResult.PASS;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag isAdvanced) {
        components.add(Component.translatable("item.telluriumsrandomstuff.opal_color_shifter.tooltip"));
    }
}
