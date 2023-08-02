package com.mikitellurium.telluriumsrandomstuff.common.mixin;

import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.util.LevelUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LiquidBlock.class)
public abstract class LiquidBlockMixin {

    private static final float soulLavaDripChance = 0.032f;

    @Inject(method = "randomTick", at = @At(value = "TAIL"))
    private void randomConvertLavaToSoulLava(BlockState state, ServerLevel level, BlockPos pos,
                                             RandomSource random, CallbackInfo ci) {
        if (level.getFluidState(pos).is(Fluids.LAVA) && (level.getBlockState(pos.below()).is(Blocks.SOUL_SAND) ||
                level.getBlockState(pos.below()).is(ModBlocks.GRATE_SOUL_SAND.get()))) {

            if (random.nextFloat() < soulLavaDripChance) {
                BlockPos maybeCauldronPos = LevelUtils.findFillableCauldronBelow(level, pos.below()); // Find empty cauldron under soul sand
                if (maybeCauldronPos != null) {
                    BlockState soulLavaCauldron = ModBlocks.SOUL_LAVA_CAULDRON_BLOCK.get().defaultBlockState();
                    level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                    level.setBlockAndUpdate(maybeCauldronPos, soulLavaCauldron);
                    level.gameEvent(GameEvent.BLOCK_CHANGE, maybeCauldronPos, GameEvent.Context.of(soulLavaCauldron));
                    level.playSound(null, maybeCauldronPos, SoundEvents.SOUL_ESCAPE, SoundSource.BLOCKS, 0.8f, 1.0f);
                }
            }
        }
    }

}
