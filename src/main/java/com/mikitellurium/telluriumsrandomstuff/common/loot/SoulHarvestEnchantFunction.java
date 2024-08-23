package com.mikitellurium.telluriumsrandomstuff.common.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.mikitellurium.telluriumsrandomstuff.registry.ModEnchantments;
import com.mikitellurium.telluriumsrandomstuff.registry.ModLootItemFunctions;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

public class SoulHarvestEnchantFunction extends LootItemConditionalFunction {

    private final float chance;
    private final float levelMultiplier;
    private final NumberProvider lootingMultiplier;

    protected SoulHarvestEnchantFunction(LootItemCondition[] conditions, float chance, float levelMultiplier, NumberProvider lootingMultiplier) {
        super(conditions);
        this.chance = chance;
        this.levelMultiplier = levelMultiplier;
        this.lootingMultiplier = lootingMultiplier;
    }

    @Override
    protected ItemStack run(ItemStack itemStack, LootContext context) {
        Entity entity = context.getParamOrNull(LootContextParams.KILLER_ENTITY);
        if (entity instanceof LivingEntity) {
            int soulHarvest = this.getSoulHarvestLevel((LivingEntity) entity);
            if (soulHarvest > 0) {
                float f = chance + levelMultiplier * (soulHarvest - 1);
                if (context.getRandom().nextFloat() < f) {
                    int looting = context.getLootingModifier();
                    if (looting == 0) {
                        return itemStack;
                    }

                    float g = (float)looting * this.lootingMultiplier.getFloat(context);
                    itemStack.grow(Math.round(g));
                    return itemStack;
                }
            }
        }
        return ItemStack.EMPTY;
    }

    private int getSoulHarvestLevel(LivingEntity livingEntity) {
        return EnchantmentHelper.getEnchantmentLevel(ModEnchantments.SOUL_HARVESTING.get(), livingEntity);
    }

    @Override
    public LootItemFunctionType getType() {
        return ModLootItemFunctions.SOUL_HARVEST_ENCHANT.get();
    }

    public static class Serializer extends LootItemConditionalFunction.Serializer<SoulHarvestEnchantFunction> {

        @Override
        public SoulHarvestEnchantFunction deserialize(JsonObject json, JsonDeserializationContext context, LootItemCondition[] lootConditions) {
            float chance = GsonHelper.getAsFloat(json, "chance");
            float levelMultiplier = GsonHelper.getAsFloat(json, "multiplier");
            NumberProvider lootingMultiplier = GsonHelper.getAsObject(json, "looting", context, NumberProvider.class);
            return new SoulHarvestEnchantFunction(lootConditions, chance, levelMultiplier, lootingMultiplier);
        }

    }

}
