package com.mikitellurium.telluriumsrandomstuff.block.custom;

import com.mikitellurium.telluriumsrandomstuff.block.ModBlocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class OpalSlabBlock extends SlabBlock {

    public OpalSlabBlock() {
        super(Properties.copy(ModBlocks.OPAL_COBBLESTONE.get()));
    }

}
