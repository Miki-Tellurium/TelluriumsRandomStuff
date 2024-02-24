package com.mikitellurium.telluriumsrandomstuff.integration.jei.category;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mikitellurium.telluriumsrandomstuff.common.recipe.SoulFurnaceSmeltingRecipe;
import com.mikitellurium.telluriumsrandomstuff.integration.jei.JeiIntegration;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.registry.ModFluids;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
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
import net.minecraftforge.fluids.FluidStack;

import java.util.List;
import java.util.function.Function;

public class SoulFurnaceSmeltingCategory extends SoulLavaTankCategory<SoulFurnaceSmeltingRecipe> {

    public final static ResourceLocation UID = FastLoc.modLoc("soul_furnace_smelting");

    private final IDrawable background;
    private final IDrawable icon;
    private final LoadingCache<Integer, IDrawableAnimated> cachedArrows;
    private final int smeltingTime = 100;
    private final IDrawableAnimated animatedFlame;

    public SoulFurnaceSmeltingCategory(IGuiHelper guiHelper) {
        super(guiHelper, 4000, (recipe) -> 4000);
        this.background = guiHelper.createDrawable(JeiIntegration.GUI_TEXTURE, 0, 0, 120, 71);
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.SOUL_FURNACE.get()));
        this.cachedArrows = CacheBuilder.newBuilder()
                .maximumSize(25)
                .build(new CacheLoader<>() {
                    @Override
                    public IDrawableAnimated load(Integer cookTime) {
                        return guiHelper.drawableBuilder(JeiIntegration.GUI_TEXTURE, 176, 14, 24, 17)
                                .buildAnimated(cookTime, IDrawableAnimated.StartDirection.LEFT, false);
                    }
                });
        IDrawableStatic staticFlame = guiHelper.createDrawable(JeiIntegration.GUI_TEXTURE, 176, 0, 14, 14);
        this.animatedFlame = guiHelper.createAnimatedDrawable(staticFlame, 300, IDrawableAnimated.StartDirection.TOP, true);
    }

    protected IDrawableAnimated getArrow() {
        return this.cachedArrows.getUnchecked(smeltingTime);
    }

    @Override
    public void draw(SoulFurnaceSmeltingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics graphics,
                     double mouseX, double mouseY) {
        animatedFlame.draw(graphics, 37, 43);
        getArrow().draw(graphics, 58, 23);
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
        builder.addSlot(RecipeIngredientRole.INPUT, 35, 24).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 95, 24).addItemStack(recipe.getResultItem(RegistryAccess.EMPTY));
    }

}
