package com.mikitellurium.telluriumsrandomstuff.common.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class FloatingWalkwayBlockItem extends BlockItem {
    public FloatingWalkwayBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        BlockHitResult blockHitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
        InteractionResult interactionResult = super.useOn(new UseOnContext(player, interactionHand, blockHitResult));
        return new InteractionResultHolder<>(interactionResult, player.getItemInHand(interactionHand));
    }

    @Override
    public @Nullable BlockPlaceContext updatePlacementContext(BlockPlaceContext context) {
        if (context.getClickedFace() != Direction.UP) {
            return context;
        }
        BlockPos pos = context.getClickedPos().below();
        Level level = context.getLevel();
        BlockState blockstate = level.getBlockState(pos);
        if (!blockstate.is(this.getBlock())) {
            return context;
        }
        Direction direction = context.getHorizontalDirection();
        return BlockPlaceContext.at(context, pos.relative(direction), direction);
    }
}
