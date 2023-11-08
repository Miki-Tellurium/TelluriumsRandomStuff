package com.mikitellurium.telluriumsrandomstuff.common.block;

import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.registry.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BubbleColumnBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

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

    /* Events */
    private static boolean wasInBubbleColumn;
    private static boolean firstTick = true;

    @SubscribeEvent
    public static void onBubbleColumnEnterSoundEvent(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START || event.player == null) return;
        if (event.player.level().isClientSide) {
            // Play a sound when entering bubble columns
            BlockState blockstate = event.player.level().getBlockStatesIfLoaded(event.player.getBoundingBox().inflate(0.0D, -0.4F, 0.0D)
                            .deflate(1.0E-6D)).filter((block) -> block.is(ModBlocks.CUSTOM_BUBBLE_COLUMN.get()))
                    .findFirst().orElse(null);
            if (blockstate != null) {
                if (!wasInBubbleColumn && !firstTick && blockstate.is(ModBlocks.CUSTOM_BUBBLE_COLUMN.get()) && !event.player.isSpectator()) {
                    boolean flag = blockstate.getValue(CustomBubbleColumnBlock.DRAG_DOWN);
                    if (flag) {
                        event.player.playSound(SoundEvents.BUBBLE_COLUMN_WHIRLPOOL_INSIDE, 1.0F, 1.0F);
                    } else {
                        event.player.playSound(SoundEvents.BUBBLE_COLUMN_UPWARDS_INSIDE, 1.0F, 1.0F);
                    }
                }

                wasInBubbleColumn = true;
            } else {
                wasInBubbleColumn = false;
            }

            firstTick = false;
        }

    }

    @SubscribeEvent
    public static void livingTickEvent(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.level().isClientSide) {
            return;
        }
        // Increase entity air supply when inside bubble column
        BlockPos pos = BlockPos.containing(entity.blockPosition().getX(), (int) entity.getEyeY(), entity.blockPosition().getZ());
        if (entity.level().getBlockState(pos).is(ModBlocks.CUSTOM_BUBBLE_COLUMN.get())) {
            entity.setAirSupply(Math.min(entity.getAirSupply() + 5, entity.getMaxAirSupply()));
        }

    }

}