package com.mikitellurium.telluriumsrandomstuff.registry;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.common.recipe.SoulFurnaceSmeltingRecipe;
import com.mikitellurium.telluriumsrandomstuff.common.recipe.SoulLavaTransmutationRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipeSerializers {

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, TelluriumsRandomStuffMod.MOD_ID);

    public static final RegistryObject<RecipeSerializer<SoulFurnaceSmeltingRecipe>> SOUL_FURNACE_SMELTING_SERIALIZER =
            SERIALIZERS.register("soul_furnace_smelting", () -> SoulFurnaceSmeltingRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<SoulLavaTransmutationRecipe>> SOUL_LAVA_TRANSMUTATION_SERIALIZER =
            SERIALIZERS.register("soul_lava_transmutation", () -> SoulLavaTransmutationRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }

}


