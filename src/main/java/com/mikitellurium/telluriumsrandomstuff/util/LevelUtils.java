package com.mikitellurium.telluriumsrandomstuff.util;

import com.mikitellurium.telluriumsrandomstuff.block.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.tag.ModTags;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.EntityTypePredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class LevelUtils {

    private static final VoxelShape REQUIRED_SPACE_TO_DRIP_THROUGH_NON_SOLID_BLOCK =
            Block.box(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);

    @Nullable
    public static BlockPos findFillableCauldronBelow(Level pLevel, BlockPos pPos) {
        Predicate<BlockState> predicate = (blockState) -> blockState.getBlock() instanceof CauldronBlock;
        BiPredicate<BlockPos, BlockState> bipredicate = (blockPos, blockState) -> canDripThrough(pLevel, blockPos, blockState);
        return findBlockVertical(pLevel, pPos, Direction.DOWN.getAxisDirection(), bipredicate, predicate, 11).orElse(null);
    }

    private static boolean canDripThrough(BlockGetter pLevel, BlockPos pPos, BlockState pState) {
        if (pState.isAir()) {
            return true;
        } else if (pState.isSolidRender(pLevel, pPos)) {
            return false;
        } else if (!pState.getFluidState().isEmpty()) {
            return false;
        } else {
            VoxelShape voxelshape = pState.getCollisionShape(pLevel, pPos);
            return !Shapes.joinIsNotEmpty(REQUIRED_SPACE_TO_DRIP_THROUGH_NON_SOLID_BLOCK, voxelshape, BooleanOp.AND);
        }
    }

    @SuppressWarnings("SameParameterValue")
    private static Optional<BlockPos> findBlockVertical(LevelAccessor pLevel, BlockPos pPos, Direction.AxisDirection pAxis,
                                                        BiPredicate<BlockPos, BlockState> pPositionalStatePredicate,
                                                        Predicate<BlockState> pStatePredicate, int pMaxIterations) {
        Direction direction = Direction.get(pAxis, Direction.Axis.Y);
        BlockPos.MutableBlockPos blockpos$mutableblockpos = pPos.mutable();

        for(int i = 1; i < pMaxIterations; ++i) {
            blockpos$mutableblockpos.move(direction);
            BlockState blockstate = pLevel.getBlockState(blockpos$mutableblockpos);
            if (pStatePredicate.test(blockstate)) {
                return Optional.of(blockpos$mutableblockpos.immutable());
            }

            if (pLevel.isOutsideBuildHeight(blockpos$mutableblockpos.getY()) ||
                    !pPositionalStatePredicate.test(blockpos$mutableblockpos, blockstate)) {
                return Optional.empty();
            }
        }

        return Optional.empty();
    }

}
