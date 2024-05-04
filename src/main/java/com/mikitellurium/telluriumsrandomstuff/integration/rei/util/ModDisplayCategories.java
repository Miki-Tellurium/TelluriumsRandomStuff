package com.mikitellurium.telluriumsrandomstuff.integration.rei.util;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.integration.rei.display.SoulFurnaceSmeltingDisplay;
import com.mikitellurium.telluriumsrandomstuff.integration.rei.display.SoulInfusionDisplay;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public interface ModDisplayCategories {

    CategoryIdentifier<SoulFurnaceSmeltingDisplay> SOUL_FURNACE_SMELTING = CategoryIdentifier.of(FastLoc.modLoc("soul_furnace_smelting"));
    CategoryIdentifier<SoulInfusionDisplay> SOUL_INFUSION = CategoryIdentifier.of(FastLoc.modLoc("soul_infusion"));

    static List<CategoryIdentifier<?>> asList() {
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

}
