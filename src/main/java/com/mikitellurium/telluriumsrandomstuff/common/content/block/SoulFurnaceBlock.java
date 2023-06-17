package com.mikitellurium.telluriumsrandomstuff.common.content.block;

import com.mikitellurium.telluriumsrandomstuff.registry.ModBlockEntities;
import com.mikitellurium.telluriumsrandomstuff.common.content.blockentity.SoulFurnaceBlockEntity;
import com.mikitellurium.telluriumsrandomstuff.common.networking.ModMessages;
import com.mikitellurium.telluriumsrandomstuff.common.networking.packets.FluidSyncS2CPacket;
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

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof SoulFurnaceBlockEntity && pPlayer instanceof ServerPlayer) {
                this.openContainer(pLevel, pPos, pPlayer);
            } else {
                throw new IllegalStateException("Container provider or player is missing");
            }
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide);
    }

    @Override
    protected void openContainer(Level pLevel, BlockPos pPos, Player pPlayer) {
        NetworkHooks.openScreen((ServerPlayer)pPlayer, (SoulFurnaceBlockEntity)pLevel.getBlockEntity(pPos), pPos);
        SoulFurnaceBlockEntity blockEntity = (SoulFurnaceBlockEntity) pLevel.getBlockEntity(pPos);
        // Update the fluid data on the client
        if (blockEntity != null) {
            ModMessages.sendToClients(new FluidSyncS2CPacket(blockEntity.getFluid(), blockEntity.getBlockPos()));
        } else {
            throw new IllegalStateException("Container provider is missing");
        }
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pState.getValue(LIT)) {
            double pX = pPos.getX() + 0.5D;
            double pY = pPos.getY();
            double pZ = pPos.getZ() + 0.5D;
            if (pRandom.nextDouble() < 0.1D) {
                pLevel.playLocalSound(pX, pY, pZ, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
            }

            Direction direction = pState.getValue(FACING);
            Direction.Axis direction$axis = direction.getAxis();
            double d0 = pRandom.nextDouble() * 0.6D - 0.3D;
            double d1 = direction$axis == Direction.Axis.X ? (double)direction.getStepX() * 0.52D : d0;
            double d2 = pRandom.nextDouble() * 6.0D / 16.0D;
            double d3 = direction$axis == Direction.Axis.Z ? (double)direction.getStepZ() * 0.52D : d0;
            pLevel.addParticle(ParticleTypes.SMOKE, pX + d1, pY + d2, pZ + d3, 0.0D, 0.0D, 0.0D);
            pLevel.addParticle(ParticleTypes.SOUL_FIRE_FLAME, pX + d1, pY + d2, pZ + d3, 0.0D, 0.0D, 0.0D);
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new SoulFurnaceBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState,
                                                                  BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, ModBlockEntities.SOUL_FURNACE.get(),
                SoulFurnaceBlockEntity::tick);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof SoulFurnaceBlockEntity) {
                ((SoulFurnaceBlockEntity) blockEntity).dropItemsOnBreak();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    public static int getLightLevel(BlockState state) {
        return state.getValue(LIT) ? 13 : 2;
    }

}
