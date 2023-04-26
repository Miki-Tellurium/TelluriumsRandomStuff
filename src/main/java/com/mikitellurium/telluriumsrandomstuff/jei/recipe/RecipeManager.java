package com.mikitellurium.telluriumsrandomstuff.jei.recipe;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class RecipeManager {

    public static List<SoulFurnaceRecipe> getConvertedVanillaRecipes(List<SmeltingRecipe> smeltingRecipes) {
        List<SoulFurnaceRecipe> soulFurnaceRecipes = NonNullList.create();
        for (SmeltingRecipe recipe : smeltingRecipes) {
            soulFurnaceRecipes.add(convert(recipe));
        }
        return soulFurnaceRecipes;
    }

    private static SoulFurnaceRecipe convert(SmeltingRecipe recipe) {
        String itemId = ForgeRegistries.ITEMS.getDelegateOrThrow(recipe.getResultItem(RegistryAccess.EMPTY).getItem())
                .key().location().getPath();
        ResourceLocation id = new ResourceLocation(TelluriumsRandomStuffMod.MOD_ID, "soul_furnace_" + itemId);

        Ingredient ingredient = recipe.getIngredients().get(0);
        ItemStack output = recipe.getResultItem(RegistryAccess.EMPTY);
        return new SoulFurnaceRecipe(id, output, ingredient, 0);
    }

}
