package com.mikitellurium.telluriumsrandomstuff.block.custom;

import com.mikitellurium.telluriumsrandomstuff.block.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.tag.ModTags;
import com.mikitellurium.telluriumsrandomstuff.util.LevelUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BubbleColumnBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;

public class CustomBubbleColumnBlock extends BubbleColumnBlock {

    public CustomBubbleColumnBlock() {
        super(Properties.of()
                .mapColor(MapColor.WATER)
                .noCollission()
                .noLootTable());
    }

    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        updateColumn(pLevel, pPos, pState, pLevel.getBlockState(pPos.below()));
    }

    public static void updateColumn(LevelAccessor pLevel, BlockPos pPos, BlockState pState) {
        updateColumn(pLevel, pPos, pLevel.getBlockState(pPos), pState);
    }

    public static void updateColumn(LevelAccessor pLevel, BlockPos pPos, BlockState pFluid, BlockState pState) {
        if (canExistIn(pFluid)) {
            BlockState blockstate = getColumnState(pState);
            pLevel.setBlock(pPos, blockstate, 2);
            BlockPos.MutableBlockPos blockpos$mutableblockpos = pPos.mutable().move(Direction.UP);

            while(canExistIn(pLevel.getBlockState(blockpos$mutableblockpos))) {
                if (!pLevel.setBlock(blockpos$mutableblockpos, blockstate, 2)) {
                    return;
                }

                blockpos$mutableblockpos.move(Direction.UP);
            }

        }
    }

    private static BlockState getColumnState(BlockState blockState) {
        if (blockState.is(ModBlocks.CUSTOM_BUBBLE_COLUMN.get())) {
            return blockState;
        } else if (blockState.is(ModTags.Blocks.BUBBLE_COLUMN_LIFT_UP)) {
            return ModBlocks.CUSTOM_BUBBLE_COLUMN.get().defaultBlockState().setValue(DRAG_DOWN, Boolean.valueOf(false));
        } else {
            return blockState.is(ModTags.Blocks.BUBBLE_COLUMN_DRAG_DOWN) ? ModBlocks.CUSTOM_BUBBLE_COLUMN.get().defaultBlockState().setValue(DRAG_DOWN, Boolean.valueOf(true)) : Blocks.WATER.defaultBlockState();
        }
    }

    private static boolean canExistIn(BlockState pBlockState) {
        return pBlockState.is(ModBlocks.CUSTOM_BUBBLE_COLUMN.get()) || pBlockState.is(Blocks.WATER) && pBlockState.getFluidState().getAmount() >= 8 && pBlockState.getFluidState().isSource();
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockState blockstate = pLevel.getBlockState(pPos.below());
        return blockstate.is(ModBlocks.CUSTOM_BUBBLE_COLUMN.get()) || blockstate.is(ModTags.Blocks.BUBBLE_COLUMN_GENERATOR);
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        if (!canSurvive(pState, pLevel, pCurrentPos) || pFacing == Direction.DOWN || pFacing == Direction.UP && !pFacingState.is(ModBlocks.CUSTOM_BUBBLE_COLUMN.get()) && canExistIn(pFacingState)) {
            pLevel.scheduleTick(pCurrentPos, this, 5);
        }

        return pState;
    }
}