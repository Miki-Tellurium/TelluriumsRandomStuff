package com.mikitellurium.telluriumsrandomstuff.integration.jei.category;

import com.mikitellurium.telluriumsrandomstuff.client.gui.util.GuiFluidRenderer;
import com.mikitellurium.telluriumsrandomstuff.common.effect.MobEffectUpgradeType;
import com.mikitellurium.telluriumsrandomstuff.common.recipe.PotionMixingRecipe;
import com.mikitellurium.telluriumsrandomstuff.integration.jei.JeiIntegration;
import com.mikitellurium.telluriumsrandomstuff.integration.jei.util.BrewingBubblesTickTimer;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.registry.ModFluids;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import com.mikitellurium.telluriumsrandomstuff.util.RecipeHelper;
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
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class PotionMixingCategory extends SoulLavaTankCategory<PotionMixingCategory.Recipe> {

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
        ITickTimer bubblesTickTimer = new BrewingBubblesTickTimer(guiHelper);
        bubbles = guiHelper.drawableBuilder(FastLoc.GUI_ELEMENTS_TEXTURE, 89, 0, 11, 29)
                .buildAnimated(bubblesTickTimer, IDrawableAnimated.StartDirection.BOTTOM);
        this.soulLavaTimer = new TickTimer(400, 1000, true);
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
        graphics.drawString(font, recipe.getLabel(), 24, 0, 0xFF606060, false);
        bubbles.draw(graphics, 36, 30);
        bubbles.draw(graphics, 78, 30);
        GuiFluidRenderer.drawBackground(graphics, 57, 10, new FluidStack(ModFluids.SOUL_LAVA_SOURCE.get(),
                        soulLavaTimer.getValue()), soulLavaTimer.getMaxValue(), 8, 35);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, Recipe recipe, IFocusGroup focuses) {
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

    public abstract static class Recipe {
        abstract Component getLabel();
        abstract List<ItemStack> getFirstInputs();
        abstract List<ItemStack> getSecondInputs();
        abstract List<ItemStack> getOutputs();
        public List<ItemStack> getReceptacles() {
            return List.of(RecipeHelper.MUNDANE_POTION, RecipeHelper.THICK_POTION);
        }
    }

    public static class Amplifier extends Recipe {

        private final List<ItemStack> inputs = RecipeHelper.getPotionsByUpgradeType(MobEffectUpgradeType.AMPLIFIER);
        private final List<ItemStack> outputs = Util.make(new ArrayList<>(), (list) -> {
            inputs.forEach((itemStack -> list.add(new PotionMixingRecipe(itemStack, itemStack).assemble())));
        });

        @Override
        Component getLabel() {
            return Component.translatable("jei.telluriumsrandomstuff.category.potion_mixing.label.amplifier");
        }

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

        private final List<ItemStack> inputs =  RecipeHelper.getPotionsByUpgradeType(MobEffectUpgradeType.DURATION);
        private final List<ItemStack> outputs = Util.make(new ArrayList<>(), (list) -> {
            inputs.forEach((itemStack -> list.add(new PotionMixingRecipe(itemStack, itemStack).assemble())));
        });

        @Override
        Component getLabel() {
            return Component.translatable("jei.telluriumsrandomstuff.category.potion_mixing.label.duration");
        }

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
        Component getLabel() {
            return Component.translatable("jei.telluriumsrandomstuff.category.potion_mixing.label.effect_mix");
        }

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
