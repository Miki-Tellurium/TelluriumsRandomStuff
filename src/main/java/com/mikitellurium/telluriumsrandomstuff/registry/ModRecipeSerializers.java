package com.mikitellurium.telluriumsrandomstuff.registry;

import com.mikitellurium.telluriumsrandomstuff.common.recipe.CompactingRecipe;
import com.mikitellurium.telluriumsrandomstuff.common.recipe.SoulFurnaceSmeltingRecipe;
import com.mikitellurium.telluriumsrandomstuff.common.recipe.SoulInfusionRecipe;
import com.mikitellurium.telluriumsrandomstuff.common.recipe.SoulLavaTransmutationRecipe;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipeSerializers {

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, FastLoc.modId());

    public static final RegistryObject<RecipeSerializer<SoulFurnaceSmeltingRecipe>> SOUL_FURNACE_SMELTING_SERIALIZER =
            SERIALIZERS.register(SoulFurnaceSmeltingRecipe.Type.ID, () -> SoulFurnaceSmeltingRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<SoulLavaTransmutationRecipe>> SOUL_LAVA_TRANSMUTATION_SERIALIZER =
            SERIALIZERS.register(SoulLavaTransmutationRecipe.Type.ID, () -> SoulLavaTransmutationRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<SoulInfusionRecipe>> SOUL_INFUSION_SERIALIZER =
            SERIALIZERS.register(SoulInfusionRecipe.Type.ID, () -> SoulInfusionRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<CompactingRecipe>> COMPACTING_SERIALIZER =
            SERIALIZERS.register(CompactingRecipe.Type.ID, () -> CompactingRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }

}


