package com.mikitellurium.telluriumsrandomstuff.block.custom;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class OpalButtonBlock extends ButtonBlock {

    public OpalButtonBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.STONE_BUTTON), 20, false,
                SoundEvents.STONE_BUTTON_CLICK_OFF, SoundEvents.STONE_BUTTON_CLICK_ON);
    }

}
