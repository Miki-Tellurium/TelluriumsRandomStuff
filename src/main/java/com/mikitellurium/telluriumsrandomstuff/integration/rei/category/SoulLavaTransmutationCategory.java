package com.mikitellurium.telluriumsrandomstuff.integration.rei.category;

import com.mikitellurium.telluriumsrandomstuff.integration.rei.display.AmethystLensInfoDisplay;
import com.mikitellurium.telluriumsrandomstuff.integration.rei.display.SoulLavaTransmutationDisplay;
import com.mikitellurium.telluriumsrandomstuff.integration.rei.util.BlockStateEntryType;
import com.mikitellurium.telluriumsrandomstuff.integration.rei.util.ModDisplayCategories;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SoulLavaTransmutationCategory implements DisplayCategory<SoulLavaTransmutationDisplay> {

    @Override
    public CategoryIdentifier<? extends SoulLavaTransmutationDisplay> getCategoryIdentifier() {
        return ModDisplayCategories.SOUL_LAVA_TRANSMUTATION;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("jei.telluriumsrandomstuff.category.soul_lava_transmutation");
    }

    @Override
    public Renderer getIcon() {
        return EntryStack.of(BlockStateEntryType.TYPE, ModBlocks.SOUL_LAVA_CAULDRON.get().defaultBlockState());
    }

    @Override
    public List<Widget> setupDisplay(SoulLavaTransmutationDisplay display, Rectangle bounds) {
        Point startPoint = new Point(bounds.x, bounds.y);
        List<Widget> widgets = new ArrayList<>();
        widgets.add(Widgets.createRecipeBase(bounds));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 5, startPoint.y + 5))
                .entries(display.getInputEntries().get(0))
                .markInput());
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 47, startPoint.y + 47))
                .entries(display.getOutputEntries().get(0))
                .markOutput());
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 5, startPoint.y + 47))
                .entry((EntryStack<?>)this.getIcon())
                .disableBackground()
                .markInput());

        widgets.add(Widgets.createDrawableWidget((graphics, mouseX, mouseY, delta) -> {
            graphics.blit(FastLoc.GUI_ELEMENTS_TEXTURE, startPoint.x + 5, startPoint.y + 25,
                    55, 26, 16, 24);
            graphics.blit(FastLoc.GUI_ELEMENTS_TEXTURE, startPoint.x + 22, startPoint.y + 46,
                    0, 0, 24, 16);
        }));

        return widgets;
    }

    @Override
    public int getDisplayHeight() {
        return 68;
    }

    @Override
    public int getDisplayWidth(SoulLavaTransmutationDisplay display) {
        return 68;
    }

}
