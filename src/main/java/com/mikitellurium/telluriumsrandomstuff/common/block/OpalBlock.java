package com.mikitellurium.telluriumsrandomstuff.common.block;

import com.mikitellurium.telluriumsrandomstuff.util.ColorsUtil;
import com.mojang.realmsclient.util.JsonUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.HitResult;

import javax.annotation.Nullable;
import java.util.List;

public class OpalBlock extends Block {
    public static final int HUE_RANGE = 32;
    public static final IntegerProperty HUE = IntegerProperty.create("hue", 0, HUE_RANGE - 1);

    public OpalBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(HUE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
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
            setStackHue(stack, state.getValue(HUE));
        }
        return drops;
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        ItemStack stack = state.getBlock().asItem().getDefaultInstance();
        setStackHue(stack, state.getValue(HUE));
        return stack;
    }

    public int getColor(BlockState state) {
        return ColorsUtil.getOpalColor(state.getValue(HUE));
    }

    public static void setStackHue(ItemStack stack, int hue) {
        Block block = Block.byItem(stack.getItem());
        if (block instanceof OpalBlock) {
            CompoundTag tag = stack.getOrCreateTag();
            tag.putInt("hue", hue);
        }
    }
}
