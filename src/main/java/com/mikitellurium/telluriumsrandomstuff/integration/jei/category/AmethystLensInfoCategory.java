package com.mikitellurium.telluriumsrandomstuff.integration.jei.category;

import com.mikitellurium.telluriumsrandomstuff.integration.jei.JeiIntegration;
import com.mikitellurium.telluriumsrandomstuff.integration.jei.util.FluidBlockRenderer;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluids;

public class AmethystLensInfoCategory implements IRecipeCategory<AmethystLensInfoCategory.Recipe> {

    public final static ResourceLocation UID = FastLoc.modLoc("amethyst_lens_info");

    private final IDrawable icon;
    private final IDrawable background;
    private final IDrawable downArrow;
    private final IDrawable rightArrow;
    private final IDrawable itemSlot;

    public AmethystLensInfoCategory(IGuiHelper guiHelper) {
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModItems.AMETHYST_LENS.get()));
        this.background = guiHelper.createBlankDrawable(60, 60);
        this.downArrow = guiHelper.createDrawable(JeiIntegration.GUI_TEXTURE, 239, 24, 16, 24);
        this.rightArrow = guiHelper.createDrawable(JeiIntegration.GUI_TEXTURE, 176, 14, 24, 16);
        this.itemSlot = guiHelper.createDrawable(JeiIntegration.GUI_TEXTURE, 34, 23, 18, 18);
    }

    @Override
    public RecipeType<AmethystLensInfoCategory.Recipe> getRecipeType() {
        return JeiIntegration.AMETHYST_LENS_INFO_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("jei.telluriumsrandomstuff.category.amethyst_lens_crafting");
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void draw(AmethystLensInfoCategory.Recipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics graphics,
                     double mouseX, double mouseY) {
        itemSlot.draw(graphics, 0, 1);
        itemSlot.draw(graphics, 43, 39);
        downArrow.draw(graphics, 1, 18);
        rightArrow.draw(graphics, 19, 41);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, AmethystLensInfoCategory.Recipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 2).addItemStack(recipe.getMoltenAmethyst());
        builder.addSlot(RecipeIngredientRole.CATALYST, 1, 42).addFluidStack(Fluids.WATER, 1000)
                .setCustomRenderer(ForgeTypes.FLUID_STACK, new FluidBlockRenderer());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 44, 40).addItemStack(recipe.getAmethystLens());
        builder.addInvisibleIngredients(RecipeIngredientRole.INPUT).addItemStack(new ItemStack(ModItems.MOLTEN_AMETHYST.get()));
        builder.addInvisibleIngredients(RecipeIngredientRole.OUTPUT).addItemStack(new ItemStack(ModItems.AMETHYST_LENS.get()));
    }

    public static class Recipe {

        private final ItemStack moltenAmethyst;
        private final ItemStack amethystLens;

        public Recipe() {
            this.moltenAmethyst = new ItemStack(ModItems.MOLTEN_AMETHYST.get());
            this.amethystLens = new ItemStack(ModItems.AMETHYST_LENS.get());
        }

        public ItemStack getMoltenAmethyst() {
            return moltenAmethyst;
        }

        public ItemStack getAmethystLens() {
            return amethystLens;
        }
    }

}
