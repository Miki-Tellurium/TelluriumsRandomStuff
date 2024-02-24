package com.mikitellurium.telluriumsrandomstuff.common.block;

import com.mikitellurium.telluriumsrandomstuff.common.blockentity.SoulCompactorBlockEntity;
import com.mikitellurium.telluriumsrandomstuff.common.blockentity.SoulFurnaceBlockEntity;
import com.mikitellurium.telluriumsrandomstuff.common.blockentity.SoulInfuserBlockEntity;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlockEntities;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
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

//    @Override
//    public void animateTick(BlockState blockState, Level level, BlockPos pos, RandomSource random) {
//        if (blockState.getValue(LIT)) {
//            double pX = pos.getX() + 0.5D;
//            double pY = pos.getY() + 0.7D;
//            double pZ = pos.getZ() + 0.5D;
//            if (random.nextDouble() < 0.1D) {
//                level.playLocalSound(pX, pY, pZ, SoundEvents.LAVA_POP, SoundSource.BLOCKS, 1.0F, 1.0F, false);
//            }
//
//            if (random.nextInt(3) == 0) {
//                Direction direction = blockState.getValue(FACING);
//                Direction.Axis direction$axis = direction.getAxis();
//                double d0 = random.nextDouble() * 0.6D - 0.3D;
//                double d1 = direction$axis == Direction.Axis.X ? (double) direction.getStepX() * 0.52D : d0;
//                double d2 = random.nextDouble() * 3.0D / 16.0D;
//                double d3 = direction$axis == Direction.Axis.Z ? (double) direction.getStepZ() * 0.52D : d0;
//                level.addParticle(ParticleTypes.SMOKE, pX + d1, pY + d2, pZ + d3, 0.0D, -0.04D, 0.0D);
//                level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, pX + d1, pY + d2, pZ + d3, 0.0D, -0.04D, 0.0D);
//            }
//        }
//    }

}
