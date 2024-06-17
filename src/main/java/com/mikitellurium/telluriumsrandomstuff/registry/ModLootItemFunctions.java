package com.mikitellurium.telluriumsrandomstuff.registry;

import com.mikitellurium.telluriumsrandomstuff.common.loot.SetRandomGooglesColorFunction;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModLootItemFunctions {

    public static DeferredRegister<LootItemFunctionType> LOOT_ITEM_FUNCTIONS =
            DeferredRegister.create(Registries.LOOT_FUNCTION_TYPE, FastLoc.modId());

    public static RegistryObject<LootItemFunctionType> SET_RANDOM_GOOGLES_COLOR = LOOT_ITEM_FUNCTIONS.register(
            "set_random_googles_color", () -> new LootItemFunctionType(new SetRandomGooglesColorFunction.Serializer()));

    public static void register(IEventBus eventBus) {
        LOOT_ITEM_FUNCTIONS.register(eventBus);
    }

}
