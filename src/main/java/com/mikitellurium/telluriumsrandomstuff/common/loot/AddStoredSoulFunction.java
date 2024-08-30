package com.mikitellurium.telluriumsrandomstuff.common.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.mikitellurium.telluriumsrandomstuff.common.capability.SoulStorage;
import com.mikitellurium.telluriumsrandomstuff.common.item.SoulStorageItem;
import com.mikitellurium.telluriumsrandomstuff.registry.ModLootItemFunctions;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

public class AddStoredSoulFunction extends LootItemConditionalFunction {

    private final NumberProvider count;

    protected AddStoredSoulFunction(LootItemCondition[] lootItemConditions, NumberProvider count) {
        super(lootItemConditions);
        this.count = count;
    }

    @Override
    protected ItemStack run(ItemStack itemStack, LootContext context) {
        if (SoulStorageItem.isSoulStorageItem(itemStack)) {
            Entity entity = context.getParam(LootContextParams.THIS_ENTITY);
            String entityId = EntityType.getKey(entity.getType()).toString();
            SoulStorage.performAction(itemStack, (storage) -> storage.set(entityId, this.count.getInt(context)));
        }
        return itemStack;
    }

    @Override
    public LootItemFunctionType getType() {
        return ModLootItemFunctions.ADD_STORED_SOUL.get();
    }

    public static class Serializer extends LootItemConditionalFunction.Serializer<AddStoredSoulFunction> {

        @Override
        public AddStoredSoulFunction deserialize(JsonObject json, JsonDeserializationContext context, LootItemCondition[] lootConditions) {
            NumberProvider count = GsonHelper.getAsObject(json, "count", context, NumberProvider.class);
            return new AddStoredSoulFunction(lootConditions, count);
        }

    }

}
