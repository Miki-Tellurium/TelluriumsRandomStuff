package com.mikitellurium.telluriumsrandomstuff.integration.jei.category;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mikitellurium.telluriumsrandomstuff.common.recipe.SoulFurnaceSmeltingRecipe;
import com.mikitellurium.telluriumsrandomstuff.integration.jei.JeiIntegration;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class SoulFurnaceSmeltingCategory extends SoulLavaTankCategory<SoulFurnaceSmeltingRecipe> {

    public final static ResourceLocation UID = FastLoc.modLoc("soul_furnace_smelting");

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawableAnimated progressBar;
    private final IDrawableAnimated animatedFlame;

    public SoulFurnaceSmeltingCategory(IGuiHelper guiHelper) {
        super(guiHelper, 4000, (recipe) -> 4000);
        this.background = guiHelper.createDrawable(FastLoc.JEI_GUI_TEXTURE, 0, 0, 120, 71);
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.SOUL_FURNACE.get()));
        IDrawableStatic staticBar = guiHelper.createDrawable(FastLoc.GUI_ELEMENTS_TEXTURE, 39, 0, 2, 16);
        this.progressBar = guiHelper.createAnimatedDrawable(staticBar, 100, IDrawableAnimated.StartDirection.BOTTOM, false);
        IDrawableStatic staticFlame = guiHelper.createDrawable(FastLoc.GUI_ELEMENTS_TEXTURE, 24, 0, 14, 14);
        this.animatedFlame = guiHelper.createAnimatedDrawable(staticFlame, 300, IDrawableAnimated.StartDirection.TOP, true);
    }

    @Override
    public void draw(SoulFurnaceSmeltingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics graphics,
                     double mouseX, double mouseY) {
        animatedFlame.draw(graphics, 34, 43);
        progressBar.draw(graphics, 51, 24);
    }

    @Override
    public RecipeType<SoulFurnaceSmeltingRecipe> getRecipeType() {
        return JeiIntegration.SOUL_FURNACE_SMELTING_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("jei.telluriumsrandomstuff.category.soul_furnace_smelting");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, SoulFurnaceSmeltingRecipe recipe, IFocusGroup focuses) {
        super.setRecipe(builder, recipe, focuses);
        builder.addSlot(RecipeIngredientRole.INPUT, 33, 24).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 95, 24).addItemStack(recipe.getResultItem(RegistryAccess.EMPTY));
    }

}
