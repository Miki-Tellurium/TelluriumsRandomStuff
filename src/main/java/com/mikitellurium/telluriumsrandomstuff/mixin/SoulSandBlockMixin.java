package com.mikitellurium.telluriumsrandomstuff.mixin;

import com.mikitellurium.telluriumsrandomstuff.registry.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoulSandBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SoulSandBlock.class)
public class SoulSandBlockMixin extends Block {

    public SoulSandBlockMixin(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void animateTick(@NotNull BlockState state, Level level, BlockPos pos, @NotNull RandomSource random) {
        if (level.getFluidState(pos.above()).is(Fluids.LAVA) && level.isEmptyBlock(pos.below())) {
            if (random.nextInt(12) == 0) {
                double d0 = pos.getX() + random.nextDouble();
                double d1 = pos.getY() - 0.05d;
                double d2 = pos.getZ() + random.nextDouble();
                level.addParticle(ModParticles.SOUL_LAVA_HANG.get(), d0, d1, d2, 0.0D, 0.0D, 0.0D);
            }
        }
    }

}
