package com.mikitellurium.telluriumsrandomstuff.integration.rei.display;

import com.mikitellurium.telluriumsrandomstuff.common.recipe.SoulFurnaceSmeltingRecipe;
import com.mikitellurium.telluriumsrandomstuff.integration.rei.category.SoulFurnaceSmeltingCategory;
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

public class SoulFurnaceSmeltingDisplay extends SoulLavaDisplay {

    public SoulFurnaceSmeltingDisplay(SmeltingRecipe recipe) {
        this(RecipeHelper.convertSmelting(recipe));
    }

    public SoulFurnaceSmeltingDisplay(SoulFurnaceSmeltingRecipe recipe) {
        this(EntryIngredients.ofIngredients(recipe.getIngredients()),
                Collections.singletonList(EntryIngredients.of(recipe.getResultItem(BasicDisplay.registryAccess()))),
                Optional.ofNullable(recipe.getId()));
    }

    public SoulFurnaceSmeltingDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs,
                                      Optional<ResourceLocation> location) {
        super(inputs, outputs, location);
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return SoulFurnaceSmeltingCategory.ID;
    }

    public static DisplaySerializer<SoulFurnaceSmeltingDisplay> serializer() {
        return BasicDisplay.Serializer.ofSimple(SoulFurnaceSmeltingDisplay::new);
    }

}
