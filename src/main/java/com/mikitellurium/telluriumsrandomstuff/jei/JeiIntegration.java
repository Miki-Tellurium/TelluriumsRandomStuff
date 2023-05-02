package com.mikitellurium.telluriumsrandomstuff.jei;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.block.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.enchantment.ModEnchantments;
import com.mikitellurium.telluriumsrandomstuff.fluid.ModFluids;
import com.mikitellurium.telluriumsrandomstuff.gui.SoulFurnaceGui;
import com.mikitellurium.telluriumsrandomstuff.item.ModItems;
import com.mikitellurium.telluriumsrandomstuff.jei.recipe.*;
import com.mikitellurium.telluriumsrandomstuff.util.MouseUtils;
import com.mikitellurium.telluriumsrandomstuff.util.RecipeUtils;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.ITypedIngredient;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.vanilla.IJeiAnvilRecipe;
import mezz.jei.api.recipe.vanilla.IVanillaRecipeFactory;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IClickableIngredient;
import mezz.jei.api.runtime.IIngredientManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@JeiPlugin
public class JeiIntegration implements IModPlugin {

    public static RecipeType<SoulFurnaceRecipe> SOUL_FURNACE_RECIPE_TYPE =
            new RecipeType<>(SoulFurnaceSmeltingCategory.UID, SoulFurnaceRecipe.class);
    public static final RecipeType<SoulLavaInfoCategory.Recipe> SOUL_LAVA_INFO_TYPE =
            new RecipeType<>(SoulLavaInfoCategory.UID, SoulLavaInfoCategory.Recipe.class);

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return new ResourceLocation(TelluriumsRandomStuffMod.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(new SoulFurnaceSmeltingCategory(guiHelper));
        registration.addRecipeCategories(new SoulLavaInfoCategory(guiHelper));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
        IVanillaRecipeFactory recipeFactory = registration.getVanillaRecipeFactory();

        // Convert vanilla smelting recipes in soul furnace recipes
        List<SmeltingRecipe> vanillaRecipes = recipeManager.getAllRecipesFor(net.minecraft.world.item.crafting.RecipeType.SMELTING);
        List<SoulFurnaceRecipe> convertedRecipes = RecipeUtils.getConvertedVanillaRecipes(vanillaRecipes);
        // Get soul furnace recipes from json files
        List<SoulFurnaceRecipe> soulFurnaceRecipes = recipeManager.getAllRecipesFor(SoulFurnaceRecipe.Type.INSTANCE);

        List<SoulLavaInfoCategory.Recipe> soulLavaInfoRecipes = new ArrayList<>();
        soulLavaInfoRecipes.add(new SoulLavaInfoCategory.Recipe());

        IJeiAnvilRecipe soulHarvestingAnvilRecipe = recipeFactory.createAnvilRecipe(
                ModItems.OPALIUM_SWORD.get().getDefaultInstance(),
                RecipeUtils.soulHarvestingBooks,
                RecipeUtils.soulHarvestingSwords);

        registration.addRecipes(SOUL_FURNACE_RECIPE_TYPE, convertedRecipes);
        registration.addRecipes(SOUL_FURNACE_RECIPE_TYPE, soulFurnaceRecipes);

        registration.addRecipes(SOUL_LAVA_INFO_TYPE, soulLavaInfoRecipes);

        registration.addRecipes(RecipeTypes.ANVIL, List.of(soulHarvestingAnvilRecipe));
        registration.addRecipes(RecipeTypes.ANVIL, RecipeUtils.getAnvilRecipes(recipeFactory));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.SOUL_FURNACE.get()), SOUL_FURNACE_RECIPE_TYPE);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(SoulFurnaceGui.class, 77, 28, 28, 21, SOUL_FURNACE_RECIPE_TYPE);

        IIngredientManager ingredientManager = registration.getJeiHelpers().getIngredientManager();
        // Make the soul lava tank clickable
        registration.addGuiContainerHandler(SoulFurnaceGui.class, new IGuiContainerHandler<>() {
            @Override
            public Optional<IClickableIngredient<?>> getClickableIngredientUnderMouse(SoulFurnaceGui containerScreen, double mouseX, double mouseY) {
                Rect2i area = containerScreen.getSoulLavaStorage();
                ITypedIngredient<FluidStack> soulLava =
                        ingredientManager.createTypedIngredient(ForgeTypes.FLUID_STACK, new FluidStack(ModFluids.SOUL_LAVA_SOURCE.get(), 10)).get();
                return MouseUtils.isAboveArea(mouseX, mouseY, area.getX(), area.getY(), area.getWidth(), area.getHeight()) ?
                        Optional.of(new ClickableIngredient<>(soulLava, area)) : Optional.empty();
            }
        });
    }

}
