package com.mikitellurium.telluriumsrandomstuff.common.fluid;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fluids.FluidInteractionRegistry;

import java.util.function.Supplier;

public class SoulLavaBlock extends LiquidBlock {

    public SoulLavaBlock(Supplier<? extends FlowingFluid> pFluid) {
        super(pFluid, Properties.copy(Blocks.LAVA));
    }

    @Override
    public boolean isPathfindable(BlockState blockState, BlockGetter level, BlockPos pos, PathComputationType pathType) {
        return false;
    }

    @Override
    public void entityInside(BlockState blockState, Level level, BlockPos pos, Entity entity) {
            SoulLavaFluid.hurt(entity);
            super.entityInside(blockState, level, pos, entity);
    }

}
