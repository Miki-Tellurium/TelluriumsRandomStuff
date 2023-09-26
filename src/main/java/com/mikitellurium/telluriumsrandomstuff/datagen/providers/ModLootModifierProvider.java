package com.mikitellurium.telluriumsrandomstuff.datagen.providers;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.common.content.loot.EntityLootModifier;
import com.mikitellurium.telluriumsrandomstuff.common.content.loot.LootChestModifier;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.registries.ForgeRegistries;

public class ModLootModifierProvider extends GlobalLootModifierProvider {

    public ModLootModifierProvider(PackOutput output) {
        super(output, TelluriumsRandomStuffMod.MOD_ID);
    }

    @Override
    protected void start() {
        final ResourceLocation nether_bridge = new ResourceLocation(BuiltInLootTables.NETHER_BRIDGE.getPath());
        final ResourceLocation bastion_other = new ResourceLocation(BuiltInLootTables.BASTION_OTHER.getPath());
        final ResourceLocation ancient_city = new ResourceLocation(BuiltInLootTables.ANCIENT_CITY.getPath());
        add("add_soul_jack_o_lantern_to_ancient_city",
                new LootChestModifier(ancient_city, new ResourceLocation(TelluriumsRandomStuffMod.MOD_ID, "chests/soul_jack_o_lantern")));
        add("add_lava_googles_to_nether_bridge",
                new LootChestModifier(nether_bridge, new ResourceLocation(TelluriumsRandomStuffMod.MOD_ID, "chests/lava_googles")));
        add("add_lava_googles_to_bastion_other",
                new LootChestModifier(bastion_other, new ResourceLocation(TelluriumsRandomStuffMod.MOD_ID, "chests/lava_googles")));
        ForgeRegistries.ENTITY_TYPES.getEntries().forEach((entityType) -> {
            ResourceLocation entityLocation = entityType.getKey().location();
            ResourceLocation lootTable =
                    new ResourceLocation(entityLocation.getNamespace(), "entities/" + entityLocation.getPath());
            if (entityType.getValue().getCategory() != MobCategory.MISC) {
                if (entityType.getValue().getCategory() == MobCategory.MONSTER) {
                    add("add_small_soul_fragment_to_" + entityLocation.getPath(), new EntityLootModifier(lootTable,
                            new ResourceLocation(TelluriumsRandomStuffMod.MOD_ID, "entities/small_soul_fragment_monsters")));
                } else {
                    add("add_small_soul_fragment_to_" + entityLocation.getPath(), new EntityLootModifier(lootTable,
                            new ResourceLocation(TelluriumsRandomStuffMod.MOD_ID, "entities/small_soul_fragment_other")));
                }
                TelluriumsRandomStuffMod.LOGGER.info("Added loot table for " + entityLocation.getPath());
            }
        });
    }

}


