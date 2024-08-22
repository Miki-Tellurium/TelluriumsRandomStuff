package com.mikitellurium.telluriumsrandomstuff.integration.jei;

import com.mikitellurium.telluriumsrandomstuff.client.gui.screen.*;
import com.mikitellurium.telluriumsrandomstuff.common.recipe.CompactingRecipe;
import com.mikitellurium.telluriumsrandomstuff.common.recipe.SoulFurnaceSmeltingRecipe;
import com.mikitellurium.telluriumsrandomstuff.common.recipe.SoulInfusionRecipe;
import com.mikitellurium.telluriumsrandomstuff.common.recipe.SoulLavaTransmutationRecipe;
import com.mikitellurium.telluriumsrandomstuff.integration.jei.category.*;
import com.mikitellurium.telluriumsrandomstuff.integration.jei.helper.BlockIngredientHelper;
import com.mikitellurium.telluriumsrandomstuff.integration.jei.util.BlockStateRenderer;
import com.mikitellurium.telluriumsrandomstuff.integration.jei.util.ClickableIngredient;
import com.mikitellurium.telluriumsrandomstuff.integration.jei.util.ModIngredientTypes;
import com.mikitellurium.telluriumsrandomstuff.integration.util.PotionMixingHelper;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.registry.ModFluids;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import com.mikitellurium.telluriumsrandomstuff.util.MouseUtils;
import com.mikitellurium.telluriumsrandomstuff.util.RecipeHelper;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import mezz.jei.api.helpers.IGuiHelper;
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
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@JeiPlugin
public class JeiIntegration implements IModPlugin {

