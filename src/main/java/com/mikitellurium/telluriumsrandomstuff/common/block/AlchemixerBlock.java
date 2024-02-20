package com.mikitellurium.telluriumsrandomstuff.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class AlchemixerBlock extends Block {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty[] HAS_BOTTLE = new BooleanProperty[]{
            BlockStateProperties.HAS_BOTTLE_0,
            BlockStateProperties.HAS_BOTTLE_1,
            BlockStateProperties.HAS_BOTTLE_2
    };
    protected static final VoxelShape STAND = Block.box(7.0D, 0.0D, 7.0D, 9.0D, 14.0D, 9.0D);
    protected static final VoxelShape BASE_X = Block.box(5.0D, 0.0D, 1.0D, 11.0D, 2.0D, 15.0D);
    protected static final VoxelShape BASE_Z = Block.box(1.0D, 0.0D, 5.0D, 15.0D, 2.0D, 11.0D);
    protected static final VoxelShape SHAPE_X_AXIS = Shapes.or(STAND, BASE_X);
    protected static final VoxelShape SHAPE_Z_AXIS = Shapes.or(STAND, BASE_Z);

    public AlchemixerBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.BREWING_STAND));
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(HAS_BOTTLE[0], Boolean.valueOf(false))
                .setValue(HAS_BOTTLE[1], Boolean.valueOf(false))
                .setValue(HAS_BOTTLE[2], Boolean.valueOf(false))
        );
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos pos, Player player, InteractionHand hand,
                                 BlockHitResult result) {

        level.setBlockAndUpdate(pos, blockState
                .setValue(HAS_BOTTLE[0], !blockState.getValue(HAS_BOTTLE[0]))
                .setValue(HAS_BOTTLE[1], !blockState.getValue(HAS_BOTTLE[1]))
                .setValue(HAS_BOTTLE[2], !blockState.getValue(HAS_BOTTLE[2]))
        );
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos pos, RandomSource random) {
        double d0 = (double)pos.getX() + 0.4D + (double)random.nextFloat() * 0.2D;
        double d1 = (double)pos.getY() + 0.7D + (double)random.nextFloat() * 0.3D;
        double d2 = (double)pos.getZ() + 0.4D + (double)random.nextFloat() * 0.2D;
        level.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        if (random.nextFloat() > 0.9F) {
            d0 = (double)pos.getX() + 0.4D + (double)random.nextFloat() * 0.2D;
            d1 = (double)pos.getY() + 0.1D + (double)random.nextFloat() * 0.5D;
            d2 = (double)pos.getZ() + 0.4D + (double)random.nextFloat() * 0.2D;
            level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, d0, d1, d2, 0.0D, 0.01D, 0.0D);
        }
    }

    @Override
    public boolean isPathfindable(BlockState blockState, BlockGetter level, BlockPos pos, PathComputationType type) {
        return false;
    }

    public VoxelShape getShape(BlockState blockState, BlockGetter level, BlockPos pos, CollisionContext context) {
        return blockState.getValue(FACING).getAxis() == Direction.Axis.X ? SHAPE_Z_AXIS : SHAPE_X_AXIS;
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, HAS_BOTTLE[0], HAS_BOTTLE[1], HAS_BOTTLE[2]);
    }

}
