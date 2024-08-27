package com.mikitellurium.telluriumsrandomstuff.common.block;

import com.mikitellurium.telluriumsrandomstuff.common.blockentity.AwakenedSculkShriekerBlockEntity;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlockEntities;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SculkShriekerBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class AwakenedSculkShriekerBlock extends SculkShriekerBlock {

    public AwakenedSculkShriekerBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.SCULK_SHRIEKER));
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(SHRIEKING, false)
                .setValue(WATERLOGGED, false)
                .setValue(CAN_SUMMON, true));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState blockState) {
        return new AwakenedSculkShriekerBlockEntity(pos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState,
                                                                  BlockEntityType<T> blockEntityType) {
        return BaseEntityBlock.createTickerHelper(blockEntityType, ModBlockEntities.AWAKENED_SCULK_SHRIEKER.get(),
                (tickLevel, pos, state, shrieker) -> shrieker.tick(level, pos, state));
    }

    public void tick(BlockState blockState, ServerLevel level, BlockPos pos, RandomSource random) {
        if (blockState.getValue(SHRIEKING)) {
            level.setBlockAndUpdate(pos, blockState.setValue(SHRIEKING, false));
            level.getBlockEntity(pos, ModBlockEntities.AWAKENED_SCULK_SHRIEKER.get()).ifPresent((shrieker) -> {
                shrieker.tryRespond(level);
            });
        }
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos pos, Player player, InteractionHand interactionHand, BlockHitResult hitResult) {
        if (level instanceof ServerLevel serverLevel) {
            if (blockState.getValue(CAN_SUMMON) && !blockState.getValue(SHRIEKING)) {
                ItemStack itemStack = player.getItemInHand(interactionHand);
                if (itemStack.is(ModItems.SOUL_CLUSTER.get())) {
                    if (!player.isCreative()) itemStack.shrink(1);
                    serverLevel.getBlockEntity(pos, ModBlockEntities.AWAKENED_SCULK_SHRIEKER.get()).ifPresent((shrieker) -> {
                        shrieker.tryShriek(serverLevel, (ServerPlayer) player);
                    });
                }
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState blockState, Entity entity) {
        // Disable shrieker stepOn method
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (level instanceof ServerLevel serverlevel) {
            if (blockState.getValue(SHRIEKING) && !blockState.is(newState.getBlock())) {
                serverlevel.getBlockEntity(pos, ModBlockEntities.AWAKENED_SCULK_SHRIEKER.get())
                        .ifPresent((shrieker) -> shrieker.tryRespond(serverlevel));
            }
        }

        super.onRemove(blockState, level, pos, newState, isMoving);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState blockState) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos pos) {
        return blockState.getValue(CAN_SUMMON) ? 15 : 0;
    }

}