    public static RecipeType<SoulFurnaceSmeltingRecipe> SOUL_FURNACE_SMELTING_RECIPE_TYPE =
            new RecipeType<>(SoulFurnaceSmeltingCategory.UID, SoulFurnaceSmeltingRecipe.class);
    public static final RecipeType<SoulLavaInfoCategory.Recipe> SOUL_LAVA_INFO_TYPE =
            new RecipeType<>(SoulLavaInfoCategory.UID, SoulLavaInfoCategory.Recipe.class);
    public static final RecipeType<AmethystLensInfoCategory.Recipe> AMETHYST_LENS_INFO_TYPE =
            new RecipeType<>(AmethystLensInfoCategory.UID, AmethystLensInfoCategory.Recipe.class);
    public static RecipeType<SoulInfusionRecipe> SOUL_INFUSION_RECIPE_TYPE =
            new RecipeType<>(SoulInfusionCategory.UID, SoulInfusionRecipe.class);
    public static RecipeType<SoulLavaTransmutationRecipe> SOUL_LAVA_TRANSMUTATION_RECIPE_TYPE =
            new RecipeType<>(SoulLavaTransmutationCategory.UID, SoulLavaTransmutationRecipe.class);
    public static RecipeType<PotionMixingHelper> POTION_MIXING_RECIPE_TYPE =
            new RecipeType<>(PotionMixingCategory.UID, PotionMixingHelper.class);
    public static RecipeType<CompactingRecipe> COMPACTING_RECIPE_TYPE =
            new RecipeType<>(CompactingCategory.UID, CompactingRecipe.class);

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return FastLoc.modLoc("jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(
                new SoulFurnaceSmeltingCategory(guiHelper),
                new SoulLavaInfoCategory(guiHelper),
                new AmethystLensInfoCategory(guiHelper),
                new SoulInfusionCategory(guiHelper),
                new SoulLavaTransmutationCategory(guiHelper),
                new PotionMixingCategory(guiHelper),
                new CompactingCategory(guiHelper)
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
        IVanillaRecipeFactory recipeFactory = registration.getVanillaRecipeFactory();

        List<SmeltingRecipe> vanillaRecipes = recipeManager.getAllRecipesFor(net.minecraft.world.item.crafting.RecipeType.SMELTING);
        List<SoulFurnaceSmeltingRecipe> convertedRecipes = RecipeHelper.getConvertedVanillaRecipes(vanillaRecipes);
        List<SoulLavaTransmutationRecipe> soulLavaTransmutationRecipes = recipeManager.getAllRecipesFor(SoulLavaTransmutationRecipe.Type.INSTANCE);
        List<SoulInfusionRecipe> soulInfusionRecipes = recipeManager.getAllRecipesFor(SoulInfusionRecipe.Type.INSTANCE);
        List<CompactingRecipe> compactingRecipes = recipeManager.getAllRecipesFor(CompactingRecipe.Type.INSTANCE);

        List<SoulLavaInfoCategory.Recipe> soulLavaInfoRecipes = List.of(new SoulLavaInfoCategory.Recipe());
        List<AmethystLensInfoCategory.Recipe> amethystLensInfoRecipes = List.of(
                new AmethystLensInfoCategory.Recipe(true),
                new AmethystLensInfoCategory.Recipe(false)
                );

        registration.addRecipes(SOUL_FURNACE_SMELTING_RECIPE_TYPE, convertedRecipes);
        registration.addRecipes(SOUL_LAVA_TRANSMUTATION_RECIPE_TYPE, soulLavaTransmutationRecipes);
        registration.addRecipes(SOUL_INFUSION_RECIPE_TYPE, soulInfusionRecipes);
        registration.addRecipes(SOUL_LAVA_INFO_TYPE, soulLavaInfoRecipes);
        registration.addRecipes(AMETHYST_LENS_INFO_TYPE, amethystLensInfoRecipes);
        registration.addRecipes(COMPACTING_RECIPE_TYPE, compactingRecipes);
        registration.addRecipes(POTION_MIXING_RECIPE_TYPE, List.of(
                new PotionMixingHelper.Amplifier(),
                new PotionMixingHelper.Duration(),
                new PotionMixingHelper.Mixed()
        ));
        registration.addRecipes(RecipeTypes.ANVIL, RecipeHelper.getAnvilRecipes(new RecipeHelper.RepairData(Ingredient.of(ModItems.OPAL_CRYSTAL.get()),
                ModItems.OPAL_CRYSTAL_SWORD.get().getDefaultInstance(),
                ModItems.OPAL_CRYSTAL_AXE.get().getDefaultInstance(),
                ModItems.OPAL_CRYSTAL_PICKAXE.get().getDefaultInstance(),
                ModItems.OPAL_CRYSTAL_SHOVEL.get().getDefaultInstance(),
                ModItems.OPAL_CRYSTAL_HOE.get().getDefaultInstance()), recipeFactory));
        registration.addRecipes(RecipeTypes.ANVIL, RecipeHelper.getAnvilRecipes(new RecipeHelper.RepairData(Ingredient.of(ModItems.SOUL_INFUSED_IRON_INGOT.get()),
                ModItems.SOUL_INFUSED_IRON_SWORD.get().getDefaultInstance(),
                ModItems.SOUL_INFUSED_IRON_AXE.get().getDefaultInstance(),
                ModItems.SOUL_INFUSED_IRON_PICKAXE.get().getDefaultInstance(),
                ModItems.SOUL_INFUSED_IRON_SHOVEL.get().getDefaultInstance(),
                ModItems.SOUL_INFUSED_IRON_HOE.get().getDefaultInstance(),
                ModItems.SOUL_INFUSED_IRON_BOOTS.get().getDefaultInstance(),
                ModItems.SOUL_INFUSED_IRON_LEGGINGS.get().getDefaultInstance(),
                ModItems.SOUL_INFUSED_IRON_CHESTPLATE.get().getDefaultInstance(),
                ModItems.SOUL_INFUSED_IRON_HELMET.get().getDefaultInstance(),
                ModItems.GRAPPLING_HOOK.get().getDefaultInstance()),recipeFactory));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.SOUL_FURNACE.get()), SOUL_FURNACE_SMELTING_RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.SOUL_FURNACE.get()), RecipeTypes.SMELTING);
        registration.addRecipeCatalyst(new ItemStack(ModItems.SOUL_INFUSER_LIT.get()), SOUL_INFUSION_RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(Blocks.CAULDRON), SOUL_LAVA_TRANSMUTATION_RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.ALCHEMIXER.get()), POTION_MIXING_RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModItems.SOUL_COMPACTOR_LIT.get()), COMPACTING_RECIPE_TYPE);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(SoulFurnaceScreen.class, 77, 28, 28, 21, SOUL_FURNACE_SMELTING_RECIPE_TYPE);
        registration.addRecipeClickArea(SoulInfuserScreen.class, 54, 33, 55, 18, SOUL_INFUSION_RECIPE_TYPE);
        registration.addRecipeClickArea(AlchemixerScreen.class, 88, 13, 22, 43, POTION_MIXING_RECIPE_TYPE);
        registration.addRecipeClickArea(SoulCompactorScreen.class, 75, 26, 34, 24, COMPACTING_RECIPE_TYPE);

        IIngredientManager ingredientManager = registration.getJeiHelpers().getIngredientManager();
        // Make the soul lava tank clickable
        registration.addGuiContainerHandler(AbstractSoulFuelScreen.class, new IGuiContainerHandler<>() {
            @Override
            public Optional<IClickableIngredient<?>> getClickableIngredientUnderMouse(AbstractSoulFuelScreen containerScreen, double mouseX, double mouseY) {
                Rect2i area = containerScreen.getSoulLavaStorage();
                ITypedIngredient<FluidStack> soulLava = ingredientManager.createTypedIngredient(
                        ForgeTypes.FLUID_STACK, new FluidStack(ModFluids.SOUL_LAVA_SOURCE.get(), 10)).get();
                return MouseUtils.isAboveArea(mouseX, mouseY, area.getX(), area.getY(), area.getWidth(), area.getHeight()) ?
                        Optional.of(new ClickableIngredient<>(soulLava, area)) : Optional.empty();
            }
        });
    }

    @Override
    public void registerIngredients(IModIngredientRegistration registration) {
        registration.register(ModIngredientTypes.BLOCK_STATE, List.of(), new BlockIngredientHelper(), new BlockStateRenderer());
    }

}
