package com.mikitellurium.telluriumsrandomstuff.mixin;

import com.mikitellurium.telluriumsrandomstuff.registry.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BubbleColumnBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(BubbleColumnBlock.class)
public class BubbleColumnBlockMixin {
    @Inject(method = "getColumnState", at = @At(value = "HEAD"), cancellable = true)
    private static void inject$includeCustomBlocks(BlockState pBlockState, CallbackInfoReturnable<BlockState> cir) {
        if (pBlockState.is(ModTags.Blocks.BUBBLE_COLUMN_LIFT_UP)) {
            cir.setReturnValue(Blocks.BUBBLE_COLUMN.defaultBlockState().setValue(BubbleColumnBlock.DRAG_DOWN, false));
        } else if (pBlockState.is(ModTags.Blocks.BUBBLE_COLUMN_DRAG_DOWN)) {
            cir.setReturnValue(Blocks.BUBBLE_COLUMN.defaultBlockState().setValue(BubbleColumnBlock.DRAG_DOWN, true));
        }
    }

    @Inject(method = "canSurvive", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z"),
    cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private void inject$surviveCustomBlocks(BlockState pState, LevelReader pLevel, BlockPos pPos, CallbackInfoReturnable<Boolean> cir, BlockState blockState1) {
        if (blockState1.is(ModTags.Blocks.BUBBLE_COLUMN_GENERATOR)) {
            cir.setReturnValue(true);
        }
    }
}
