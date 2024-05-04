package com.mikitellurium.telluriumsrandomstuff.integration.jei.category;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mikitellurium.telluriumsrandomstuff.common.recipe.SoulInfusionRecipe;
import com.mikitellurium.telluriumsrandomstuff.integration.jei.JeiIntegration;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class SoulInfusionCategory extends SoulLavaTankCategory<SoulInfusionRecipe> {

    public final static ResourceLocation UID = FastLoc.modLoc("soul_infusion");

    private final Font font = Minecraft.getInstance().font;
    private final IDrawable background;
    private final IDrawable icon;
    private final LoadingCache<Integer, IDrawableAnimated> cachedArrows;
    private final int smeltingTime = 120;

    public SoulInfusionCategory(IGuiHelper guiHelper) {
        super(guiHelper, 4000, SoulInfusionRecipe::getRecipeCost);
        this.background = guiHelper.createDrawable(FastLoc.JEI_GUI_TEXTURE, 0, 72, 129, 71);
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.SOUL_INFUSER_LIT.get()));
        this.cachedArrows = CacheBuilder.newBuilder()
                .maximumSize(56)
                .build(new CacheLoader<>() {
                    @Override
                    public IDrawableAnimated load(Integer cookTime) {
                        return guiHelper.drawableBuilder(FastLoc.GUI_ELEMENTS_TEXTURE, 0, 17, 55, 18)
                                .buildAnimated(cookTime, IDrawableAnimated.StartDirection.LEFT, false);
                    }
                });
    }

    protected IDrawableAnimated getArrow() {
        return this.cachedArrows.getUnchecked(smeltingTime);
    }

    @Override
    public void draw(SoulInfusionRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics graphics,
                     double mouseX, double mouseY) {
        Component translatable = Component.translatable("jei.telluriumsrandomstuff.category.soul_infusion.cost")
                .append(": " + recipe.getRecipeCost());
        graphics.drawString(font, translatable, 25, 0, 0xFF808080, false);
        getArrow().draw(graphics, 39, 27);
    }

    @Override
    public RecipeType<SoulInfusionRecipe> getRecipeType() {
        return JeiIntegration.SOUL_INFUSION_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("jei.telluriumsrandomstuff.category.soul_infusion");
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
    public void setRecipe(IRecipeLayoutBuilder builder, SoulInfusionRecipe recipe, IFocusGroup focuses) {
        super.setRecipe(builder, recipe, focuses);
        builder.addSlot(RecipeIngredientRole.INPUT, 32, 10).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 32, 47).addIngredients(recipe.getIngredients().get(1));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 101, 29).addItemStack(recipe.getResultItem(RegistryAccess.EMPTY));
    }

}
