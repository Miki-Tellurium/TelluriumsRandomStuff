package com.mikitellurium.telluriumsrandomstuff.common.fluid;

import com.mikitellurium.telluriumsrandomstuff.registry.ModFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Strider;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class SoulLavaBlock extends LiquidBlock {

    public SoulLavaBlock(Supplier<? extends FlowingFluid> fluid) {
        super(fluid, Properties.copy(Blocks.LAVA));
    }

    @Override
    public boolean isPathfindable(BlockState blockState, BlockGetter level, BlockPos pos, PathComputationType pathType) {
        return false;
    }

    @Override
    public @Nullable BlockPathTypes getBlockPathType(BlockState state, BlockGetter level, BlockPos pos, @Nullable Mob mob) {
        return BlockPathTypes.LAVA;
    }

    @Override
    public void entityInside(BlockState blockState, Level level, BlockPos pos, Entity entity) {
        if (entity instanceof Strider strider) {
            CollisionContext collisioncontext = CollisionContext.of(strider);
            if (collisioncontext.isAbove(LiquidBlock.STABLE_SHAPE, strider.blockPosition(), true) &&
                    !level.getFluidState(strider.blockPosition().above()).is(ModFluids.SOUL_LAVA_SOURCE.get())) {
                strider.setOnGround(true);
            } else {
                strider.setDeltaMovement(strider.getDeltaMovement().scale(0.5D).add(0.0D, 0.05D, 0.0D));
            }
        }
        SoulLavaFluid.hurt(entity);
        super.entityInside(blockState, level, pos, entity);
    }

}
