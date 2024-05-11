package com.mikitellurium.telluriumsrandomstuff.integration.rei;

import com.mikitellurium.telluriumsrandomstuff.integration.rei.display.*;
import com.mikitellurium.telluriumsrandomstuff.integration.rei.util.BlockStateEntryType;
import com.mikitellurium.telluriumsrandomstuff.integration.rei.util.FluidBlockEntryType;
import com.mikitellurium.telluriumsrandomstuff.integration.rei.util.ModDisplayCategories;
import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry;
import me.shedaniel.rei.api.common.entry.type.EntryTypeRegistry;
import me.shedaniel.rei.api.common.plugins.REIServerPlugin;
import me.shedaniel.rei.forge.REIPluginCommon;

@REIPluginCommon
public class ReiServerIntegration implements REIServerPlugin, ModDisplayCategories {

    @Override
    public void registerDisplaySerializer(DisplaySerializerRegistry registry) {
        registry.register(SOUL_FURNACE_SMELTING, SoulFurnaceSmeltingDisplay.serializer());
        registry.register(SOUL_INFUSION, SoulInfusionDisplay.serializer());
        registry.register(COMPACTING, CompactingDisplay.serializer());
        registry.register(POTION_MIXING, PotionMixingDisplay.serializer());
        registry.register(AMETHYST_LENS_INFO, AmethystLensInfoDisplay.serializer());
        registry.register(SOUL_LAVA_TRANSMUTATION, SoulLavaTransmutationDisplay.serializer());
        registry.register(SOUL_LAVA_INFO, SoulLavaInfoDisplay.serializer());
    }

    @Override
    public void registerEntryTypes(EntryTypeRegistry registry) {
        registry.register(BlockStateEntryType.TYPE, new BlockStateEntryType());
        registry.register(FluidBlockEntryType.TYPE, new FluidBlockEntryType());
    }

}
