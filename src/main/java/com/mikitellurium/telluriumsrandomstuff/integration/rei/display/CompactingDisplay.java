package com.mikitellurium.telluriumsrandomstuff.integration.rei.display;

import com.mikitellurium.telluriumsrandomstuff.common.recipe.CompactingRecipe;
import com.mikitellurium.telluriumsrandomstuff.common.recipe.SoulInfusionRecipe;
import com.mikitellurium.telluriumsrandomstuff.integration.rei.util.ModDisplayCategories;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.resources.ResourceLocation;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CompactingDisplay extends SoulLavaDisplay {

    private final int recipeCost;

    public CompactingDisplay(CompactingRecipe recipe) {
        this(EntryIngredients.ofIngredients(recipe.getIngredients()),
                Collections.singletonList(EntryIngredients.of(recipe.getResultItem(BasicDisplay.registryAccess()))),
                Optional.ofNullable(recipe.getId()),
                recipe.getRecipeCost());
    }

    public CompactingDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs,
                             Optional<ResourceLocation> location, int recipeCost) {
        super(inputs, outputs, location);
        this.recipeCost = recipeCost;
    }

    public int getRecipeCost() {
        return recipeCost;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return ModDisplayCategories.COMPACTING;
    }

    public static DisplaySerializer<CompactingDisplay> serializer() {
        return Serializer.of((input, output, location, tag) ->
                new CompactingDisplay(input, output, location, tag.getInt("compacting.cost")),
                (display, tag) -> tag.putInt("compacting.cost", display.recipeCost));
    }

}
