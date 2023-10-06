package com.mikitellurium.telluriumsrandomstuff.registry;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.common.loot.SetRandomGooglesColorFunction;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.functions.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModLootItemFunctions {

    public static DeferredRegister<LootItemFunctionType> LOOT_ITEM_FUNCTION_TYPES =
            DeferredRegister.create(Registries.LOOT_FUNCTION_TYPE, TelluriumsRandomStuffMod.MOD_ID);

    public static RegistryObject<LootItemFunctionType> SET_RANDOM_GOOGLES_COLOR = LOOT_ITEM_FUNCTION_TYPES.register(
            "set_random_googles_color", () -> new LootItemFunctionType(new SetRandomGooglesColorFunction.Serializer()));

    public static void register(IEventBus eventBus) {
        LOOT_ITEM_FUNCTION_TYPES.register(eventBus);
    }

}
