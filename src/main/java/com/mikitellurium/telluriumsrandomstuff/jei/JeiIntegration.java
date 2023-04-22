package com.mikitellurium.telluriumsrandomstuff.jei;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.block.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.gui.SoulFurnaceGui;
import com.mikitellurium.telluriumsrandomstuff.jei.recipe.SoulFurnaceRecipe;
import com.mikitellurium.telluriumsrandomstuff.jei.recipe.SoulFurnaceRecipeManager;
import com.mikitellurium.telluriumsrandomstuff.jei.recipe.SoulFurnaceSmeltingCategory;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class JeiIntegration implements IModPlugin {

    public static RecipeType<SoulFurnaceRecipe> SOUL_FURNACE_RECIPE_TYPE =
            new RecipeType<>(SoulFurnaceSmeltingCategory.UID, SoulFurnaceRecipe.class);

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return new ResourceLocation(TelluriumsRandomStuffMod.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(new SoulFurnaceSmeltingCategory(guiHelper));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();

        List<SmeltingRecipe> vanillaRecipes = recipeManager.getAllRecipesFor(net.minecraft.world.item.crafting.RecipeType.SMELTING);
        List<SoulFurnaceRecipe> convertedRecipes = SoulFurnaceRecipeManager.getConvertedVanillaRecipes(vanillaRecipes);
        List<SoulFurnaceRecipe> soulFurnaceRecipes = recipeManager.getAllRecipesFor(SoulFurnaceRecipe.Type.INSTANCE);

        registration.addRecipes(SOUL_FURNACE_RECIPE_TYPE, convertedRecipes);
        registration.addRecipes(SOUL_FURNACE_RECIPE_TYPE, soulFurnaceRecipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.SOUL_FURNACE.get()), SOUL_FURNACE_RECIPE_TYPE);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(SoulFurnaceGui.class, 77, 28, 28, 21, SOUL_FURNACE_RECIPE_TYPE);
    }

}
