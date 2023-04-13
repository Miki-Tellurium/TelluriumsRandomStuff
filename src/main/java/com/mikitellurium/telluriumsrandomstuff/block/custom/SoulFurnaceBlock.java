package com.mikitellurium.telluriumsrandomstuff.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class SoulFurnaceBlock extends AbstractFurnaceBlock {

    public SoulFurnaceBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.BLAST_FURNACE)
                .lightLevel(SoulFurnaceBlock::getLightLevel)
                .emissiveRendering((blockState, blockGetter, blockPos) -> true));
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide()) {
            return InteractionResult.SUCCESS;
        } else {
            if (pState.getValue(LIT)) {
                pLevel.setBlock(pPos, pState.setValue(LIT, false), 2);
            } else {
                pLevel.setBlock(pPos, pState.setValue(LIT, true), 2);
            }
           System.out.println(pState.getValue(LIT));
           return InteractionResult.CONSUME;
        }
    }

    @Override
    protected void openContainer(Level pLevel, BlockPos pPos, Player pPlayer) {

    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return null;
    }

    public static int getLightLevel(BlockState state) {
        return state.getValue(LIT) ? 13 : 2;
    }

}
