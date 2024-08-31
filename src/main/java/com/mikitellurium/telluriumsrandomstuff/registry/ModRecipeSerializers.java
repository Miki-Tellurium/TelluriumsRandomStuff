package com.mikitellurium.telluriumsrandomstuff.registry;

import com.mikitellurium.telluriumsrandomstuff.common.recipe.*;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModRecipeSerializers {

    public static final RegistryObject<RecipeSerializer<SoulFurnaceSmeltingRecipe>> SOUL_FURNACE_SMELTING =
            registerSerializer(SoulFurnaceSmeltingRecipe.Type.ID, () -> SoulFurnaceSmeltingRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<SoulLavaTransmutationRecipe>> SOUL_LAVA_TRANSMUTATION =
            registerSerializer(SoulLavaTransmutationRecipe.Type.ID, () -> SoulLavaTransmutationRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<SoulInfusionRecipe>> SOUL_INFUSION =
            registerSerializer(SoulInfusionRecipe.Type.ID, () -> SoulInfusionRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<CompactingRecipe>> COMPACTING =
            registerSerializer(CompactingRecipe.Type.ID, () -> CompactingRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<SoulStorageDisassembleRecipe>> SOUL_STORAGE_DISASSEMBLE =
            registerSerializer(SoulStorageDisassembleRecipe.ID, SoulStorageDisassembleRecipe.Serializer::new);

    private static <R extends Recipe<?>, T extends RecipeSerializer<R>> RegistryObject<T> registerSerializer(String name, Supplier<T> serializer) {
        return ModRegistries.RECIPE_SERIALIZERS.register(name, serializer);
    }

    protected static void register(IEventBus eventBus) {
        ModRegistries.RECIPE_SERIALIZERS.register(eventBus);
    }

}


