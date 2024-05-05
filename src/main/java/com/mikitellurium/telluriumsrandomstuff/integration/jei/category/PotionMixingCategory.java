package com.mikitellurium.telluriumsrandomstuff.integration.jei.category;

import com.mikitellurium.telluriumsrandomstuff.client.gui.util.GuiFluidRenderer;
import com.mikitellurium.telluriumsrandomstuff.integration.jei.JeiIntegration;
import com.mikitellurium.telluriumsrandomstuff.integration.jei.util.BrewingBubblesTickTimer;
import com.mikitellurium.telluriumsrandomstuff.integration.util.PotionMixingHelper;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.registry.ModFluids;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.ITickTimer;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.common.util.TickTimer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

public class PotionMixingCategory extends SoulLavaTankCategory<PotionMixingHelper> {

    public final static ResourceLocation UID = FastLoc.modLoc("potion_mixing");

    Font font = Minecraft.getInstance().font;
    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawableAnimated bubbles;
    private final TickTimer soulLavaTimer;

    public PotionMixingCategory(IGuiHelper guiHelper) {
        super(guiHelper, 4000, (recipe) -> 4000);
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, ModBlocks.ALCHEMIXER.get().asItem().getDefaultInstance());
        this.background = guiHelper.createDrawable(FastLoc.JEI_GUI_TEXTURE, 0, 144, 115, 71);
        ITickTimer bubblesTickTimer = new BrewingBubblesTickTimer();
        bubbles = guiHelper.drawableBuilder(FastLoc.GUI_ELEMENTS_TEXTURE, 89, 0, 11, 29)
                .buildAnimated(bubblesTickTimer, IDrawableAnimated.StartDirection.BOTTOM);
        this.soulLavaTimer = new TickTimer(400, 1000, true);
    }

    @Override
    public RecipeType<PotionMixingHelper> getRecipeType() {
        return JeiIntegration.POTION_MIXING_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("jei.telluriumsrandomstuff.category.potion_mixing");
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
    public void draw(PotionMixingHelper recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY) {
        graphics.drawString(font, recipe.getLabel(), 24, 0, 0xFF808080, false);
        bubbles.draw(graphics, 36, 30);
        bubbles.draw(graphics, 78, 30);
        GuiFluidRenderer.drawBackground(graphics, 57, 10, new FluidStack(ModFluids.SOUL_LAVA_SOURCE.get(),
                        soulLavaTimer.getValue()), soulLavaTimer.getMaxValue(), 8, 35);
    }
    // todo recipe consistency
    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, PotionMixingHelper recipe, IFocusGroup focuses) {
        super.setRecipe(builder, recipe, focuses);
        builder.addSlot(RecipeIngredientRole.INPUT, 32, 13)
                .addIngredients(VanillaTypes.ITEM_STACK, recipe.getFirstInputs());

        builder.addSlot(RecipeIngredientRole.INPUT, 74, 13)
                .addIngredients(VanillaTypes.ITEM_STACK, recipe.getSecondInputs());

        builder.addSlot(RecipeIngredientRole.INPUT, 53, 49)
                .addIngredients(VanillaTypes.ITEM_STACK, recipe.getReceptacles());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 92, 49)
                .addIngredients(VanillaTypes.ITEM_STACK, recipe.getOutputs());
    }

}
