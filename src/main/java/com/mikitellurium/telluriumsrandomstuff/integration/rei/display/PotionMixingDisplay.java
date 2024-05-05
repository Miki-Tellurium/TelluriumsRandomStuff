package com.mikitellurium.telluriumsrandomstuff.integration.rei.display;

import com.mikitellurium.telluriumsrandomstuff.common.recipe.PotionMixingRecipe;
import com.mikitellurium.telluriumsrandomstuff.common.recipe.SoulFurnaceSmeltingRecipe;
import com.mikitellurium.telluriumsrandomstuff.integration.jei.category.PotionMixingCategory;
import com.mikitellurium.telluriumsrandomstuff.integration.rei.util.ModDisplayCategories;
import com.mikitellurium.telluriumsrandomstuff.util.RecipeHelper;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.SmeltingRecipe;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PotionMixingDisplay extends SoulLavaDisplay {

    public PotionMixingDisplay(PotionMixingCategory.Recipe recipe) {
        this(EntryIngredients.ofIngredients(recipe.Ingredients()),
                Collections.singletonList(EntryIngredients.of(recipe.getResultItem(BasicDisplay.registryAccess()))),
                Optional.ofNullable(recipe.getId()));
    }

    public PotionMixingDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs,
                               Optional<ResourceLocation> location) {
        super(inputs, outputs, location);
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return ModDisplayCategories.SOUL_FURNACE_SMELTING;
    }

    public static DisplaySerializer<PotionMixingDisplay> serializer() {
        return Serializer.ofSimple(PotionMixingDisplay::new);
    }

}
