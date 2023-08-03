package com.mikitellurium.telluriumsrandomstuff.datagen.providers;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.common.content.loot.AddLavaGoogles;
import com.mikitellurium.telluriumsrandomstuff.common.content.loot.AddSoulJackOLantern;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

public class ModLootModifierProvider extends GlobalLootModifierProvider {

    public ModLootModifierProvider(PackOutput output) {
        super(output, TelluriumsRandomStuffMod.MOD_ID);
    }

    @Override
    protected void start() {
        final ResourceLocation nether_bridge = new ResourceLocation(BuiltInLootTables.NETHER_BRIDGE.getPath());
        final ResourceLocation bastion_other = new ResourceLocation(BuiltInLootTables.BASTION_OTHER.getPath());
        final ResourceLocation ancient_city = new ResourceLocation(BuiltInLootTables.ANCIENT_CITY.getPath());
        add("add_lava_googles_to_nether_bridge", new AddLavaGoogles(nether_bridge));
        add("add_lava_googles_to_bastion_other", new AddLavaGoogles(bastion_other));
        add("add_soul_jack_o_lantern_to_ancient_city", new AddSoulJackOLantern(ancient_city));
    }

}


