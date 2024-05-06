package com.mikitellurium.telluriumsrandomstuff.integration.rei.display;

import com.mikitellurium.telluriumsrandomstuff.integration.rei.util.ModDisplayCategories;
import com.mikitellurium.telluriumsrandomstuff.integration.util.PotionMixingHelper;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;

import java.util.List;
import java.util.Optional;

public class PotionMixingDisplay extends SoulLavaDisplay {

    private final String label;

    public PotionMixingDisplay(PotionMixingHelper recipe) {
        this(EntryIngredients.ofIngredients(recipe.getIngredients()),
                List.of(EntryIngredients.ofItemStacks(recipe.getOutputs())),
                recipe.getLabel().getString());
    }

    public PotionMixingDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs, String label) {
        super(inputs, outputs);
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return ModDisplayCategories.POTION_MIXING;
    }

    public static DisplaySerializer<PotionMixingDisplay> serializer() {
        return BasicDisplay.Serializer.of((input, output, location, tag) ->
                        new PotionMixingDisplay(input, output, tag.getString("potion_mixing.label")),
                (display, tag) -> tag.putString("potion_mixing.label", display.label));
    }

}
