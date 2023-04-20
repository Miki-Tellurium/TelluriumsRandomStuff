package com.mikitellurium.telluriumsrandomstuff.block.custom;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class OpalPressurePlateBlock extends PressurePlateBlock {

    public OpalPressurePlateBlock() {
        super(Sensitivity.MOBS, Properties.copy(Blocks.STONE_PRESSURE_PLATE), BlockSetType.STONE);
    }

}
