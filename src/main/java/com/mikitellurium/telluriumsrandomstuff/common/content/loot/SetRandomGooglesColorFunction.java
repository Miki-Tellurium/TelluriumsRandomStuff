package com.mikitellurium.telluriumsrandomstuff.common.content.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.mikitellurium.telluriumsrandomstuff.common.content.item.LavaGooglesItem;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import com.mikitellurium.telluriumsrandomstuff.registry.ModLootItemFunctions;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class SetRandomGooglesColorFunction extends LootItemConditionalFunction {

    protected SetRandomGooglesColorFunction(LootItemCondition[] lootConditions) {
        super(lootConditions);
    }

    @Override
    protected ItemStack run(ItemStack itemStack, LootContext context) {
        if (itemStack.is(ModItems.LAVA_GOOGLES.get())) {
            return LavaGooglesItem.setRandomColor(itemStack, context.getRandom());
        }
        return itemStack;
    }

    @Override
    public LootItemFunctionType getType() {
        return ModLootItemFunctions.SET_RANDOM_GOOGLES_COLOR.get();
    }

    public static class Serializer extends LootItemConditionalFunction.Serializer<SetRandomGooglesColorFunction> {

        @Override
        public SetRandomGooglesColorFunction deserialize(JsonObject jsonObject, JsonDeserializationContext deserializationContext,
                                                         LootItemCondition[] lootConditions) {
            return new SetRandomGooglesColorFunction(lootConditions);
        }

    }

}
