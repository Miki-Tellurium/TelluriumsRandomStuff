package com.mikitellurium.telluriumsrandomstuff.integration.jei.category;

import com.mikitellurium.telluriumsrandomstuff.integration.jei.JeiIntegration;
import com.mikitellurium.telluriumsrandomstuff.registry.ModFluids;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
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
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class SoulLavaTankCategory<T> implements IRecipeCategory<T> {

    private final IDrawable tankGlass;
    private final int tankCapacity;
    private final Function<T, Integer> getFluidAmount;

    public SoulLavaTankCategory(IGuiHelper guiHelper, int tankCapacity, Function<T, Integer> getFluidAmount) {
        this.tankGlass = guiHelper.createDrawable(JeiIntegration.GUI_TEXTURE, 176, 31, 16, 48);
        this.tankCapacity = tankCapacity;
        this.getFluidAmount = getFluidAmount;
    }

    @Override
    public void draw(T recipe, IRecipeSlotsView slotsView, GuiGraphics graphics, double mouseX, double mouseY) {
    }

    @Override
    public RecipeType<T> getRecipeType() {
        return null;
    }

    @Override
    public Component getTitle() {
        return null;
    }

    @Override
    public IDrawable getBackground() {
        return null;
    }

    @Override
    public IDrawable getIcon() {
        return null;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, T recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 4, 54).addItemStack(ModItems.SOUL_LAVA_BUCKET.get().getDefaultInstance());

        builder.addSlot(RecipeIngredientRole.CATALYST, 4, 2)
                .addIngredient(ForgeTypes.FLUID_STACK, new FluidStack(ModFluids.SOUL_LAVA_SOURCE.get(), getFluidAmount.apply(recipe)))
                .setFluidRenderer(tankCapacity, false, 16, 47)
                .setOverlay(tankGlass, 0, 0);

        builder.addInvisibleIngredients(RecipeIngredientRole.CATALYST).addFluidStack(ModFluids.SOUL_LAVA_SOURCE.get(), 0);
    }

}
