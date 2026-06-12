package com.mikitellurium.telluriumsrandomstuff.common.block;

import com.mikitellurium.telluriumsrandomstuff.registry.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class FloatingWalkwayBlock extends Block implements BucketPickup {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty SUNKEN = BooleanProperty.create("sunken");
    protected static final VoxelShape SHAPE_FLOATING = Block.box(1.0D, 13.0D, 1.0D, 15.0D, 15.0D, 15.0D);
    protected static final VoxelShape SHAPE_SUNKEN = Block.box(1.0D, 12.0D, 1.0D, 15.0D, 14.0D, 15.0D);

    public FloatingWalkwayBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(FACING, Direction.NORTH)
                .setValue(SUNKEN, false)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, SUNKEN);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(SUNKEN) ? SHAPE_SUNKEN : SHAPE_FLOATING;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE_FLOATING;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        level.scheduleTick(pos, this, 10);
        super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return Fluids.WATER.getSource(false);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        FluidState fluidState = level.getFluidState(pos);
        return fluidState.is(Fluids.WATER) && fluidState.isSource();
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        List<LivingEntity> entityList = level.getEntities(EntityTypeTest.forClass(LivingEntity.class), AABB.unitCubeFromLowerCorner(Vec3.atLowerCornerOf(pos.above())), Entity::isAlive);
        if (entityList.isEmpty() && state.getValue(SUNKEN)) {
            BlockState newState = state.setValue(SUNKEN, false);
            level.setBlockAndUpdate(pos, newState);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(newState));
        } else {
            level.scheduleTick(pos, this, 10);
        }
        super.tick(state, level, pos, random);
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if (!level.isClientSide) {
            if (entity instanceof LivingEntity && !state.getValue(SUNKEN)) {
                BlockState newState = state.setValue(SUNKEN, true);
                level.setBlockAndUpdate(pos, newState);
                RandomSource random = level.random;
                if (!level.getFluidState(pos.above()).is(Fluids.WATER)) {
                    spawnBubbleParticles((ServerLevel) level, newState, pos, random);
                }
                Holder<SoundEvent> sound = Holder.direct(ModSounds.FLOATING_WALKWAY_SUNK.get());
                float pitch = (float) (random.nextInt(4) + 1) / 10;
                level.playSeededSound(null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, sound, SoundSource.BLOCKS, 0.3F, pitch, random.nextLong());
                level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(entity, newState));
            }
            level.scheduleTick(pos, this, 10);
        }
        super.stepOn(level, pos, state, entity);
    }

    private void spawnBubbleParticles(ServerLevel level, BlockState state, BlockPos pos, RandomSource random) {
        int count = random.nextInt(4) + 4;
        for (int i = 0; i < count; i++) {
            double d0 = random.nextDouble();
            double d1 = random.nextDouble();
            double d2 = state.getShape(level, pos).max(Direction.Axis.Y, d0, d1);
            level.sendParticles(ParticleTypes.BUBBLE, (double) pos.getX() + d0, (double) pos.getY() + d2, (double) pos.getZ() + d1, 0, 0.0D, 0.0D, 0.0D, 0);
        }
    }

    @Override
    public boolean canBeReplaced(BlockState state, Fluid fluid) {
        return false;
    }

    @Override
    public ItemStack pickupBlock(LevelAccessor level, BlockPos pos, BlockState state) {
        level.destroyBlock(pos, true);
        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
        return new ItemStack(Items.WATER_BUCKET);
    }

    @Override
    public Optional<SoundEvent> getPickupSound() {
        return Fluids.WATER.getPickupSound();
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter level, List<Component> components, TooltipFlag flag) {
        components.add(Component.translatable("block.telluriumsrandomstuff.floating_walkway.tooltip"));
    }
}
