package com.mikitellurium.telluriumsrandomstuff.integration.rei;

import com.mikitellurium.telluriumsrandomstuff.client.hud.screen.AlchemixerScreen;
import com.mikitellurium.telluriumsrandomstuff.client.hud.screen.SoulCompactorScreen;
import com.mikitellurium.telluriumsrandomstuff.client.hud.screen.SoulFurnaceScreen;
import com.mikitellurium.telluriumsrandomstuff.client.hud.screen.SoulInfuserScreen;
import com.mikitellurium.telluriumsrandomstuff.common.recipe.CompactingRecipe;
import com.mikitellurium.telluriumsrandomstuff.common.recipe.SoulFurnaceSmeltingRecipe;
import com.mikitellurium.telluriumsrandomstuff.common.recipe.SoulInfusionRecipe;
import com.mikitellurium.telluriumsrandomstuff.common.recipe.SoulLavaTransmutationRecipe;
import com.mikitellurium.telluriumsrandomstuff.integration.rei.category.*;
import com.mikitellurium.telluriumsrandomstuff.integration.rei.display.*;
import com.mikitellurium.telluriumsrandomstuff.integration.rei.util.BlockStateEntryType;
import com.mikitellurium.telluriumsrandomstuff.integration.rei.util.ClickableSoulLavaTank;
import com.mikitellurium.telluriumsrandomstuff.integration.rei.util.ModDisplayCategories;
import com.mikitellurium.telluriumsrandomstuff.integration.util.PotionMixingHelper;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.forge.REIPluginClient;
import me.shedaniel.rei.plugin.common.BuiltinPlugin;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.block.Blocks;

@REIPluginClient
public class ReiClientIntegration implements REIClientPlugin, ModDisplayCategories {

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(
                new SoulFurnaceSmeltingCategory(),
                new SoulInfusionCategory(),
                new CompactingCategory(),
                new PotionMixingCategory(),
                new AmethystLensInfoCategory(),
                new SoulLavaTransmutationCategory(),
                new SoulLavaInfoCategory());

        registry.addWorkstations(SOUL_FURNACE_SMELTING, EntryStacks.of(ModBlocks.SOUL_FURNACE.get()));
        registry.addWorkstations(BuiltinPlugin.SMELTING, EntryStacks.of(ModBlocks.SOUL_FURNACE.get()));
        registry.addWorkstations(SOUL_INFUSION, EntryStacks.of(ModBlocks.SOUL_INFUSER.get()));
        registry.addWorkstations(COMPACTING, EntryStacks.of(ModBlocks.SOUL_COMPACTOR.get()));
        registry.addWorkstations(POTION_MIXING, EntryStacks.of(ModBlocks.ALCHEMIXER.get().asItem().getDefaultInstance()));
        registry.addWorkstations(SOUL_LAVA_TRANSMUTATION, EntryStack.of(BlockStateEntryType.TYPE, ModBlocks.SOUL_LAVA_CAULDRON.get().defaultBlockState()));
        registry.addWorkstations(SOUL_LAVA_INFO, EntryStack.of(BlockStateEntryType.TYPE, Blocks.CAULDRON.defaultBlockState()));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerFiller(SoulFurnaceSmeltingRecipe.class, SoulFurnaceSmeltingDisplay::new);
        registry.registerFiller(SmeltingRecipe.class, SoulFurnaceSmeltingDisplay::new);
        registry.registerFiller(SoulInfusionRecipe.class, SoulInfusionDisplay::new);
        registry.registerFiller(CompactingRecipe.class, CompactingDisplay::new);
        registry.registerFiller(SoulLavaTransmutationRecipe.class, SoulLavaTransmutationDisplay::new);

        registry.add(new PotionMixingDisplay(new PotionMixingHelper.Amplifier()));
        registry.add(new PotionMixingDisplay(new PotionMixingHelper.Duration()));
        registry.add(new PotionMixingDisplay(new PotionMixingHelper.Mixed()));
        registry.add(new AmethystLensInfoDisplay(new AmethystLensInfoDisplay.Recipe(true)));
        registry.add(new AmethystLensInfoDisplay(new AmethystLensInfoDisplay.Recipe(false)));
        registry.add(new SoulLavaInfoDisplay());
    }

    @Override
    public void registerScreens(ScreenRegistry registry) {
        registry.registerContainerClickArea(new Rectangle(90, 26, 26, 18), SoulFurnaceScreen.class, SOUL_FURNACE_SMELTING);
        registry.registerContainerClickArea(new Rectangle(54, 33, 55, 18), SoulInfuserScreen.class, SOUL_INFUSION);
        registry.registerContainerClickArea(new Rectangle(75, 26, 34, 24), SoulCompactorScreen.class, COMPACTING);
        registry.registerContainerClickArea(new Rectangle(88, 13, 22, 43), AlchemixerScreen.class, POTION_MIXING);
        registry.registerClickArea(SoulInfuserScreen.class, new ClickableSoulLavaTank<>());
        registry.registerClickArea(SoulFurnaceScreen.class, new ClickableSoulLavaTank<>());
        registry.registerClickArea(SoulCompactorScreen.class, new ClickableSoulLavaTank<>());
        registry.registerClickArea(AlchemixerScreen.class, new ClickableSoulLavaTank<>());
    }

}
