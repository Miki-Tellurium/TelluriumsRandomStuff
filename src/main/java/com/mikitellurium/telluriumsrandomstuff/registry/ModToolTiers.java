package com.mikitellurium.telluriumsrandomstuff.registry;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

public class ModToolTiers {

    public static final Tier OPAL_CRYSTAL = TierSortingRegistry.registerTier(
            new ForgeTier(3, 250, 10.0f, 3.0f, 15,
                    BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(ModItems.OPAL_CRYSTAL.get())),
            new ResourceLocation(TelluriumsRandomStuffMod.MOD_ID, "opal_crystal"),
            List.of(Tiers.IRON), List.of(Tiers.NETHERITE));

}
