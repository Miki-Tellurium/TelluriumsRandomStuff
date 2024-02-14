package com.mikitellurium.telluriumsrandomstuff.common.block;

import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.registry.ModParticles;
import com.mikitellurium.telluriumsrandomstuff.util.ParticleUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CauldronBlock;
import net.minecraft.world.level.block.SoulSandBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class InfusedSoulSandBlock extends SoulSandBlock {

    private final VoxelShape REQUIRED_SPACE_TO_DRIP_THROUGH_NON_SOLID_BLOCK =
            Block.box(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);
    private final float soulLavaDripChance = 0.032f;
    private final int depletionChance = 10;

    public InfusedSoulSandBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.SOUL_SAND)
                .randomTicks());
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos pos, RandomSource random) {
        if (level.getFluidState(pos.above()).is(Fluids.LAVA)) {
            if (random.nextInt(12) == 0) {
                double d0 = pos.getX() + random.nextDouble();
                double d1 = pos.getY() - 0.05d;
                double d2 = pos.getZ() + random.nextDouble();
                level.addParticle(ModParticles.SOUL_LAVA_HANG.get(), d0, d1, d2, 0.0D, 0.0D, 0.0D);
            }
        }

        if (level.isRaining()) {
            ParticleUtils.spawnRainParticles(level, pos, blockState, random);
        }
    }

    @Override
    public void tick(BlockState blockState, ServerLevel level, BlockPos pos, RandomSource random) {
        CustomBubbleColumnBlock.updateColumn(level, pos.above(), blockState);
    }

    @Override
    public void randomTick(BlockState blockState, ServerLevel level, BlockPos pos, RandomSource random) {
        if (level.getFluidState(pos.above()).is(Fluids.LAVA)) {
            if (random.nextFloat() < soulLavaDripChance) {
                BlockPos maybeCauldronPos = this.findFillableCauldronBelow(level, pos.below());
                if (maybeCauldronPos != null) {
                    BlockState soulLavaCauldron = ModBlocks.SOUL_LAVA_CAULDRON.get().defaultBlockState();
                    level.setBlockAndUpdate(pos.above(), Blocks.AIR.defaultBlockState());
                    level.setBlockAndUpdate(maybeCauldronPos, soulLavaCauldron);
                    level.gameEvent(GameEvent.BLOCK_CHANGE, maybeCauldronPos, GameEvent.Context.of(soulLavaCauldron));
                    level.playSound(null, maybeCauldronPos, SoundEvents.SOUL_ESCAPE, SoundSource.BLOCKS, 0.8f, 1.0f);
                    if (random.nextInt(100) < depletionChance) {
                        level.setBlockAndUpdate(pos, Blocks.SOUL_SAND.defaultBlockState());
                        level.playSound(null, pos, SoundEvents.SOUL_ESCAPE, SoundSource.BLOCKS, 0.8f, 1.5f);
                    }
                }
            }
        }
    }

    public BlockPos findFillableCauldronBelow(Level pLevel, BlockPos pPos) {
        Predicate<BlockState> predicate = (blockState) -> blockState.getBlock() instanceof CauldronBlock;
        BiPredicate<BlockPos, BlockState> bipredicate = (blockPos, blockState) -> canDripThrough(pLevel, blockPos, blockState);
        return findBlockVertical(pLevel, pPos, Direction.DOWN.getAxisDirection(), bipredicate, predicate, 11).orElse(null);
    }

    private boolean canDripThrough(BlockGetter pLevel, BlockPos pPos, BlockState pState) {
        if (pState.isAir()) {
            return true;
        } else if (pState.isSolidRender(pLevel, pPos)) {
            return false;
        } else if (!pState.getFluidState().isEmpty()) {
            return false;
        } else {
            VoxelShape voxelshape = pState.getCollisionShape(pLevel, pPos);
            return !Shapes.joinIsNotEmpty(REQUIRED_SPACE_TO_DRIP_THROUGH_NON_SOLID_BLOCK, voxelshape, BooleanOp.AND);
        }
    }

    @SuppressWarnings("SameParameterValue")
    private Optional<BlockPos> findBlockVertical(LevelAccessor pLevel, BlockPos pPos, Direction.AxisDirection pAxis,
                                                        BiPredicate<BlockPos, BlockState> pPositionalStatePredicate,
                                                        Predicate<BlockState> pStatePredicate, int pMaxIterations) {
        Direction direction = Direction.get(pAxis, Direction.Axis.Y);
        BlockPos.MutableBlockPos blockpos$mutableblockpos = pPos.mutable();

        for(int i = 1; i < pMaxIterations; ++i) {
            blockpos$mutableblockpos.move(direction);
            BlockState blockstate = pLevel.getBlockState(blockpos$mutableblockpos);
            if (pStatePredicate.test(blockstate)) {
                return Optional.of(blockpos$mutableblockpos.immutable());
            }

            if (pLevel.isOutsideBuildHeight(blockpos$mutableblockpos.getY()) ||
                    !pPositionalStatePredicate.test(blockpos$mutableblockpos, blockstate)) {
                return Optional.empty();
            }
        }

        return Optional.empty();
    }

}
