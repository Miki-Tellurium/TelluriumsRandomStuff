package com.mikitellurium.telluriumsrandomstuff.common.content.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ItemPedestalBlock extends Block {

    private final VoxelShape PEDESTAL_SHAPE = Block.box(1.0D, 0.0D, 1.0D,
            15.0D, 15.0D, 15.0D);

    public ItemPedestalBlock(Properties properties) {
        super(properties.noOcclusion());
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
        return PEDESTAL_SHAPE;
    }

}
