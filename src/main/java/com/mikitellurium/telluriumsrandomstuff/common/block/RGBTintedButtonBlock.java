package com.mikitellurium.telluriumsrandomstuff.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RGBTintedButtonBlock extends ButtonBlock implements RGBTinted {
    public RGBTintedButtonBlock(Properties properties, BlockSetType type, int ticksToStayPressed, boolean arrowsCanPress) {
        super(properties, type, ticksToStayPressed, arrowsCanPress);
        this.registerDefaultState(this.defaultBlockState().setValue(HUE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(HUE);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        ItemStack stack = context.getItemInHand();
        if (stack.hasTag() && stack.getTag().contains("hue")) {
            return super.defaultBlockState().setValue(HUE, stack.getTag().getInt("hue"));
        }
        return super.defaultBlockState();
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        List<ItemStack> drops = super.getDrops(state, params);
        for (ItemStack stack: drops) {
            RGBTinted.setStackHue(stack, state.getValue(HUE));
        }
        return drops;
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        ItemStack stack = state.getBlock().asItem().getDefaultInstance();
        RGBTinted.setStackHue(stack, state.getValue(HUE));
        return stack;
    }
}
