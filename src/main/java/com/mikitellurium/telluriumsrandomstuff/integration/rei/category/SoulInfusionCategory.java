package com.mikitellurium.telluriumsrandomstuff.integration.rei.category;

import com.mikitellurium.telluriumsrandomstuff.integration.rei.display.SoulInfusionDisplay;
import com.mikitellurium.telluriumsrandomstuff.integration.rei.guielement.CustomArrowWidget;
import com.mikitellurium.telluriumsrandomstuff.integration.rei.guielement.SoulBurningFireWidget;
import com.mikitellurium.telluriumsrandomstuff.integration.rei.guielement.SoulLavaTankWidget;
import com.mikitellurium.telluriumsrandomstuff.integration.rei.util.ModDisplayCategories;
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
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class SoulInfusionCategory implements DisplayCategory<SoulInfusionDisplay> {

    private final int infusionTime = 120;

    @Override
    public CategoryIdentifier<? extends SoulInfusionDisplay> getCategoryIdentifier() {
        return ModDisplayCategories.SOUL_INFUSION;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("jei.telluriumsrandomstuff.category.soul_infusion");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModBlocks.SOUL_INFUSER_LIT.get());
    }

    @Override
    public List<Widget> setupDisplay(SoulInfusionDisplay display, Rectangle bounds) {
        Point startPoint = new Point(bounds.x, bounds.y);
        List<Widget> widgets = new ArrayList<>();
        Rectangle recipeBounds = bounds.clone();
        recipeBounds.setSize(bounds.width, bounds.height + 4);
        widgets.add(Widgets.createRecipeBase(recipeBounds));

        widgets.add(Widgets.createTexturedWidget(FastLoc.JEI_GUI_TEXTURE, startPoint.x, startPoint.y + 1,
                0, 72, this.getDisplayWidth(display), this.getDisplayHeight(), 256, 256));

        widgets.add(new CustomArrowWidget(FastLoc.GUI_ELEMENTS_TEXTURE, new Point(startPoint.x + 39, startPoint.y + 28),
                new Rectangle(0, 17, 55, 18))
                .animationDurationTicks(infusionTime));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 32, startPoint.y + 11))
                .entries(display.getInputEntries().get(0))
                .disableBackground()
                .markInput());
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 32, startPoint.y + 48))
                .entries(display.getInputEntries().get(1))
                .disableBackground()
                .markInput());
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 101, startPoint.y + 30))
                .entries(display.getOutputEntries().get(0))
                .disableBackground()
                .markOutput());

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 4, startPoint.y + 55))
                .entries(List.of(EntryStacks.of(ModItems.SOUL_LAVA_BUCKET.get())))
                .disableBackground()
                .markOutput());
        SoulLavaTankWidget soulLavaTank = new SoulLavaTankWidget(startPoint.x + 4, startPoint.y + 3, 4000, display.getRecipeCost());
        widgets.add(Widgets.withTooltip(soulLavaTank, soulLavaTank.getFluidTooltips(TooltipContext.ofMouse())));

        Component label = Component.translatable("jei.telluriumsrandomstuff.category.soul_infusion.cost")
                .append(": " + display.getRecipeCost());
        widgets.add(Widgets.createLabel(new Point(startPoint.x + 90, startPoint.y + 5), label)
                .color(0xFF808080)
                .noShadow());

        return widgets;
    }

    @Override
    public int getDisplayWidth(SoulInfusionDisplay display) {
        return 129;
    }

    @Override
    public int getDisplayHeight() {
        return 71;
    }

}
