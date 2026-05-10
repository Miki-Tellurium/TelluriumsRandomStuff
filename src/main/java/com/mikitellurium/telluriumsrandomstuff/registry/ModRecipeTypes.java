package com.mikitellurium.telluriumsrandomstuff.registry;

import com.mikitellurium.telluriumsrandomstuff.common.recipe.CompactingRecipe;
import com.mikitellurium.telluriumsrandomstuff.common.recipe.SoulInfusionRecipe;
import com.mikitellurium.telluriumsrandomstuff.common.recipe.SoulLavaTransmutationRecipe;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

public class ModRecipeTypes {
    public static final RecipeType<SoulLavaTransmutationRecipe> SOUL_LAVA_TRANSMUTATION = create("soul_lava_transmutation");
    public static final RecipeType<SoulInfusionRecipe> SOUL_INFUSION = create("soul_infusion");
    public static final RecipeType<CompactingRecipe> COMPACTING = create("compacting");

    private static <T extends Recipe<?>> RecipeType<T> create(String id) {
        return RecipeType.simple(FastLoc.modLoc(id));
    }
}
