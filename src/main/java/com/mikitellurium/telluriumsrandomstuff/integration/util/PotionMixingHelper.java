package com.mikitellurium.telluriumsrandomstuff.integration.util;

import com.mikitellurium.telluriumsrandomstuff.common.effect.MobEffectUpgradeType;
import com.mikitellurium.telluriumsrandomstuff.common.recipe.PotionMixingRecipe;
import com.mikitellurium.telluriumsrandomstuff.util.RecipeHelper;
import net.minecraft.Util;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;

public abstract class PotionMixingHelper {

    public abstract Component getLabel();
    public abstract List<ItemStack> getFirstInputs();
    public abstract List<ItemStack> getSecondInputs();
    public abstract List<ItemStack> getOutputs();
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(Ingredient.EMPTY,
                Ingredient.of(this.getFirstInputs().toArray(new ItemStack[0])),
                Ingredient.of(this.getSecondInputs().toArray(new ItemStack[0])),
                Ingredient.of(this.getReceptacles().toArray(new ItemStack[0]))
                );
    }
    public List<ItemStack> getReceptacles() {
        return List.of(RecipeHelper.MUNDANE_POTION, RecipeHelper.THICK_POTION);
    }

    public static class Amplifier extends PotionMixingHelper {

        private final List<ItemStack> inputs = RecipeHelper.getPotionsByUpgradeType(MobEffectUpgradeType.AMPLIFIER);
        private final List<ItemStack> outputs = Util.make(new ArrayList<>(), (list) -> {
            inputs.forEach((itemStack -> list.add(new PotionMixingRecipe(itemStack, itemStack).assemble())));
        });

        @Override
        public Component getLabel() {
            return Component.translatable("jei.telluriumsrandomstuff.category.potion_mixing.label.amplifier");
        }

        @Override
        public List<ItemStack> getFirstInputs() {
            return inputs;
        }

        @Override
        public List<ItemStack> getSecondInputs() {
            return inputs;
        }

        @Override
        public List<ItemStack> getOutputs() {
            return outputs;
        }
    }

    public static class Duration extends PotionMixingHelper {

        private final List<ItemStack> inputs =  RecipeHelper.getPotionsByUpgradeType(MobEffectUpgradeType.DURATION);
        private final List<ItemStack> outputs = Util.make(new ArrayList<>(), (list) -> {
            inputs.forEach((itemStack -> list.add(new PotionMixingRecipe(itemStack, itemStack).assemble())));
        });

        @Override
        public Component getLabel() {
            return Component.translatable("jei.telluriumsrandomstuff.category.potion_mixing.label.duration");
        }

        @Override
        public List<ItemStack> getFirstInputs() {
            return inputs;
        }

        @Override
        public List<ItemStack> getSecondInputs() {
            return inputs;
        }

        @Override
        public List<ItemStack> getOutputs() {
            return outputs;
        }
    }

    public static class Mixed extends PotionMixingHelper {

        private final List<ItemStack> inputs1 = RecipeHelper.getRandomPotionList(20);
        private final List<ItemStack> inputs2 = RecipeHelper.getRandomPotionList(11);
        private final List<ItemStack> outputs = Util.make(new ArrayList<>(), (list) -> {
            for (int i = 0; i < inputs1.size(); i++) {
                list.add(new PotionMixingRecipe(inputs1.get(i), inputs2.get(i)).assemble());
            }
        });

        @Override
        public Component getLabel() {
            return Component.translatable("jei.telluriumsrandomstuff.category.potion_mixing.label.effect_mix");
        }

        @Override
        public List<ItemStack> getFirstInputs() {
            return inputs1;
        }

        @Override
        public List<ItemStack> getSecondInputs() {
            return inputs2;
        }

        @Override
        public List<ItemStack> getOutputs() {
            return outputs;
        }
    }

}
