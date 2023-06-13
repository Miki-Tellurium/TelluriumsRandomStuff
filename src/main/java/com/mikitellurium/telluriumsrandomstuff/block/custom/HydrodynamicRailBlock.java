package com.mikitellurium.telluriumsrandomstuff.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.minecraft.world.phys.Vec3;

public class HydrodynamicRailBlock extends BaseRailBlock implements SimpleWaterloggedBlock {

    public static final EnumProperty<RailShape> SHAPE = BlockStateProperties.RAIL_SHAPE_STRAIGHT;

    public HydrodynamicRailBlock() {
        super(true, Properties.of()
                .noCollission()
                .strength(0.7F)
                .sound(SoundType.METAL));
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(SHAPE, RailShape.NORTH_SOUTH)
                .setValue(WATERLOGGED, Boolean.valueOf(false)));
    }

    @Override
    public void onMinecartPass(BlockState state, Level level, BlockPos pos, AbstractMinecart cart) {
        if (!level.isClientSide) {
            Vec3 vec = cart.getDeltaMovement();
            double distance = vec.horizontalDistance();
            if (state.getValue(WATERLOGGED)) {
                if (distance > 0.01D) {
                    double increase = 0.06D; // how much the minecart speed increase, the higher this is the faster
                    cart.setDeltaMovement(vec.add(vec.x / distance * increase, 0.0D, vec.z / distance * increase));
                }
            } else {
                if (distance < 0.03D) {
                    cart.setDeltaMovement(Vec3.ZERO);
                } else {
                    cart.setDeltaMovement(cart.getDeltaMovement().multiply(0.5D, 0.0D, 0.5D));
                }
            }
        }
    }

    @Override
    public float getRailMaxSpeed(BlockState state, Level level, BlockPos pos, AbstractMinecart cart) {
        return state.getValue(WATERLOGGED) ? 0.5f : cart.getMaxCartSpeedOnRail() * 0.1f;
    }

    @Override
    public Property<RailShape> getShapeProperty() {
        return SHAPE;
    }

    @Override
    public BlockState rotate(BlockState blockState, Rotation rotation) {
        switch(rotation) {
            case CLOCKWISE_180:
                switch(blockState.getValue(SHAPE)) {
                    case ASCENDING_EAST:
                        return blockState.setValue(SHAPE, RailShape.ASCENDING_WEST);
                    case ASCENDING_WEST:
                        return blockState.setValue(SHAPE, RailShape.ASCENDING_EAST);
                    case ASCENDING_NORTH:
                        return blockState.setValue(SHAPE, RailShape.ASCENDING_SOUTH);
                    case ASCENDING_SOUTH:
                        return blockState.setValue(SHAPE, RailShape.ASCENDING_NORTH);
                    case SOUTH_EAST:
                        return blockState.setValue(SHAPE, RailShape.NORTH_WEST);
                    case SOUTH_WEST:
                        return blockState.setValue(SHAPE, RailShape.NORTH_EAST);
                    case NORTH_WEST:
                        return blockState.setValue(SHAPE, RailShape.SOUTH_EAST);
                    case NORTH_EAST:
                        return blockState.setValue(SHAPE, RailShape.SOUTH_WEST);
                    case NORTH_SOUTH: //Forge fix: MC-196102
                    case EAST_WEST:
                        return blockState;
                }
            case COUNTERCLOCKWISE_90:
                switch(blockState.getValue(SHAPE)) {
                    case NORTH_SOUTH:
                        return blockState.setValue(SHAPE, RailShape.EAST_WEST);
                    case EAST_WEST:
                        return blockState.setValue(SHAPE, RailShape.NORTH_SOUTH);
                    case ASCENDING_EAST:
                        return blockState.setValue(SHAPE, RailShape.ASCENDING_NORTH);
                    case ASCENDING_WEST:
                        return blockState.setValue(SHAPE, RailShape.ASCENDING_SOUTH);
                    case ASCENDING_NORTH:
                        return blockState.setValue(SHAPE, RailShape.ASCENDING_WEST);
                    case ASCENDING_SOUTH:
                        return blockState.setValue(SHAPE, RailShape.ASCENDING_EAST);
                    case SOUTH_EAST:
                        return blockState.setValue(SHAPE, RailShape.NORTH_EAST);
                    case SOUTH_WEST:
                        return blockState.setValue(SHAPE, RailShape.SOUTH_EAST);
                    case NORTH_WEST:
                        return blockState.setValue(SHAPE, RailShape.SOUTH_WEST);
                    case NORTH_EAST:
                        return blockState.setValue(SHAPE, RailShape.NORTH_WEST);
                }
            case CLOCKWISE_90:
                switch(blockState.getValue(SHAPE)) {
                    case NORTH_SOUTH:
                        return blockState.setValue(SHAPE, RailShape.EAST_WEST);
                    case EAST_WEST:
                        return blockState.setValue(SHAPE, RailShape.NORTH_SOUTH);
                    case ASCENDING_EAST:
                        return blockState.setValue(SHAPE, RailShape.ASCENDING_SOUTH);
                    case ASCENDING_WEST:
                        return blockState.setValue(SHAPE, RailShape.ASCENDING_NORTH);
                    case ASCENDING_NORTH:
                        return blockState.setValue(SHAPE, RailShape.ASCENDING_EAST);
                    case ASCENDING_SOUTH:
                        return blockState.setValue(SHAPE, RailShape.ASCENDING_WEST);
                    case SOUTH_EAST:
                        return blockState.setValue(SHAPE, RailShape.SOUTH_WEST);
                    case SOUTH_WEST:
                        return blockState.setValue(SHAPE, RailShape.NORTH_WEST);
                    case NORTH_WEST:
                        return blockState.setValue(SHAPE, RailShape.NORTH_EAST);
                    case NORTH_EAST:
                        return blockState.setValue(SHAPE, RailShape.SOUTH_EAST);
                }
            default:
                return blockState;
        }
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        RailShape railshape = pState.getValue(SHAPE);
        switch (pMirror) {
            case LEFT_RIGHT:
                switch (railshape) {
                    case ASCENDING_NORTH:
                        return pState.setValue(SHAPE, RailShape.ASCENDING_SOUTH);
                    case ASCENDING_SOUTH:
                        return pState.setValue(SHAPE, RailShape.ASCENDING_NORTH);
                    case SOUTH_EAST:
                        return pState.setValue(SHAPE, RailShape.NORTH_EAST);
                    case SOUTH_WEST:
                        return pState.setValue(SHAPE, RailShape.NORTH_WEST);
                    case NORTH_WEST:
                        return pState.setValue(SHAPE, RailShape.SOUTH_WEST);
                    case NORTH_EAST:
                        return pState.setValue(SHAPE, RailShape.SOUTH_EAST);
                    default:
                        return super.mirror(pState, pMirror);
                }
            case FRONT_BACK:
                switch (railshape) {
                    case ASCENDING_EAST:
                        return pState.setValue(SHAPE, RailShape.ASCENDING_WEST);
                    case ASCENDING_WEST:
                        return pState.setValue(SHAPE, RailShape.ASCENDING_EAST);
                    case ASCENDING_NORTH:
                    case ASCENDING_SOUTH:
                    default:
                        break;
                    case SOUTH_EAST:
                        return pState.setValue(SHAPE, RailShape.SOUTH_WEST);
                    case SOUTH_WEST:
                        return pState.setValue(SHAPE, RailShape.SOUTH_EAST);
                    case NORTH_WEST:
                        return pState.setValue(SHAPE, RailShape.NORTH_EAST);
                    case NORTH_EAST:
                        return pState.setValue(SHAPE, RailShape.NORTH_WEST);
                }
            default:
                return pState;
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(getShapeProperty(), WATERLOGGED);
    }

}
