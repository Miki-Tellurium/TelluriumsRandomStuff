package com.mikitellurium.telluriumsrandomstuff.registry;

import com.mikitellurium.telluriumsrandomstuff.common.loot.SetRandomGooglesColorFunction;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModLootItemFunctions {

    public static RegistryObject<LootItemFunctionType> SET_RANDOM_GOOGLES_COLOR = registerFunction(
            "set_random_googles_color", () -> new LootItemFunctionType(new SetRandomGooglesColorFunction.Serializer()));

    private static <T extends LootItemFunctionType> RegistryObject<T> registerFunction(String name, Supplier<T> function) {
        return ModRegistries.LOOT_ITEM_FUNCTIONS.register(name, function);
    }

    protected static void register(IEventBus eventBus) {
        ModRegistries.LOOT_ITEM_FUNCTIONS.register(eventBus);
    }

}
