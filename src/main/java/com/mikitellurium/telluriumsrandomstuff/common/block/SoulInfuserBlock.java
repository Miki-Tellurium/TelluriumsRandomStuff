package com.mikitellurium.telluriumsrandomstuff.common.block;

import com.mikitellurium.telluriumsrandomstuff.common.blockentity.AbstractSoulSmeltingBlockEntity;
import com.mikitellurium.telluriumsrandomstuff.common.blockentity.SoulInfuserBlockEntity;
import com.mikitellurium.telluriumsrandomstuff.lib.TickingFurnaceBlock;
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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class SoulInfuserBlock extends TickingFurnaceBlock {

    public SoulInfuserBlock() {
        super(SoulInfuserBlockEntity::new, BlockBehaviour.Properties.copy(Blocks.BLAST_FURNACE));
    }

    @Override
    public BlockEntityType<?> getBlockEntityType() {
        return ModBlockEntities.SOUL_INFUSER.get();
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos pos, Player player, InteractionHand hand,
                                 BlockHitResult hitResult) {
        if (!level.isClientSide) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof SoulInfuserBlockEntity && player instanceof ServerPlayer) {
                this.openContainer(level, pos, player);
            } else {
                throw new IllegalStateException("Container provider or player is missing");
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    protected void openContainer(Level level, BlockPos pos, Player player) {
        NetworkHooks.openScreen((ServerPlayer)player, (SoulInfuserBlockEntity)level.getBlockEntity(pos), pos);
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos pos, RandomSource random) {
        if (blockState.getValue(LIT)) {
            double x = pos.getX() + 0.5D;
            double y = pos.getY();
            double z = pos.getZ() + 0.5D;
            if (random.nextDouble() < 0.1D) {
                level.playLocalSound(x, y, z, SoundEvents.BLASTFURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
            }

            Direction direction = blockState.getValue(FACING);
            Direction.Axis direction$axis = direction.getAxis();
            double d = random.nextDouble() * 0.6D - 0.3D;
            double xAxis = direction$axis == Direction.Axis.X ? direction.getStepX() * 0.52D : d;
            double d1 = random.nextDouble() * 9.0D / 16.0D;
            double yAxis = direction$axis == Direction.Axis.Z ? direction.getStepZ() * 0.52D : d;
            level.addParticle(ParticleTypes.SMOKE, x + xAxis, y + d1, z + yAxis, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (blockState.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof SoulInfuserBlockEntity) {
                ((SoulInfuserBlockEntity) blockEntity).dropItemsOnBreak();
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
