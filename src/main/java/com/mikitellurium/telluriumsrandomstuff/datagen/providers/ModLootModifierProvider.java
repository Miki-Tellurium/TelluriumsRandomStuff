package com.mikitellurium.telluriumsrandomstuff.datagen.providers;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.common.content.loot.LavaGooglesLootModifier;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

public class ModLootModifierProvider extends GlobalLootModifierProvider {

    public ModLootModifierProvider(PackOutput output) {
        super(output, TelluriumsRandomStuffMod.MOD_ID);
    }

    @Override
    protected void start() {
        LootItemCondition[] lavaGooglesConditions = new LootItemCondition[] {
                new LootTableIdCondition.Builder(
                        new ResourceLocation(BuiltInLootTables.NETHER_BRIDGE.getPath())).build()
        };
        add("lava_googles_in_nether_fortress",
                new LavaGooglesLootModifier(lavaGooglesConditions, ModItems.LAVA_GOOGLES.get()));
    }

}


