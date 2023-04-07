package com.mikitellurium.telluriumsrandomstuff.block.custom;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class OpalPressurePlateBlock extends PressurePlateBlock {

    public OpalPressurePlateBlock() {
        super(Sensitivity.MOBS, Properties.copy(Blocks.STONE_PRESSURE_PLATE), SoundEvents.STONE_PRESSURE_PLATE_CLICK_OFF,
                SoundEvents.STONE_PRESSURE_PLATE_CLICK_ON);
    }

}
