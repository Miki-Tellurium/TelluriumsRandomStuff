package com.mikitellurium.telluriumsrandomstuff.mixin;

import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.registry.ModFluidTypes;
import com.mikitellurium.telluriumsrandomstuff.registry.ModFluids;
import com.mikitellurium.telluriumsrandomstuff.util.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @ModifyVariable(method = "travel", at = @At("STORE"), ordinal = 0)
    private FluidState getCorrectSoulLavaState(FluidState fluidState) {
        LivingEntity entity = (LivingEntity) (Object) this;
        BlockState blockstate = entity.level().getBlockStatesIfLoaded(entity.getBoundingBox())
                .filter((block) -> block.is(ModBlocks.SOUL_LAVA_BLOCK.get()))
                .findFirst().orElse(null);
        return blockstate != null ? ModFluids.SOUL_LAVA_SOURCE.get().defaultFluidState() : fluidState;
    }

}
