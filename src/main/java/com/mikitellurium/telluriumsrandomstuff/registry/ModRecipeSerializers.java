package com.mikitellurium.telluriumsrandomstuff.registry;

import com.mikitellurium.telluriumsrandomstuff.common.recipe.CompactingRecipe;
import com.mikitellurium.telluriumsrandomstuff.common.recipe.SoulFurnaceSmeltingRecipe;
import com.mikitellurium.telluriumsrandomstuff.common.recipe.SoulInfusionRecipe;
import com.mikitellurium.telluriumsrandomstuff.common.recipe.SoulLavaTransmutationRecipe;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModRecipeSerializers {

    public static final RegistryObject<RecipeSerializer<SoulFurnaceSmeltingRecipe>> SOUL_FURNACE_SMELTING_SERIALIZER =
            registerSerializer(SoulFurnaceSmeltingRecipe.Type.ID, () -> SoulFurnaceSmeltingRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<SoulLavaTransmutationRecipe>> SOUL_LAVA_TRANSMUTATION_SERIALIZER =
            registerSerializer(SoulLavaTransmutationRecipe.Type.ID, () -> SoulLavaTransmutationRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<SoulInfusionRecipe>> SOUL_INFUSION_SERIALIZER =
            registerSerializer(SoulInfusionRecipe.Type.ID, () -> SoulInfusionRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<CompactingRecipe>> COMPACTING_SERIALIZER =
            registerSerializer(CompactingRecipe.Type.ID, () -> CompactingRecipe.Serializer.INSTANCE);

    private static <R extends Recipe<?>, T extends RecipeSerializer<R>> RegistryObject<T> registerSerializer(String name, Supplier<T> serializer) {
        return ModRegistries.RECIPE_SERIALIZERS.register(name, serializer);
    }

    protected static void register(IEventBus eventBus) {
        ModRegistries.RECIPE_SERIALIZERS.register(eventBus);
    }

}


