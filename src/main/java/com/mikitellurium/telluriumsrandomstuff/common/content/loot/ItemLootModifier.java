package com.mikitellurium.telluriumsrandomstuff.common.content.loot;

import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.common.loot.LootTableIdCondition;
import org.jetbrains.annotations.NotNull;

public class ItemLootModifier extends LootModifier {

    private final ItemLike itemLike;
    private final int chance;

    protected ItemLootModifier(LootItemCondition[] conditionsIn, ItemLike itemLike, int chance) {
        super(conditionsIn);
        this.itemLike = itemLike;
        this.chance = chance;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return null;
    }

    public ItemLike getItem() {
        return itemLike;
    }

    public int getChance() {
        return chance;
    }

    public static LootItemCondition[] getLootConditions(ResourceLocation... lootTableLocation) {
        int size = lootTableLocation.length;
        LootItemCondition[] conditions = new LootItemCondition[size];
        for (int i = 0; i < size; i++) {
            conditions[i] = LootTableIdCondition.builder(lootTableLocation[i]).build();
        }
        return conditions;
    }

}
