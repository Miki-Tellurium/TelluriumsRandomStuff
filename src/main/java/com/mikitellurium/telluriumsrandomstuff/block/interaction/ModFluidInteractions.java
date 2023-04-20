package com.mikitellurium.telluriumsrandomstuff.block.interaction;

import com.mikitellurium.telluriumsrandomstuff.fluid.ModFluidTypes;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidInteractionRegistry;
import net.minecraftforge.fluids.FluidInteractionRegistry.InteractionInformation;

public class ModFluidInteractions {

    private static final InteractionInformation SOUL_LAVA_WITH_WATER = new InteractionInformation(ForgeMod.WATER_TYPE.get(),
            fluidState -> fluidState.isSource() ? Blocks.CRYING_OBSIDIAN.defaultBlockState() : Blocks.SOUL_SOIL.defaultBlockState()
    );
    private static final InteractionInformation SOUL_LAVA_WITH_LAVA = new InteractionInformation(ForgeMod.LAVA_TYPE.get(),
            fluidState -> Blocks.BLACKSTONE.defaultBlockState()
    );
    private static final InteractionInformation LAVA_WITH_SOUL_LAVA = new InteractionInformation(ModFluidTypes.SOUL_LAVA_FLUID_TYPE.get(),
            fluidState -> Blocks.BLACKSTONE.defaultBlockState()
    );

    public static void register() {
        FluidInteractionRegistry.addInteraction(ModFluidTypes.SOUL_LAVA_FLUID_TYPE.get(), SOUL_LAVA_WITH_WATER);
        FluidInteractionRegistry.addInteraction(ModFluidTypes.SOUL_LAVA_FLUID_TYPE.get(), SOUL_LAVA_WITH_LAVA);
        FluidInteractionRegistry.addInteraction(ForgeMod.LAVA_TYPE.get(), LAVA_WITH_SOUL_LAVA);
    }

}
