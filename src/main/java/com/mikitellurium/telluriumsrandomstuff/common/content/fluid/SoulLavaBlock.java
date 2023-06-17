package com.mikitellurium.telluriumsrandomstuff.common.content.fluid;

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
    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return false;
    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if (pEntity instanceof ItemEntity) {

            if (!pEntity.fireImmune()) {
                pEntity.setRemainingFireTicks(pEntity.getRemainingFireTicks() + 1);
                if (pEntity.getRemainingFireTicks() == 0) {
                    pEntity.setSecondsOnFire(8);
                }
            }

            pEntity.hurt(pLevel.damageSources().inFire(), 2.0f);
            if (pEntity.wasOnFire) {
                pEntity.playSound(SoundEvents.FIRE_EXTINGUISH, 0.5F, 2.6f);
            }
            super.entityInside(pState, pLevel, pPos, pEntity);
        }
    }

}
