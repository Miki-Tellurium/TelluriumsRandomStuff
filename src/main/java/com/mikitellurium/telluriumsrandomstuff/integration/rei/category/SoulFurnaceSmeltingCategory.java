package com.mikitellurium.telluriumsrandomstuff.integration.rei.category;

import com.google.common.collect.Lists;
import com.mikitellurium.telluriumsrandomstuff.integration.rei.display.SoulFurnaceSmeltingDisplay;
import com.mikitellurium.telluriumsrandomstuff.integration.rei.guielement.SoulBurningFireWidget;
import com.mikitellurium.telluriumsrandomstuff.integration.rei.guielement.SoulLavaTankWidget;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import me.shedaniel.math.Dimension;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.TooltipContext;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class SoulFurnaceSmeltingCategory implements DisplayCategory<SoulFurnaceSmeltingDisplay> {

    public static final CategoryIdentifier<SoulFurnaceSmeltingDisplay> ID =
            CategoryIdentifier.of(FastLoc.modLoc("soul_furnace_smelting"));

    private final int smeltingTime = 100;

    @Override
    public CategoryIdentifier<? extends SoulFurnaceSmeltingDisplay> getCategoryIdentifier() {
        return ID;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("jei.telluriumsrandomstuff.category.soul_furnace_smelting");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModBlocks.SOUL_FURNACE.get());
    }

    @Override
    public List<Widget> setupDisplay(SoulFurnaceSmeltingDisplay display, Rectangle bounds) {
        Point startPoint = new Point(bounds.x, bounds.y);
        List<Widget> widgets = new ArrayList<>();
        Rectangle recipeBounds = bounds.clone();
        recipeBounds.setSize(bounds.width, bounds.height + 4);
        widgets.add(Widgets.createRecipeBase(recipeBounds));

        widgets.add(Widgets.createTexturedWidget(FastLoc.JEI_GUI_TEXTURE, startPoint.x, startPoint.y + 1,
                0, 0, 120, 71, 256, 256));

        widgets.add(new SoulBurningFireWidget(
                new Rectangle(new Point(startPoint.x + 37, startPoint.y + 44), new Dimension(14, 14)))
                .animationDurationMS(10000));
        widgets.add(Widgets.createArrow(new Point(startPoint.x + 58, startPoint.y + 24))
                .animationDurationTicks(smeltingTime));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 35, startPoint.y + 25))
                .entries(display.getInputEntries().get(0))
                .disableBackground()
                .markInput());
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 95, startPoint.y + 25))
                .entries(display.getOutputEntries().get(0))
                .disableBackground()
                .markOutput());
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 4, startPoint.y + 55))
                .entries(List.of(EntryStacks.of(ModItems.SOUL_LAVA_BUCKET.get())))
                .disableBackground()
                .markOutput());
        SoulLavaTankWidget soulLavaTank = new SoulLavaTankWidget(startPoint.x + 4, startPoint.y + 3);
        widgets.add(Widgets.withTooltip(soulLavaTank, soulLavaTank.getFluidTooltips(TooltipContext.ofMouse())));

        return widgets;
    }

    @Override
    public int getDisplayWidth(SoulFurnaceSmeltingDisplay display) {
        return 120;
    }

    @Override
    public int getDisplayHeight() {
        return 71;
    }

}
