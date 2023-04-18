package com.mikitellurium.telluriumsrandomstuff.jei.recipe;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.block.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.gui.SoulFurnaceGui;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.SmeltingRecipe;

public class SoulFurnaceRecipeCategory implements IRecipeCategory<SmeltingRecipe> {

    public static final ResourceLocation UID = new ResourceLocation(TelluriumsRandomStuffMod.MOD_ID, "smelting");
    private final IDrawable background;
    private final IDrawable icon;

    public SoulFurnaceRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(SoulFurnaceGui.GUI_TEXTURE, 0, 0, 176, 166);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.SOUL_FURNACE.get()));
    }

    @Override
    public RecipeType<SmeltingRecipe> getRecipeType() {
        return RecipeTypes.SMELTING;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.telluriumsrandomstuff.soul_furnace");
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
    public void setRecipe(IRecipeLayoutBuilder builder, SmeltingRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 56, 32).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 116, 33).addItemStack(recipe.getResultItem());
    }

}
