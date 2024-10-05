package com.mikitellurium.telluriumsrandomstuff.common.block;

import com.mikitellurium.telluriumsrandomstuff.common.blockentity.AlchemixerBlockEntity;
import com.mikitellurium.telluriumsrandomstuff.common.blockentity.ItemPedestalBlockEntity;
import com.mikitellurium.telluriumsrandomstuff.networking.ModMessages;
import com.mikitellurium.telluriumsrandomstuff.networking.packets.PedestalItemSyncS2CPacket;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlockEntities;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class ItemPedestalBlock extends BaseEntityBlock {

    private final VoxelShape PEDESTAL_SHAPE = Block.box(1.0D, 0.0D, 1.0D,
            15.0D, 15.0D, 15.0D);

    public ItemPedestalBlock(Properties properties) {
        super(properties.noOcclusion());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState blockState) {
        return new ItemPedestalBlockEntity(pos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState,
                                                                  BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, ModBlockEntities.ITEM_PEDESTAL.get(),
                (tickLevel, blockPos, state, itemPedestal) -> itemPedestal.tick(tickLevel, blockPos, state));
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
            if (itemStack.is(Items.NAME_TAG) && !itemStack.hasCustomHoverName() && !itemPedestal.alwaysDisplayName()) {
                itemPedestal.setAlwaysDisplayNameAndSync();
                if (!player.isCreative()) {
                    itemStack.shrink(1);
                }
                return InteractionResult.SUCCESS;
            }

            if (itemPedestal.isEmpty()) {
                if (itemPedestal.insertItem(itemStack, !player.isCreative())) {
                    level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                }
            } else {
                itemPedestal.removeItem();
                level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
            }
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState blockState, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.block();
    }

    @Override
    public void onPlace(BlockState blockState, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof ItemPedestalBlockEntity itemPedestal) {
            if (!itemPedestal.isEmpty()) {
                ModMessages.sendToClients(new PedestalItemSyncS2CPacket(itemPedestal.getItem(), pos));
            }
            if (itemPedestal.alwaysDisplayName()) {
                itemPedestal.setAlwaysDisplayNameAndSync();
            }
        }
        super.onPlace(blockState, level, pos, oldState, movedByPiston);
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
    public ItemStack getCloneItemStack(BlockState blockState, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof ItemPedestalBlockEntity itemPedestal) {
            if (!itemPedestal.isEmpty()) {
                return Screen.hasShiftDown() ? blockState.getBlock().asItem().getDefaultInstance() : itemPedestal.getItem().copy();
            }
        }

        return blockState.getBlock().asItem().getDefaultInstance();
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState blockState) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos pos) {
        BlockEntity blockEntity = level.getExistingBlockEntity(pos);
        if (blockEntity instanceof ItemPedestalBlockEntity itemPedestal) {
            return itemPedestal.isEmpty() ? 0 : 15;
        }
        return super.getAnalogOutputSignal(blockState, level, pos);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
        return PEDESTAL_SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }

}
