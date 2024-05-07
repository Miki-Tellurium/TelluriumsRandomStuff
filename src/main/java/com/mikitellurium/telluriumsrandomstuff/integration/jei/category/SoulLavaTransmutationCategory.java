package com.mikitellurium.telluriumsrandomstuff.integration.jei.category;

import com.mikitellurium.telluriumsrandomstuff.common.recipe.SoulLavaTransmutationRecipe;
import com.mikitellurium.telluriumsrandomstuff.integration.jei.JeiIntegration;
import com.mikitellurium.telluriumsrandomstuff.integration.jei.util.ModIngredientTypes;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.registry.ModFluids;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;

public class SoulLavaTransmutationCategory implements IRecipeCategory<SoulLavaTransmutationRecipe> {

    public final static ResourceLocation UID = FastLoc.modLoc(SoulLavaTransmutationRecipe.Type.ID);

    private final IDrawable icon;
    private final IDrawable background;
    private final IDrawable downArrow;
    private final IDrawable rightArrow;
    private final IDrawable itemSlot;

    public SoulLavaTransmutationCategory(IGuiHelper guiHelper) {
        this.icon = guiHelper.createDrawableIngredient(ModIngredientTypes.BLOCK_STATE, ModBlocks.SOUL_LAVA_CAULDRON.get().defaultBlockState());
        this.background = guiHelper.createBlankDrawable(60, 60);
        this.downArrow = guiHelper.createDrawable(FastLoc.GUI_ELEMENTS_TEXTURE, 55, 26, 16, 24);
        this.rightArrow = guiHelper.createDrawable(FastLoc.GUI_ELEMENTS_TEXTURE, 0, 0, 24, 16);
        this.itemSlot = guiHelper.createDrawable(FastLoc.GUI_ELEMENTS_TEXTURE, 110, 0, 18, 18);
    }

    @Override
    public RecipeType<SoulLavaTransmutationRecipe> getRecipeType() {
        return JeiIntegration.SOUL_LAVA_TRANSMUTATION_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("jei.telluriumsrandomstuff.category.soul_lava_transmutation");
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
    public void draw(SoulLavaTransmutationRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics graphics,
                     double mouseX, double mouseY) {
        itemSlot.draw(graphics, 0, 1);
        itemSlot.draw(graphics, 43, 39);
        downArrow.draw(graphics, 1, 20);
        rightArrow.draw(graphics, 19, 41);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, SoulLavaTransmutationRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 2).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.CATALYST, 1, 42)
                .addIngredient(ModIngredientTypes.BLOCK_STATE, ModBlocks.SOUL_LAVA_CAULDRON.get().defaultBlockState());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 44, 40).addItemStack(recipe.getResultItem(RegistryAccess.EMPTY));
        builder.addInvisibleIngredients(RecipeIngredientRole.CATALYST).addItemStack(new ItemStack(Blocks.CAULDRON));
        builder.addInvisibleIngredients(RecipeIngredientRole.CATALYST).addItemStack(new ItemStack(ModItems.SOUL_LAVA_BUCKET.get()));
        builder.addInvisibleIngredients(RecipeIngredientRole.CATALYST).addFluidStack(ModFluids.SOUL_LAVA_SOURCE.get(), 1000);
    }

}
