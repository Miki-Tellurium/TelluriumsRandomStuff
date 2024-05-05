package com.mikitellurium.telluriumsrandomstuff.integration.rei;

import com.mikitellurium.telluriumsrandomstuff.integration.rei.display.CompactingDisplay;
import com.mikitellurium.telluriumsrandomstuff.integration.rei.display.PotionMixingDisplay;
import com.mikitellurium.telluriumsrandomstuff.integration.rei.display.SoulFurnaceSmeltingDisplay;
import com.mikitellurium.telluriumsrandomstuff.integration.rei.display.SoulInfusionDisplay;
import com.mikitellurium.telluriumsrandomstuff.integration.rei.util.BlockStateEntryType;
import com.mikitellurium.telluriumsrandomstuff.integration.rei.util.ModDisplayCategories;
import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry;
import me.shedaniel.rei.api.common.entry.type.EntryTypeRegistry;
import me.shedaniel.rei.api.common.plugins.REIServerPlugin;
import me.shedaniel.rei.forge.REIPluginDedicatedServer;

@REIPluginDedicatedServer
public class ReiServerIntegration implements REIServerPlugin, ModDisplayCategories {

    @Override
    public void registerDisplaySerializer(DisplaySerializerRegistry registry) {
        registry.register(SOUL_FURNACE_SMELTING, SoulFurnaceSmeltingDisplay.serializer());
        registry.register(SOUL_INFUSION, SoulInfusionDisplay.serializer());
        registry.register(COMPACTING, CompactingDisplay.serializer());
        registry.register(POTION_MIXING, PotionMixingDisplay.serializer());
    }

    @Override
    public void registerEntryTypes(EntryTypeRegistry registry) {
        registry.register(BlockStateEntryType.BLOCK_STATE, new BlockStateEntryType());
    }

}
