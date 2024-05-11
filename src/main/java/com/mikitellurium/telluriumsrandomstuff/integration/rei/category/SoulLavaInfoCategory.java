package com.mikitellurium.telluriumsrandomstuff.integration.rei.category;

import com.mikitellurium.telluriumsrandomstuff.integration.rei.display.SoulLavaInfoDisplay;
import com.mikitellurium.telluriumsrandomstuff.integration.rei.util.BlockStateEntryType;
import com.mikitellurium.telluriumsrandomstuff.integration.rei.util.FluidBlockEntryType;
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
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class SoulLavaInfoCategory implements DisplayCategory<SoulLavaInfoDisplay> {

    private final Font font = Minecraft.getInstance().font;

    @Override
    public CategoryIdentifier<? extends SoulLavaInfoDisplay> getCategoryIdentifier() {
        return ModDisplayCategories.SOUL_LAVA_INFO;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("jei.telluriumsrandomstuff.category.soul_lava_crafting");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(new ItemStack(ModItems.SOUL_LAVA_BUCKET.get()));
    }

    @Override
    public List<Widget> setupDisplay(SoulLavaInfoDisplay display, Rectangle bounds) {
        Point startPoint = new Point(bounds.x, bounds.y);
        List<Widget> widgets = new ArrayList<>();
        widgets.add(Widgets.createRecipeBase(bounds));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 95, startPoint.y + 9))
                .entry(EntryStack.of(FluidBlockEntryType.TYPE, Fluids.LAVA))
                .disableBackground()
                .markInput());
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 95, startPoint.y + 16))
                .entry(EntryStack.of(BlockStateEntryType.TYPE, ModBlocks.INFUSED_SOUL_SAND.get().defaultBlockState()))
                .disableBackground()
                .markOutput());
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 95, startPoint.y + 58))
                .entry(EntryStack.of(BlockStateEntryType.TYPE, Blocks.CAULDRON.defaultBlockState()))
                .disableBackground()
                .markInput());

        widgets.add(Widgets.createDrawableWidget((graphics, mouseX, mouseY, delta) -> {
            graphics.blit(FastLoc.GUI_ELEMENTS_TEXTURE, startPoint.x + 95, startPoint.y + 33,
                    55, 0, 16, 24);
            drawSplitString(this.font, Component.translatable("jei.telluriumsrandomstuff.category.soul_lava_crafting.description"),
                    graphics, startPoint.x + 5, startPoint.y + 8, 90, 0);
        }));

        return widgets;
    }

    private void drawSplitString(Font font, FormattedText text, GuiGraphics graphics, int x, int y, int width, int color) {
        for (FormattedText s : splitComponent(font, text, width)) {
            graphics.drawString(this.font, Language.getInstance().getVisualOrder(s), x, y , color, false);
            y += font.lineHeight;
        }
    }

    private List<FormattedText> splitComponent(Font font, FormattedText text, int width) {
        return font.getSplitter().splitLines(text, width, Style.EMPTY);
    }

    @Override
    public int getDisplayWidth(SoulLavaInfoDisplay display) {
        return 120;
    }

    @Override
    public int getDisplayHeight() {
        return 84;
    }

}

