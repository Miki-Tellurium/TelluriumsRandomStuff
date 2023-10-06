package com.mikitellurium.telluriumsrandomstuff.common.block;

import com.mikitellurium.telluriumsrandomstuff.util.ParticleUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.MagmaBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.jetbrains.annotations.Nullable;

public class GrateMagmaBlock extends MagmaBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public GrateMagmaBlock() {
        super(Properties.copy(Blocks.MAGMA_BLOCK)
                .emissiveRendering((blockState, blockGetter, blockPos) -> true));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState blockState, Entity entity) {
        //super.stepOn(level, pos, blockState, entity);
        // Disabling the magma block stepOn method damage
    }

    @Override
    public void tick(BlockState blockState, ServerLevel level, BlockPos pos, RandomSource random) {
        CustomBubbleColumnBlock.updateColumn(level, pos.above(), blockState);
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos pos, RandomSource random) {
        if (level.isRaining()) {
            ParticleUtils.spawnRainParticles(level, pos, blockState, random);
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

}