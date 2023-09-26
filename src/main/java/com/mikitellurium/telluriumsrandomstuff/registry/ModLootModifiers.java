package com.mikitellurium.telluriumsrandomstuff.registry;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.common.content.loot.EntityLootModifier;
import com.mikitellurium.telluriumsrandomstuff.common.content.loot.LootChestModifier;
import com.mojang.serialization.Codec;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModLootModifiers {

    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIER_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, TelluriumsRandomStuffMod.MOD_ID);

    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> LOOT_CHEST_MODIFIER =
            LOOT_MODIFIER_SERIALIZERS.register("add_soul_jack_o_lantern", LootChestModifier.CODEC);

    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> ENTITY_LOOT_MODIFIER =
            LOOT_MODIFIER_SERIALIZERS.register("add_small_soul_fragment", EntityLootModifier.CODEC);

    public static void register(IEventBus eventBus) {
        LOOT_MODIFIER_SERIALIZERS.register(eventBus);
    }

}
