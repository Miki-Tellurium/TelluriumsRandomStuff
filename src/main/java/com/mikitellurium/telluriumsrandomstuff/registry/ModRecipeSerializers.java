package com.mikitellurium.telluriumsrandomstuff.registry;

import com.mikitellurium.telluriumsrandomstuff.common.recipe.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModRecipeSerializers {
    public static final RegistryObject<RecipeSerializer<SoulLavaTransmutationRecipe>> SOUL_LAVA_TRANSMUTATION =
            registerSerializer(ModRecipeTypes.SOUL_LAVA_TRANSMUTATION, () -> SoulLavaTransmutationRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<SoulInfusionRecipe>> SOUL_INFUSION =
            registerSerializer(ModRecipeTypes.SOUL_INFUSION, () -> SoulInfusionRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<CompactingRecipe>> COMPACTING =
            registerSerializer(ModRecipeTypes.COMPACTING, () -> CompactingRecipe.Serializer.INSTANCE);

    private static <R extends Recipe<?>, T extends RecipeSerializer<R>> RegistryObject<T> registerSerializer(RecipeType<R> type, Supplier<T> serializer) {
        return ModRegistries.RECIPE_SERIALIZERS.register(ResourceLocation.of(type.toString(), ':').getPath(), serializer);
    }

    protected static void register(IEventBus eventBus) {
        ModRegistries.RECIPE_SERIALIZERS.register(eventBus);
    }
}


