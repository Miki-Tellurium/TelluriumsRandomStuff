package com.mikitellurium.telluriumsrandomstuff.common.entity.goal;

import com.mikitellurium.telluriumsrandomstuff.common.fluid.SoulLavaFluid;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.monster.Strider;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.pathfinder.PathComputationType;

public class StriderGoToSoulLavaGoal extends MoveToBlockGoal {

    private final Strider strider;

    public StriderGoToSoulLavaGoal(Strider strider, double speedModifier) {
        super(strider, speedModifier, 8, 2);
        this.strider = strider;
    }

    public BlockPos getMoveToTarget() {
        return this.blockPos;
    }

    public boolean canContinueToUse() {
        return !SoulLavaFluid.isEntityInSoulLava(this.strider) && this.isValidTarget(this.strider.level(), this.blockPos);
    }

    public boolean canUse() {
        return !SoulLavaFluid.isEntityInSoulLava(this.strider) && super.canUse();
    }

    public boolean shouldRecalculatePath() {
        return this.tryTicks % 20 == 0;
    }

    protected boolean isValidTarget(LevelReader pLevel, BlockPos pPos) {
        return pLevel.getBlockState(pPos).is(ModBlocks.SOUL_LAVA_BLOCK.get()) &&
                pLevel.getBlockState(pPos.above()).isPathfindable(pLevel, pPos, PathComputationType.LAND);
    }

}
