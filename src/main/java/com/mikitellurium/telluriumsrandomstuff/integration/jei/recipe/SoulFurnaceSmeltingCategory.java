package com.mikitellurium.telluriumsrandomstuff.integration.jei.recipe;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.common.recipe.SoulFurnaceSmeltingRecipe;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.registry.ModFluids;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import com.mikitellurium.telluriumsrandomstuff.integration.jei.JeiIntegration;
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
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public class SoulFurnaceSmeltingCategory implements IRecipeCategory<SoulFurnaceSmeltingRecipe> {

    public final static ResourceLocation UID =
            new ResourceLocation(TelluriumsRandomStuffMod.MOD_ID, "soul_furnace_smelting");
    public final static ResourceLocation GUI_TEXTURE =
            new ResourceLocation(TelluriumsRandomStuffMod.MOD_ID, "textures/gui/jei_gui.png");

    private final Font font = Minecraft.getInstance().font;
    private final int textColor = 0xFF808080;
    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawable tankGlass;
    private final LoadingCache<Integer, IDrawableAnimated> cachedArrows;
    private final int smeltingTime = 100;
    private final IDrawableStatic staticFlame;
    private final IDrawableAnimated animatedFlame;

    public SoulFurnaceSmeltingCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(GUI_TEXTURE, 0, 0, 120, 72);
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.SOUL_FURNACE.get()));
        this.cachedArrows = CacheBuilder.newBuilder()
                .maximumSize(25)
                .build(new CacheLoader<>() {
                    @Override
                    public IDrawableAnimated load(Integer cookTime) {
                        return guiHelper.drawableBuilder(GUI_TEXTURE, 176, 14, 24, 17)
                                .buildAnimated(cookTime, IDrawableAnimated.StartDirection.LEFT, false);
                    }
                });
        this.staticFlame = guiHelper.createDrawable(GUI_TEXTURE, 176, 0, 14, 14);
        this.animatedFlame = guiHelper.createAnimatedDrawable(staticFlame, 300, IDrawableAnimated.StartDirection.TOP, true);
        this.tankGlass = guiHelper.createDrawable(GUI_TEXTURE, 176, 31, 16, 48);
    }

    protected IDrawableAnimated getArrow() {
        return this.cachedArrows.getUnchecked(smeltingTime);
    }

    @Override
    public void draw(SoulFurnaceSmeltingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics graphics,
                     double mouseX, double mouseY) {
        animatedFlame.draw(graphics, 37, 43);
        getArrow().draw(graphics, 58, 24);
    }

    @Override
    public RecipeType<SoulFurnaceSmeltingRecipe> getRecipeType() {
        return JeiIntegration.SOUL_FURNACE_SMELTING_RECIPE_TYPE;
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
    public void setRecipe(IRecipeLayoutBuilder builder, SoulFurnaceSmeltingRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 4, 54).addItemStack(ModItems.SOUL_LAVA_BUCKET.get().getDefaultInstance());
        builder.addSlot(RecipeIngredientRole.INPUT, 35, 24).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 95, 24).addItemStack(recipe.getResultItem(RegistryAccess.EMPTY));
        builder.addSlot(RecipeIngredientRole.CATALYST, 4, 2)
                .addIngredients(ForgeTypes.FLUID_STACK, List.of(new FluidStack(ModFluids.SOUL_LAVA_SOURCE.get(), 4000)))
                .setFluidRenderer(4000, false, 16, 48)
                .setOverlay(tankGlass, 0, 0);
        builder.addInvisibleIngredients(RecipeIngredientRole.CATALYST).addFluidStack(ModFluids.SOUL_LAVA_SOURCE.get(), 0);
    }

}
