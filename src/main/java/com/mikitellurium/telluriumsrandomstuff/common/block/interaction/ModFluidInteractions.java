package com.mikitellurium.telluriumsrandomstuff.common.block.interaction;

import com.mikitellurium.telluriumsrandomstuff.registry.ModFluidTypes;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidInteractionRegistry;
import net.minecraftforge.fluids.FluidInteractionRegistry.InteractionInformation;

public class ModFluidInteractions {

    private static final InteractionInformation SOUL_LAVA_WITH_WATER = new InteractionInformation(ForgeMod.WATER_TYPE.get(),
            fluidState -> fluidState.isSource() ? Blocks.CRYING_OBSIDIAN.defaultBlockState() : Blocks.SOUL_SOIL.defaultBlockState()
    );

    public static void register() {
        FluidInteractionRegistry.addInteraction(ModFluidTypes.SOUL_LAVA_FLUID_TYPE.get(), SOUL_LAVA_WITH_WATER);
    }

}
