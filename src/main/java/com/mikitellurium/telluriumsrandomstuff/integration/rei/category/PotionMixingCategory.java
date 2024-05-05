package com.mikitellurium.telluriumsrandomstuff.integration.rei.category;

import com.mikitellurium.telluriumsrandomstuff.integration.rei.display.PotionMixingDisplay;
import com.mikitellurium.telluriumsrandomstuff.integration.rei.guielement.SoulBrewingBubblesWidget;
import com.mikitellurium.telluriumsrandomstuff.integration.rei.guielement.SoulLavaBuretteWidget;
import com.mikitellurium.telluriumsrandomstuff.integration.rei.guielement.SoulLavaTankWidget;
import com.mikitellurium.telluriumsrandomstuff.integration.rei.util.ModDisplayCategories;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
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

public class PotionMixingCategory implements DisplayCategory<PotionMixingDisplay> {

    private final int mixingTime = 400;

    @Override
    public CategoryIdentifier<? extends PotionMixingDisplay> getCategoryIdentifier() {
        return ModDisplayCategories.POTION_MIXING;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("jei.telluriumsrandomstuff.category.potion_mixing");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModBlocks.ALCHEMIXER.get().asItem().getDefaultInstance());
    }

    @Override
    public List<Widget> setupDisplay(PotionMixingDisplay display, Rectangle bounds) {
        Point startPoint = new Point(bounds.x, bounds.y);
        List<Widget> widgets = new ArrayList<>();
        Rectangle recipeBounds = bounds.clone();
        recipeBounds.setSize(bounds.width, bounds.height + 4);
        widgets.add(Widgets.createRecipeBase(recipeBounds));

        widgets.add(Widgets.createTexturedWidget(FastLoc.JEI_GUI_TEXTURE, startPoint.x, startPoint.y + 2,
                0, 144, this.getDisplayWidth(display), this.getDisplayHeight(), 256, 256));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 32, startPoint.y + 15))
                .entries(display.getInputEntries().get(0))
                .disableBackground()
                .markInput());
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 74, startPoint.y + 15))
                .entries(display.getInputEntries().get(1))
                .disableBackground()
                .markOutput());
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 53, startPoint.y + 51))
                .entries(display.getInputEntries().get(2))
                .disableBackground()
                .markInput());
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 92, startPoint.y + 51))
                .entries(display.getOutputEntries().get(0))
                .disableBackground()
                .markInput());

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 4, startPoint.y + 56))
                .entries(List.of(EntryStacks.of(ModItems.SOUL_LAVA_BUCKET.get())))
                .disableBackground()
                .markOutput());
        SoulLavaTankWidget soulLavaTank = new SoulLavaTankWidget(startPoint.x + 4, startPoint.y + 4, 4000, 4000);
        widgets.add(Widgets.withTooltip(soulLavaTank, soulLavaTank.getFluidTooltips(TooltipContext.ofMouse())));

        widgets.add(Widgets.createLabel(new Point(startPoint.x + 24, startPoint.y + 4), Component.translatable(display.getLabel()))
                .color(0xFF808080)
                .leftAligned()
                .noShadow());

        widgets.add(new SoulBrewingBubblesWidget(startPoint.x + 36, startPoint.y + 32));
        widgets.add(new SoulBrewingBubblesWidget(startPoint.x + 78, startPoint.y + 32));

        widgets.add(new SoulLavaBuretteWidget(startPoint.x + 57, startPoint.y + 11)
                .animationDurationTicks(mixingTime));

        return widgets;
    }

    @Override
    public int getDisplayWidth(PotionMixingDisplay display) {
        return 120;
    }

    @Override
    public int getDisplayHeight() {
        return 71;
    }

}
