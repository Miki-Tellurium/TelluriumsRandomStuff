package com.mikitellurium.telluriumsrandomstuff.integration.rei.util;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.integration.rei.display.*;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public interface ModDisplayCategories {

    CategoryIdentifier<SoulFurnaceSmeltingDisplay> SOUL_FURNACE_SMELTING = CategoryIdentifier.of(FastLoc.modLoc("soul_furnace_smelting"));
    CategoryIdentifier<SoulInfusionDisplay> SOUL_INFUSION = CategoryIdentifier.of(FastLoc.modLoc("soul_infusion"));
    CategoryIdentifier<CompactingDisplay> COMPACTING = CategoryIdentifier.of(FastLoc.modLoc("compacting"));
    CategoryIdentifier<PotionMixingDisplay> POTION_MIXING = CategoryIdentifier.of(FastLoc.modLoc("potion_mixing"));
    CategoryIdentifier<AmethystLensInfoDisplay> AMETHYST_LENS_INFO = CategoryIdentifier.of(FastLoc.modLoc("amethyst_lens_info"));
    CategoryIdentifier<SoulLavaTransmutationDisplay> SOUL_LAVA_TRANSMUTATION = CategoryIdentifier.of(FastLoc.modLoc("soul_lava_transmutation"));
    CategoryIdentifier<SoulLavaInfoDisplay> SOUL_LAVA_INFO = CategoryIdentifier.of(FastLoc.modLoc("soul_lava_info"));

    static List<CategoryIdentifier<?>> entriesAsList() {
        List<CategoryIdentifier<?>> identifiers = new ArrayList<>();
        for (Field field : ModDisplayCategories.class.getDeclaredFields()) {
            try {
                CategoryIdentifier<?> identifier = (CategoryIdentifier<?>) field.get(null);
                identifiers.add(identifier);
            } catch (IllegalAccessException e) {
                TelluriumsRandomStuffMod.LOGGER.error("Cold not access CategoryIdentifier " + field.getName());
                e.printStackTrace();
            }
        }
        return identifiers;
    }

    static List<CategoryIdentifier<?>> useSoulLava() {
        return List.of(SOUL_FURNACE_SMELTING, SOUL_INFUSION, COMPACTING, POTION_MIXING, SOUL_LAVA_TRANSMUTATION);
    }

}
