package com.mikitellurium.telluriumsrandomstuff.fluid.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.pathfinder.PathComputationType;

import java.util.function.Supplier;

public class SoulLavaBlock extends LiquidBlock {

    public SoulLavaBlock(Supplier<? extends FlowingFluid> pFluid) {
        super(pFluid, Properties.copy(Blocks.LAVA));
    }

    @Override
    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return false;
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        if (this.shouldSpreadLiquid(pLevel, pPos, pState)) {
            pLevel.scheduleTick(pPos, pState.getFluidState().getType(), this.getFluid().getTickDelay(pLevel));
        }
    }

    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        if (this.shouldSpreadLiquid(pLevel, pPos, pState)) {
            pLevel.scheduleTick(pPos, pState.getFluidState().getType(), this.getFluid().getTickDelay(pLevel));
        }
    }

    private boolean shouldSpreadLiquid(Level pLevel, BlockPos pPos, BlockState pState) {
        boolean flag = pLevel.getBlockState(pPos.below()).is(Blocks.SOUL_SOIL);

        for(Direction direction : POSSIBLE_FLOW_DIRECTIONS) {
            BlockPos blockpos = pPos.relative(direction.getOpposite());
            if (pLevel.getFluidState(blockpos).is(FluidTags.WATER)) {
                Block block = pLevel.getFluidState(pPos).isSource() ? Blocks.CRYING_OBSIDIAN : Blocks.SOUL_SOIL;
                pLevel.setBlockAndUpdate(pPos, net.minecraftforge.event.ForgeEventFactory.fireFluidPlaceBlockEvent(pLevel, pPos, pPos, block.defaultBlockState()));
                this.fizz(pLevel, pPos);
                return false;
            }

            if (flag && pLevel.getBlockState(blockpos).is(Blocks.BLUE_ICE)) {
                pLevel.setBlockAndUpdate(pPos, net.minecraftforge.event.ForgeEventFactory.fireFluidPlaceBlockEvent(pLevel, pPos, pPos, Blocks.BASALT.defaultBlockState()));
                this.fizz(pLevel, pPos);
                return false;

            }
        }

        return true;
    }

    private void fizz(LevelAccessor pLevel, BlockPos pPos) {
        pLevel.levelEvent(1501, pPos, 0);
    }

}
