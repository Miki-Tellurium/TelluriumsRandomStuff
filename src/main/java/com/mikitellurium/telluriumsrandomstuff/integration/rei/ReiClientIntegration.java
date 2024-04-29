package com.mikitellurium.telluriumsrandomstuff.integration.rei;

import com.mikitellurium.telluriumsrandomstuff.integration.rei.util.BlockStateEntryType;
import com.mikitellurium.telluriumsrandomstuff.integration.rei.util.BlockStateRenderer;
import me.shedaniel.rei.api.client.entry.renderer.EntryRendererRegistry;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.forge.REIPluginClient;

@REIPluginClient
public class ReiClientIntegration implements REIClientPlugin {

    @Override
    public void registerEntryRenderers(EntryRendererRegistry registry) {
        registry.register(BlockStateEntryType.BLOCK_STATE, new BlockStateRenderer());
    }

}
