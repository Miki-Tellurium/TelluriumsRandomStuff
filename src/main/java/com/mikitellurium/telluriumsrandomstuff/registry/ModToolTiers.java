package com.mikitellurium.telluriumsrandomstuff.registry;

import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

public class ModToolTiers {

    public static final Tier OPAL_CRYSTAL = TierSortingRegistry.registerTier(
            new ForgeTier(3, 250, 10.0f, 3.0f, 15,
                    ModTags.Blocks.NEEDS_OPAL_CRYSTAL_TOOL, () -> Ingredient.of(ModItems.OPAL_CRYSTAL.get())),
            FastLoc.modLoc("opal_crystal"), List.of(Tiers.DIAMOND), List.of());

}
