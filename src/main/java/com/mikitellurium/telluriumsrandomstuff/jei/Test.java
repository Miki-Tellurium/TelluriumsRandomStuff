package com.mikitellurium.telluriumsrandomstuff.jei;

import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;

import java.util.List;

public class Test {

    public static void testRecipes(RecipeManager recipeManager) {
        List<SmeltingRecipe> recipes = recipeManager.getAllRecipesFor(RecipeType.SMELTING);
        for (SmeltingRecipe recipe : recipes) {
            System.out.println("-----");
            System.out.println("Ingredient: " + recipe.getIngredients().get(0));
            System.out.println("Result: " + recipe.getResultItem(RegistryAccess.EMPTY).getItem());
        }
    }

}
