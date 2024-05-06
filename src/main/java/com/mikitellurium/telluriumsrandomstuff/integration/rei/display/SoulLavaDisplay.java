package com.mikitellurium.telluriumsrandomstuff.integration.rei.display;

import com.mikitellurium.telluriumsrandomstuff.registry.ModFluids;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class SoulLavaDisplay extends BasicDisplay {

    public SoulLavaDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
        super(inputs, outputs);
    }

    public SoulLavaDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs,
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
    public abstract CategoryIdentifier<?> getCategoryIdentifier();

}
