package com.mikitellurium.telluriumsrandomstuff.common.content.block;

import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TorchflowerCropBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class BrightTorchflowerCropBlock extends TorchflowerCropBlock {

    public BrightTorchflowerCropBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.TORCHFLOWER_CROP)
                .lightLevel((blockState) -> {
                    switch (blockState.getValue(TorchflowerCropBlock.AGE)) {
                        case 1 -> {return 7;}
                        case 2 -> {return 15;}
                        default -> {return  0;}
                    }}));
    }

    @Override
    public BlockState getStateForAge(int age) {
        return age == 2 ? ModBlocks.BRIGHT_TORCHFLOWER.get().defaultBlockState() : super.getStateForAge(age);
    }

}
