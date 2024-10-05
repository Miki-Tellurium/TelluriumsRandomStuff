package com.mikitellurium.telluriumsrandomstuff.common.block;

import com.mikitellurium.telluriumsrandomstuff.common.blockentity.AbstractSoulFueledBlockEntity;
import com.mikitellurium.telluriumsrandomstuff.common.blockentity.AbstractSoulSmeltingBlockEntity;
import com.mikitellurium.telluriumsrandomstuff.common.blockentity.AlchemixerBlockEntity;
import com.mikitellurium.telluriumsrandomstuff.common.blockentity.SoulCompactorBlockEntity;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlockEntities;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class SoulCompactorBlock extends AbstractFurnaceBlock {

    public SoulCompactorBlock() {
        super(Properties.copy(ModBlocks.SOUL_FURNACE.get()));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState blockState) {
        return new SoulCompactorBlockEntity(pos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState,
                                                                  BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, ModBlockEntities.SOUL_COMPACTOR.get(),
                (tickLevel, blockPos, state, soulCompactor) -> soulCompactor.tick(tickLevel, blockPos, state));
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos pos, Player player, InteractionHand hand,
                                 BlockHitResult hitResult) {
        if (!level.isClientSide) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof SoulCompactorBlockEntity && player instanceof ServerPlayer) {
                this.openContainer(level, pos, player);
            } else {
                throw new IllegalStateException("Container provider or player is missing");
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    protected void openContainer(Level level, BlockPos pos, Player player) {
        NetworkHooks.openScreen((ServerPlayer)player, (SoulCompactorBlockEntity)level.getBlockEntity(pos), pos);
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (blockState.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof SoulCompactorBlockEntity) {
                ((SoulCompactorBlockEntity) blockEntity).dropItemsOnBreak();
            }
        }
        super.onRemove(blockState, level, pos, newState, isMoving);
    }

    public int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos pos) {
        BlockEntity blockEntity = level.getExistingBlockEntity(pos);
        if (blockEntity instanceof AbstractSoulSmeltingBlockEntity<?> smeltingBlockEntity) {
            return smeltingBlockEntity.getAnalogOutputSignal();
        }
        return super.getAnalogOutputSignal(blockState, level, pos);
    }

}
