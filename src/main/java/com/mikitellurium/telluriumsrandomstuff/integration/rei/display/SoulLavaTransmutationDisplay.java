package com.mikitellurium.telluriumsrandomstuff.integration.rei.display;

import com.mikitellurium.telluriumsrandomstuff.common.recipe.SoulLavaTransmutationRecipe;
import com.mikitellurium.telluriumsrandomstuff.integration.rei.util.BlockStateEntryType;
import com.mikitellurium.telluriumsrandomstuff.integration.rei.util.FluidBlockEntryType;
import com.mikitellurium.telluriumsrandomstuff.integration.rei.util.ModDisplayCategories;
import com.mikitellurium.telluriumsrandomstuff.registry.ModFluids;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SoulLavaTransmutationDisplay extends BasicDisplay {

    public SoulLavaTransmutationDisplay(SoulLavaTransmutationRecipe recipe) {
        this(getInputIngredients(recipe),
                Collections.singletonList(EntryIngredients.of(recipe.getResultItem(BasicDisplay.registryAccess()))),
                Optional.ofNullable(recipe.getId()));
    }

    public SoulLavaTransmutationDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs,
                                        Optional<ResourceLocation> location) {
        super(inputs, outputs, location);
    }

    private static List<EntryIngredient> getInputIngredients(SoulLavaTransmutationRecipe recipe) {
        List<EntryIngredient> ingredients = new ArrayList<>(EntryIngredients.ofIngredients(recipe.getIngredients()));
        ingredients.addAll(List.of(
                EntryIngredients.of(ModFluids.SOUL_LAVA_SOURCE.get()),
                EntryIngredients.of(ModItems.SOUL_LAVA_BUCKET.get()),
                EntryIngredients.of(Blocks.CAULDRON)
        ));
        return ingredients;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return ModDisplayCategories.SOUL_LAVA_TRANSMUTATION;
    }

    public static DisplaySerializer<SoulLavaTransmutationDisplay> serializer() {
        return Serializer.ofSimple(SoulLavaTransmutationDisplay::new);
    }

}
