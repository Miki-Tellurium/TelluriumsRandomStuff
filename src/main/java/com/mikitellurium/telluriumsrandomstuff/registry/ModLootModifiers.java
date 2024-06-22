package com.mikitellurium.telluriumsrandomstuff.registry;

import com.mikitellurium.telluriumsrandomstuff.common.loot.LootChestModifier;
import com.mojang.serialization.Codec;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModLootModifiers {

    public static final RegistryObject<Codec<LootChestModifier>> LOOT_CHEST_MODIFIER =
            registerModifier("loot_chest_modifier", LootChestModifier.CODEC);

    private static <M extends IGlobalLootModifier, T extends Codec<M>> RegistryObject<T> registerModifier(String name, Supplier<T> modifier) {
        return ModRegistries.LOOT_MODIFIER_SERIALIZERS.register(name, modifier);
    }

    protected static void register(IEventBus eventBus) {
        ModRegistries.LOOT_MODIFIER_SERIALIZERS.register(eventBus);
    }

}
