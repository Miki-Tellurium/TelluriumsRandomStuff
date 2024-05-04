package com.mikitellurium.telluriumsrandomstuff.integration.rei;

import com.mikitellurium.telluriumsrandomstuff.client.gui.screen.SoulCompactorScreen;
import com.mikitellurium.telluriumsrandomstuff.client.gui.screen.SoulFurnaceScreen;
import com.mikitellurium.telluriumsrandomstuff.client.gui.screen.SoulInfuserScreen;
import com.mikitellurium.telluriumsrandomstuff.common.recipe.CompactingRecipe;
import com.mikitellurium.telluriumsrandomstuff.common.recipe.SoulFurnaceSmeltingRecipe;
import com.mikitellurium.telluriumsrandomstuff.common.recipe.SoulInfusionRecipe;
import com.mikitellurium.telluriumsrandomstuff.integration.rei.category.CompactingCategory;
import com.mikitellurium.telluriumsrandomstuff.integration.rei.category.SoulFurnaceSmeltingCategory;
import com.mikitellurium.telluriumsrandomstuff.integration.rei.category.SoulInfusionCategory;
import com.mikitellurium.telluriumsrandomstuff.integration.rei.display.CompactingDisplay;
import com.mikitellurium.telluriumsrandomstuff.integration.rei.display.SoulFurnaceSmeltingDisplay;
import com.mikitellurium.telluriumsrandomstuff.integration.rei.display.SoulInfusionDisplay;
import com.mikitellurium.telluriumsrandomstuff.integration.rei.util.BlockStateEntryType;
import com.mikitellurium.telluriumsrandomstuff.integration.rei.util.BlockStateRenderer;
import com.mikitellurium.telluriumsrandomstuff.integration.rei.util.ClickableSoulLavaTank;
import com.mikitellurium.telluriumsrandomstuff.integration.rei.util.ModDisplayCategories;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.entry.renderer.EntryRendererRegistry;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.forge.REIPluginClient;
import me.shedaniel.rei.plugin.common.BuiltinPlugin;
import net.minecraft.world.item.crafting.SmeltingRecipe;

@REIPluginClient
public class ReiClientIntegration implements REIClientPlugin, ModDisplayCategories {

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(
                new SoulFurnaceSmeltingCategory(),
                new SoulInfusionCategory(),
                new CompactingCategory());

        registry.addWorkstations(SOUL_FURNACE_SMELTING, EntryStacks.of(ModBlocks.SOUL_FURNACE.get()));
        registry.addWorkstations(BuiltinPlugin.SMELTING, EntryStacks.of(ModBlocks.SOUL_FURNACE.get()));
        registry.addWorkstations(SOUL_INFUSION, EntryStacks.of(ModBlocks.SOUL_INFUSER.get()));
        registry.addWorkstations(COMPACTING, EntryStacks.of(ModBlocks.SOUL_COMPACTOR.get()));

    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerFiller(SoulFurnaceSmeltingRecipe.class, SoulFurnaceSmeltingDisplay::new);
        registry.registerFiller(SmeltingRecipe.class, SoulFurnaceSmeltingDisplay::new);
        registry.registerFiller(SoulInfusionRecipe.class, SoulInfusionDisplay::new);
        registry.registerFiller(CompactingRecipe.class, CompactingDisplay::new);
    }

    @Override
    public void registerScreens(ScreenRegistry registry) {
        registry.registerContainerClickArea(new Rectangle(77, 28, 28, 21), SoulFurnaceScreen.class, SOUL_FURNACE_SMELTING);
        registry.registerContainerClickArea(new Rectangle(54, 33, 55, 18), SoulInfuserScreen.class, SOUL_INFUSION);
        registry.registerContainerClickArea(new Rectangle(75, 26, 34, 24), SoulCompactorScreen.class, COMPACTING);
        registry.registerClickArea(SoulInfuserScreen.class, new ClickableSoulLavaTank<>());
        registry.registerClickArea(SoulFurnaceScreen.class, new ClickableSoulLavaTank<>());
        registry.registerClickArea(SoulCompactorScreen.class, new ClickableSoulLavaTank<>());
    }

    @Override
    public void registerEntryRenderers(EntryRendererRegistry registry) {
        registry.register(BlockStateEntryType.BLOCK_STATE, new BlockStateRenderer());
    }

}
