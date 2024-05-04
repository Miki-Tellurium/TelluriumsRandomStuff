package com.mikitellurium.telluriumsrandomstuff.integration.rei.display;

import com.mikitellurium.telluriumsrandomstuff.common.recipe.SoulInfusionRecipe;
import com.mikitellurium.telluriumsrandomstuff.integration.rei.category.SoulInfusionCategory;
import com.mikitellurium.telluriumsrandomstuff.integration.rei.util.ModDisplayCategories;
import com.mikitellurium.telluriumsrandomstuff.util.LogUtils;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.*;

public class SoulInfusionDisplay extends SoulLavaDisplay {

    private final int recipeCost;

    public SoulInfusionDisplay(SoulInfusionRecipe recipe) {
        this(EntryIngredients.ofIngredients(recipe.getIngredients()),
                Collections.singletonList(EntryIngredients.of(recipe.getResultItem(BasicDisplay.registryAccess()))),
                Optional.ofNullable(recipe.getId()),
                recipe.getRecipeCost());
    }

    public SoulInfusionDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs,
                               Optional<ResourceLocation> location, int recipeCost) {
        super(inputs, outputs, location);
        this.recipeCost = recipeCost;
    }

    public int getRecipeCost() {
        return recipeCost;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return ModDisplayCategories.SOUL_INFUSION;
    }

    public static DisplaySerializer<SoulInfusionDisplay> serializer() {
        return BasicDisplay.Serializer.of((input, output, location, tag) ->
                new SoulInfusionDisplay(input, output, location, tag.getInt("soul_infusion.cost")),
                (display, tag) -> tag.putInt("soul_infusion.cost", display.recipeCost));
    }

}
