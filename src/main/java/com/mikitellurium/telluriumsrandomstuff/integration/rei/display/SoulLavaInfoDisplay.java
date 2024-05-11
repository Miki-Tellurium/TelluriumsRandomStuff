package com.mikitellurium.telluriumsrandomstuff.integration.rei.display;

import com.mikitellurium.telluriumsrandomstuff.integration.rei.util.ModDisplayCategories;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.registry.ModFluids;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;

import java.util.List;

public class SoulLavaInfoDisplay extends BasicDisplay {

    public SoulLavaInfoDisplay() {
        this(List.of(EntryIngredients.of(Fluids.LAVA),
                        EntryIngredients.of(ModBlocks.INFUSED_SOUL_SAND.get()),
                        EntryIngredients.of(Blocks.CAULDRON),
                        EntryIngredients.of(Items.LAVA_BUCKET)),
                List.of(EntryIngredients.of(ModFluids.SOUL_LAVA_SOURCE.get()),
                        EntryIngredients.of(ModItems.SOUL_LAVA_BUCKET.get())));
    }

    public SoulLavaInfoDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
        super(inputs, outputs);
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return ModDisplayCategories.SOUL_LAVA_INFO;
    }

    public static DisplaySerializer<SoulLavaInfoDisplay> serializer() {
        return Serializer.ofSimple(((input, output, location) -> new SoulLavaInfoDisplay(input, output)));
    }

}
