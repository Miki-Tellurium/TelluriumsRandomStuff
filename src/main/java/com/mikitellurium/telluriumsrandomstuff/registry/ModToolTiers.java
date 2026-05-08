package com.mikitellurium.telluriumsrandomstuff.registry;

import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

public class ModToolTiers {
    public static final Tier SPIRITED_IRON = TierSortingRegistry.registerTier(
            new ForgeTier(2, 500, 6.0F, 2.5F, 20,
                    ModTags.Blocks.NEEDS_SPIRITED_IRON_TOOL, () -> Ingredient.of(ModItems.SPIRITED_IRON_INGOT.get())),
            FastLoc.modLoc("spirited_iron"), List.of(Tiers.IRON), List.of(Tiers.DIAMOND));
}
