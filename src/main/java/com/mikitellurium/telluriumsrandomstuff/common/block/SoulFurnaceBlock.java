package com.mikitellurium.telluriumsrandomstuff.common.block;

import com.mikitellurium.telluriumsrandomstuff.common.blockentity.SoulFurnaceBlockEntity;
import com.mikitellurium.telluriumsrandomstuff.lib.TickingBlockEntity;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlockEntities;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class SoulFurnaceBlock extends AbstractFurnaceBlock {

    public SoulFurnaceBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.BLAST_FURNACE)
                .explosionResistance(1200.0f)
                .lightLevel(SoulFurnaceBlock::getLightLevel)
                .emissiveRendering((blockState, blockGetter, blockPos) -> true));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState blockState) {
        return new SoulFurnaceBlockEntity(pos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntities.SOUL_FURNACE.get(),
                (tickLevel, pos, state, blockEntity) -> blockEntity.tick(tickLevel, pos, state));
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos pos, Player player, InteractionHand hand,
                                 BlockHitResult hitResult) {
        if (!level.isClientSide) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof SoulFurnaceBlockEntity && player instanceof ServerPlayer) {
                this.openContainer(level, pos, player);
            } else {
                throw new IllegalStateException("Container provider or player is missing");
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    protected void openContainer(Level level, BlockPos pos, Player player) {
        NetworkHooks.openScreen((ServerPlayer)player, (SoulFurnaceBlockEntity)level.getBlockEntity(pos), pos);
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos pos, RandomSource random) {
        if (blockState.getValue(LIT)) {
            double pX = pos.getX() + 0.5D;
            double pY = pos.getY();
            double pZ = pos.getZ() + 0.5D;
            if (random.nextDouble() < 0.1D) {
                level.playLocalSound(pX, pY, pZ, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
            }

            Direction direction = blockState.getValue(FACING);
            Direction.Axis direction$axis = direction.getAxis();
            double d0 = random.nextDouble() * 0.6D - 0.3D;
            double d1 = direction$axis == Direction.Axis.X ? (double)direction.getStepX() * 0.52D : d0;
            double d2 = random.nextDouble() * 6.0D / 16.0D;
            double d3 = direction$axis == Direction.Axis.Z ? (double)direction.getStepZ() * 0.52D : d0;
            level.addParticle(ParticleTypes.SMOKE, pX + d1, pY + d2, pZ + d3, 0.0D, 0.0D, 0.0D);
            level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, pX + d1, pY + d2, pZ + d3, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (blockState.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof SoulFurnaceBlockEntity) {
                ((SoulFurnaceBlockEntity) blockEntity).dropItemsOnBreak();
            }
        }
        super.onRemove(blockState, level, pos, newState, isMoving);
    }

    public int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos pos) {
        BlockEntity blockEntity = level.getExistingBlockEntity(pos);
        if (blockEntity instanceof SoulFurnaceBlockEntity soulFurnace) {
            return soulFurnace.getAnalogOutputSignal();
        }
        return super.getAnalogOutputSignal(blockState, level, pos);
    }

    public static int getLightLevel(BlockState state) {
        return state.getValue(LIT) ? 13 : 2;
    }

}
