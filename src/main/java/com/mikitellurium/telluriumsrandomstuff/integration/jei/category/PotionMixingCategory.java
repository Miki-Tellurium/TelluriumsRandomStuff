package com.mikitellurium.telluriumsrandomstuff.integration.jei.category;

import com.mikitellurium.telluriumsrandomstuff.common.recipe.MobEffectUpgrade;
import com.mikitellurium.telluriumsrandomstuff.common.recipe.PotionMixingRecipe;
import com.mikitellurium.telluriumsrandomstuff.integration.jei.JeiIntegration;
import com.mikitellurium.telluriumsrandomstuff.integration.jei.util.BrewingBubblesTickTimer;
import com.mikitellurium.telluriumsrandomstuff.integration.jei.util.FluidTankRenderer;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.registry.ModFluids;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import com.mikitellurium.telluriumsrandomstuff.util.RecipeHelper;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.ITickTimer;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class PotionMixingCategory implements IRecipeCategory<PotionMixingCategory.Recipe> {

    public final static ResourceLocation UID = FastLoc.modLoc("potion_mixing");

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawableAnimated bubbles;
    private final IDrawable tankGlass;

    public PotionMixingCategory(IGuiHelper guiHelper) {
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, ModBlocks.ALCHEMIXER.get().asItem().getDefaultInstance());
        this.background = guiHelper.createDrawable(JeiIntegration.GUI_TEXTURE, 0, 145, 112, 71);
        ITickTimer bubblesTickTimer = new BrewingBubblesTickTimer(guiHelper);
        bubbles = guiHelper.drawableBuilder(JeiIntegration.GUI_TEXTURE, 192, 31, 11, 29)
                .buildAnimated(bubblesTickTimer, IDrawableAnimated.StartDirection.BOTTOM);
        this.tankGlass = guiHelper.createDrawable(JeiIntegration.GUI_TEXTURE, 176, 31, 16, 48);
    }

    @Override
    public RecipeType<Recipe> getRecipeType() {
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
    public void draw(Recipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY) {
        bubbles.draw(graphics, 36, 25);
        bubbles.draw(graphics, 78, 25);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, Recipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 32, 8)
                .addIngredients(VanillaTypes.ITEM_STACK, recipe.getFirstInputs());

        builder.addSlot(RecipeIngredientRole.INPUT, 74, 8)
                .addIngredients(VanillaTypes.ITEM_STACK, recipe.getSecondInputs());

        builder.addSlot(RecipeIngredientRole.INPUT, 53, 49)
                .addIngredients(VanillaTypes.ITEM_STACK, recipe.getReceptacles());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 92, 49)
                .addIngredients(VanillaTypes.ITEM_STACK, recipe.getOutputs());

        builder.addSlot(RecipeIngredientRole.INPUT, 4, 54).addItemStack(ModItems.SOUL_LAVA_BUCKET.get().getDefaultInstance());
        builder.addSlot(RecipeIngredientRole.CATALYST, 4, 2)
                .addIngredients(ForgeTypes.FLUID_STACK, List.of(new FluidStack(ModFluids.SOUL_LAVA_SOURCE.get(), 4000)))
                .setCustomRenderer(ForgeTypes.FLUID_STACK, new FluidTankRenderer(0, 0, 16 ,48, 4000))
                .setOverlay(tankGlass, 0, 0);
        builder.addInvisibleIngredients(RecipeIngredientRole.CATALYST).addFluidStack(ModFluids.SOUL_LAVA_SOURCE.get(), 0);
    }

    public abstract static class Recipe {
        abstract List<ItemStack> getFirstInputs();
        abstract List<ItemStack> getSecondInputs();
        abstract List<ItemStack> getOutputs();
        public List<ItemStack> getReceptacles() {
            return List.of(RecipeHelper.MUNDANE_POTION, RecipeHelper.THICK_POTION);
        }
    }

    public static class Amplifier extends Recipe {

        private final List<ItemStack> inputs = RecipeHelper.getPotionsByUpgradeType(MobEffectUpgrade.AMPLIFIER);
        private final List<ItemStack> outputs = Util.make(new ArrayList<>(), (list) -> {
            inputs.forEach((itemStack -> list.add(new PotionMixingRecipe(itemStack, itemStack).assemble())));
        });

        @Override
        List<ItemStack> getFirstInputs() {
            return inputs;
        }

        @Override
        List<ItemStack> getSecondInputs() {
            return inputs;
        }

        @Override
        List<ItemStack> getOutputs() {
            return outputs;
        }
    }

    public static class Duration extends Recipe {

        private final List<ItemStack> inputs =  RecipeHelper.getPotionsByUpgradeType(MobEffectUpgrade.DURATION);
        private final List<ItemStack> outputs = Util.make(new ArrayList<>(), (list) -> {
            inputs.forEach((itemStack -> list.add(new PotionMixingRecipe(itemStack, itemStack).assemble())));
        });

        @Override
        List<ItemStack> getFirstInputs() {
            return inputs;
        }

        @Override
        List<ItemStack> getSecondInputs() {
            return inputs;
        }

        @Override
        List<ItemStack> getOutputs() {
            return outputs;
        }
    }

    public static class Mixed extends Recipe {

        private final List<ItemStack> inputs1 = RecipeHelper.getRandomPotionList(20);
        private final List<ItemStack> inputs2 = RecipeHelper.getRandomPotionList(11);
        private final List<ItemStack> outputs = Util.make(new ArrayList<>(), (list) -> {
            for (int i = 0; i < inputs1.size(); i++) {
                list.add(new PotionMixingRecipe(inputs1.get(i), inputs2.get(i)).assemble());
            }
        });

        @Override
        List<ItemStack> getFirstInputs() {
            return inputs1;
        }

        @Override
        List<ItemStack> getSecondInputs() {
            return inputs2;
        }

        @Override
        List<ItemStack> getOutputs() {
            return outputs;
        }
    }
}
