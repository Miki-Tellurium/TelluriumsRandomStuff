package com.mikitellurium.telluriumsrandomstuff.integration.rei.display;

import com.mikitellurium.telluriumsrandomstuff.common.recipe.SoulFurnaceSmeltingRecipe;
import com.mikitellurium.telluriumsrandomstuff.integration.rei.category.SoulFurnaceSmeltingCategory;
import com.mikitellurium.telluriumsrandomstuff.registry.ModFluids;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import com.mikitellurium.telluriumsrandomstuff.util.RecipeHelper;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.SmeltingRecipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SoulFurnaceSmeltingDisplay extends BasicDisplay {

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
    public List<EntryIngredient> getInputEntries() {
        List<EntryIngredient> inputs = new ArrayList<>(super.getInputEntries());
        inputs.addAll(List.of(
                EntryIngredients.of(ModItems.SOUL_LAVA_BUCKET.get()),
                EntryIngredients.of(ModFluids.SOUL_LAVA_SOURCE.get())
                ));
        return inputs;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return SoulFurnaceSmeltingCategory.ID;
    }

    public static DisplaySerializer<SoulFurnaceSmeltingDisplay> serializer() {
        return BasicDisplay.Serializer.ofSimple(SoulFurnaceSmeltingDisplay::new);
    }

}
