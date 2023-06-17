package com.mikitellurium.telluriumsrandomstuff.common.content.block;

import com.mikitellurium.telluriumsrandomstuff.registry.ModParticles;
import com.mikitellurium.telluriumsrandomstuff.registry.ModSoundTypes;
import com.mikitellurium.telluriumsrandomstuff.util.ParticleUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

public class GrateSoulSandBlock extends SoulSandBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public GrateSoulSandBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.SOUL_SAND)
                .speedFactor(1.0f)
                .sound(ModSoundTypes.GRATE_SOUL_SAND));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (level.getFluidState(pos.above()).is(Fluids.LAVA)) {
            if (random.nextInt(12) == 0) {
                double d0 = pos.getX() + random.nextDouble();
                double d1 = pos.getY() - 0.05d;
                double d2 = pos.getZ() + random.nextDouble();
                level.addParticle(ModParticles.SOUL_LAVA_HANG.get(), d0, d1, d2, 0.0D, 0.0D, 0.0D);
            }
        }

        if (level.isRaining()) {
            ParticleUtils.handleRainParticles(level, pos, state, random);
        }
    }

    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        CustomBubbleColumnBlock.updateColumn(pLevel, pPos.above(), pState);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

}
