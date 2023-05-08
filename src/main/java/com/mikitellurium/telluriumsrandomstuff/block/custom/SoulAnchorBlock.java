package com.mikitellurium.telluriumsrandomstuff.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

public class SoulAnchorBlock extends Block {

    public static final BooleanProperty CHARGED = BooleanProperty.create("charged");

    public SoulAnchorBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.RESPAWN_ANCHOR)
                .lightLevel(SoulAnchorBlock::getSoulAnchorLightLevel));
        this.registerDefaultState(this.stateDefinition.any().setValue(CHARGED, false));
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos pos, Player player,
                                 InteractionHand interactionHand, BlockHitResult hitResult) {
        if (!level.isClientSide) {
            boolean flag = blockState.getValue(CHARGED);
            level.setBlockAndUpdate(pos, blockState.setValue(CHARGED, !flag));
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    public static int getSoulAnchorLightLevel(BlockState blockState) {
        return blockState.getValue(CHARGED) ? 12 : 0;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(CHARGED);
    }

}
