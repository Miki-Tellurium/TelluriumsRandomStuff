package com.mikitellurium.telluriumsrandomstuff.integration.rei;

import com.mikitellurium.telluriumsrandomstuff.integration.rei.util.BlockStateEntryType;
import me.shedaniel.rei.api.common.entry.type.EntryTypeRegistry;
import me.shedaniel.rei.api.common.plugins.REIServerPlugin;
import me.shedaniel.rei.forge.REIPluginDedicatedServer;

@REIPluginDedicatedServer
public class ReiServerIntegration implements REIServerPlugin {

    @Override
    public void registerEntryTypes(EntryTypeRegistry registry) {
        registry.register(BlockStateEntryType.BLOCK_STATE, new BlockStateEntryType());
    }

}
