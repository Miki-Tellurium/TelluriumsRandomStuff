package com.mikitellurium.telluriumsrandomstuff.block.custom;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class OpalButtonBlock extends ButtonBlock {

    public OpalButtonBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.STONE_BUTTON), BlockSetType.STONE, 20, false);
    }

}
