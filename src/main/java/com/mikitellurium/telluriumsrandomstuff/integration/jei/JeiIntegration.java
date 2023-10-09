package com.mikitellurium.telluriumsrandomstuff.integration.jei;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.client.gui.render.BlockStateRenderer;
import com.mikitellurium.telluriumsrandomstuff.common.recipe.SoulFurnaceSmeltingRecipe;
import com.mikitellurium.telluriumsrandomstuff.integration.jei.helper.BlockIngredientHelper;
import com.mikitellurium.telluriumsrandomstuff.integration.jei.recipe.*;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.registry.ModFluids;
import com.mikitellurium.telluriumsrandomstuff.client.gui.screen.SoulFurnaceScreen;
import com.mikitellurium.telluriumsrandomstuff.util.MouseUtils;
import com.mikitellurium.telluriumsrandomstuff.util.RecipeUtils;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.ingredients.ITypedIngredient;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.vanilla.IVanillaRecipeFactory;
import mezz.jei.api.registration.*;
import mezz.jei.api.runtime.IClickableIngredient;
import mezz.jei.api.runtime.IIngredientManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@JeiPlugin
public class JeiIntegration implements IModPlugin {

    public static RecipeType<SoulFurnaceSmeltingRecipe> SOUL_FURNACE_SMELTING_RECIPE_TYPE =
            new RecipeType<>(SoulFurnaceSmeltingCategory.UID, SoulFurnaceSmeltingRecipe.class);
    public static final RecipeType<SoulLavaInfoCategory.Recipe> SOUL_LAVA_INFO_TYPE =
            new RecipeType<>(SoulLavaInfoCategory.UID, SoulLavaInfoCategory.Recipe.class);
    public static final RecipeType<AmethystLensInfoCategory.Recipe> AMETHYST_LENS_INFO_TYPE =
            new RecipeType<>(AmethystLensInfoCategory.UID, AmethystLensInfoCategory.Recipe.class);

    public static final IIngredientType<Block> BLOCK = () -> Block.class;

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return new ResourceLocation(TelluriumsRandomStuffMod.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
        //IPlatformFluidHelper<?> fluidHelper = registration.getJeiHelpers().getPlatformFluidHelper();
        registration.addRecipeCategories(new SoulFurnaceSmeltingCategory(guiHelper));
        registration.addRecipeCategories(new SoulLavaInfoCategory(guiHelper));
        registration.addRecipeCategories(new AmethystLensInfoCategory(guiHelper));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
        IVanillaRecipeFactory recipeFactory = registration.getVanillaRecipeFactory();

        // Convert vanilla smelting recipes in soul furnace recipes
        List<SmeltingRecipe> vanillaRecipes = recipeManager.getAllRecipesFor(net.minecraft.world.item.crafting.RecipeType.SMELTING);
        List<SoulFurnaceSmeltingRecipe> convertedRecipes = RecipeUtils.getConvertedVanillaRecipes(vanillaRecipes);

        // Get soul furnace recipes from json files
        List<SoulLavaInfoCategory.Recipe> soulLavaInfoRecipes = List.of(new SoulLavaInfoCategory.Recipe());

        List<AmethystLensInfoCategory.Recipe> amethystLensInfoRecipes = List.of(new AmethystLensInfoCategory.Recipe());

        registration.addRecipes(SOUL_FURNACE_SMELTING_RECIPE_TYPE, convertedRecipes);
        registration.addRecipes(SOUL_LAVA_INFO_TYPE, soulLavaInfoRecipes);
        registration.addRecipes(AMETHYST_LENS_INFO_TYPE, amethystLensInfoRecipes);
        registration.addRecipes(RecipeTypes.ANVIL, RecipeUtils.getAnvilRecipes(recipeFactory));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.SOUL_FURNACE.get()), SOUL_FURNACE_SMELTING_RECIPE_TYPE);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(SoulFurnaceScreen.class, 77, 28, 28, 21, SOUL_FURNACE_SMELTING_RECIPE_TYPE);

        IIngredientManager ingredientManager = registration.getJeiHelpers().getIngredientManager();
        // Make the soul lava tank clickable
        registration.addGuiContainerHandler(SoulFurnaceScreen.class, new IGuiContainerHandler<>() {
            @Override
            public Optional<IClickableIngredient<?>> getClickableIngredientUnderMouse(SoulFurnaceScreen containerScreen, double mouseX, double mouseY) {
                Rect2i area = containerScreen.getSoulLavaStorage();
                ITypedIngredient<FluidStack> soulLava =
                        ingredientManager.createTypedIngredient(ForgeTypes.FLUID_STACK, new FluidStack(ModFluids.SOUL_LAVA_SOURCE.get(), 10)).get();
                return MouseUtils.isAboveArea(mouseX, mouseY, area.getX(), area.getY(), area.getWidth(), area.getHeight()) ?
                        Optional.of(new ClickableIngredient<>(soulLava, area)) : Optional.empty();
            }
        });
    }

    @Override
    public void registerIngredients(IModIngredientRegistration registration) {
        registration.register(BLOCK, List.of(), new BlockIngredientHelper(), new BlockStateRenderer());
    }

}
