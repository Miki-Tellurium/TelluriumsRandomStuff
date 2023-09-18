package com.mikitellurium.telluriumsrandomstuff.common.content.block;

import com.mikitellurium.telluriumsrandomstuff.common.content.blockentity.ItemPedestalBlockEntity;
import com.mikitellurium.telluriumsrandomstuff.util.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class ItemPedestalBlock extends BaseEntityBlock {

    private final VoxelShape PEDESTAL_SHAPE = Block.box(1.0D, 0.0D, 1.0D,
            15.0D, 15.0D, 15.0D);

    public ItemPedestalBlock(Properties properties) {
        super(properties.noOcclusion());
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
        return PEDESTAL_SHAPE;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState blockState) {
        return new ItemPedestalBlockEntity(pos, blockState);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos pos, Player player, InteractionHand hand,
                                 BlockHitResult hitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof ItemPedestalBlockEntity itemPedestal) {
            ItemStack itemStack = player.getItemInHand(hand);
            if (itemPedestal.isEmpty()) {
                if (itemPedestal.insertItem(itemStack, !player.isCreative())) {
                    level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                    LogUtils.sendChatMessage("Inserted item");
                } else {
                    LogUtils.sendChatMessage("Could not insert item");
                }
            } else {
                itemPedestal.removeItem();
                level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                LogUtils.sendChatMessage("Removed item");
            }
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (blockState.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof ItemPedestalBlockEntity itemPedestal) {
                itemPedestal.dropItem();
            }
        }
        super.onRemove(blockState, level, pos, newState, isMoving);
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }

}
