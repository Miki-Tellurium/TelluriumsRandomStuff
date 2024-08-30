package com.mikitellurium.telluriumsrandomstuff.registry;

import com.mikitellurium.telluriumsrandomstuff.common.loot.AddStoredSoulFunction;
import com.mikitellurium.telluriumsrandomstuff.common.loot.SetRandomGooglesColorFunction;
import com.mikitellurium.telluriumsrandomstuff.common.loot.SoulHarvestEnchantFunction;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModLootItemFunctions {

    public static RegistryObject<LootItemFunctionType> SET_RANDOM_GOOGLES_COLOR = registerFunction(
            "set_random_googles_color", () -> new LootItemFunctionType(new SetRandomGooglesColorFunction.Serializer()));

    public static RegistryObject<LootItemFunctionType> SOUL_HARVEST_ENCHANT = registerFunction(
            "soul_harvest_enchant", () -> new LootItemFunctionType(new SoulHarvestEnchantFunction.Serializer()));

    public static RegistryObject<LootItemFunctionType> ADD_STORED_SOUL = registerFunction(
            "add_stored_soul", () -> new LootItemFunctionType(new AddStoredSoulFunction.Serializer()));

    private static <T extends LootItemFunctionType> RegistryObject<T> registerFunction(String name, Supplier<T> function) {
        return ModRegistries.LOOT_ITEM_FUNCTIONS.register(name, function);
    }

    protected static void register(IEventBus eventBus) {
        ModRegistries.LOOT_ITEM_FUNCTIONS.register(eventBus);
    }

}
