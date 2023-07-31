package com.mikitellurium.telluriumsrandomstuff.common.datagen.providers;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.common.content.loot.ItemLootModifier;
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
        add("lava_googles_in_nether_fortress",
                new ItemLootModifier(
                        new LootItemCondition[] {
                                new LootTableIdCondition.Builder(
                                        new ResourceLocation(BuiltInLootTables.NETHER_BRIDGE.getPath())).build()
                }, ModItems.LAVA_GOOGLES.get()));
    }

}


