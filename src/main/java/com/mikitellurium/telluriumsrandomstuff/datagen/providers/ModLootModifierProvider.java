package com.mikitellurium.telluriumsrandomstuff.datagen.providers;

import com.mikitellurium.telluriumsrandomstuff.common.loot.LootChestModifier;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

public class ModLootModifierProvider extends GlobalLootModifierProvider {

    public ModLootModifierProvider(PackOutput output) {
        super(output, FastLoc.modId());
    }

    @Override
    protected void start() {
        add("add_soul_jack_o_lantern_to_ancient_city",
                new LootChestModifier(BuiltInLootTables.ANCIENT_CITY, FastLoc.modLoc("chests/soul_jack_o_lantern")));
        add("add_lava_googles_to_nether_bridge",
                new LootChestModifier(BuiltInLootTables.NETHER_BRIDGE, FastLoc.modLoc("chests/lava_googles")));
        add("add_lava_googles_to_bastion_other",
                new LootChestModifier(BuiltInLootTables.BASTION_OTHER, FastLoc.modLoc("chests/lava_googles")));
    }

}


