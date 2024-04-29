package com.mikitellurium.telluriumsrandomstuff.lib;

import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.common.loot.LootTableIdCondition;
import org.jetbrains.annotations.NotNull;

public class ModLootModifier extends LootModifier {

    private final ResourceLocation lootTable;

    protected ModLootModifier(LootItemCondition[] lootConditions, ResourceLocation lootTable) {
        super(lootConditions);
        this.lootTable = lootTable;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return null;
    }

    public ResourceLocation getLootTable() {
        return lootTable;
    }

    public static LootItemCondition[] getLootConditions(ResourceLocation lootTableLocation) {
        return new LootItemCondition[] {LootTableIdCondition.builder(lootTableLocation).build()};
    }

}
