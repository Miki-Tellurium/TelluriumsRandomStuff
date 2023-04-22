package com.mikitellurium.telluriumsrandomstuff.jei.recipe;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SmeltingRecipe;

import java.util.List;

public class SoulFurnaceRecipeManager {

    public static List<SoulFurnaceRecipe> getConvertedVanillaRecipes(List<SmeltingRecipe> smeltingRecipes) {
        List<SoulFurnaceRecipe> soulFurnaceRecipes = NonNullList.create();
        for (SmeltingRecipe recipe : smeltingRecipes) {
            soulFurnaceRecipes.add(convert(recipe));
        }
        return soulFurnaceRecipes;
    }

    private static SoulFurnaceRecipe convert(SmeltingRecipe recipe) {
        ResourceLocation id = new ResourceLocation(TelluriumsRandomStuffMod.MOD_ID,
                "soul_furnace_" + recipe.getIngredients().get(0).hashCode());
        Ingredient ingredient = recipe.getIngredients().get(0);
        ItemStack output = recipe.getResultItem(RegistryAccess.EMPTY);
        return new SoulFurnaceRecipe(id, output, ingredient);
    }

}
