package com.mikitellurium.telluriumsrandomstuff.jei.recipe;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.block.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.fluid.ModFluids;
import com.mikitellurium.telluriumsrandomstuff.item.ModItems;
import com.mikitellurium.telluriumsrandomstuff.jei.JeiIntegration;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class SoulFurnaceSmeltingCategory implements IRecipeCategory<SoulFurnaceRecipe> {

    public final static ResourceLocation UID =
            new ResourceLocation(TelluriumsRandomStuffMod.MOD_ID, "soul_furnace_smelting");
    public final static ResourceLocation TEXTURE =
            new ResourceLocation(TelluriumsRandomStuffMod.MOD_ID, "textures/gui/soul_furnace_gui.png");

    private final IDrawable background;
    private final IDrawable icon;

    public SoulFurnaceSmeltingCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 81);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.SOUL_FURNACE.get()));
    }

    @Override
    public RecipeType<SoulFurnaceRecipe> getRecipeType() {
        return JeiIntegration.SOUL_FURNACE_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.telluriumsrandomstuff.soul_furnace");
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
    public void setRecipe(IRecipeLayoutBuilder builder, SoulFurnaceRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 56, 30).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 116, 30).addItemStack(recipe.getResultItem(RegistryAccess.EMPTY));
        builder.addInvisibleIngredients(RecipeIngredientRole.INPUT).addItemStack(ModItems.SOUL_LAVA_BUCKET.get().getDefaultInstance());
        builder.addInvisibleIngredients(RecipeIngredientRole.INPUT).addFluidStack(ModFluids.SOUL_LAVA_SOURCE.get(), 50);
    }

}
