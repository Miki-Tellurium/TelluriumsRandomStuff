package com.mikitellurium.telluriumsrandomstuff.integration.rei.guielement;

import com.mikitellurium.telluriumsrandomstuff.client.hud.render.GuiFluidRenderer;
import com.mikitellurium.telluriumsrandomstuff.registry.ModFluids;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import me.shedaniel.math.Dimension;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.widgets.BaseWidget;
import me.shedaniel.rei.api.client.gui.widgets.Tooltip;
import me.shedaniel.rei.api.client.gui.widgets.TooltipContext;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SoulLavaTankWidget extends BaseWidget<SoulLavaTankWidget> {

    private final Rectangle bounds;
    private final int capacity;
    private final int amount;

    public SoulLavaTankWidget(int xPos, int yPos, int capacity, int amount) {
        this.bounds = new Rectangle(new Point(xPos, yPos), new Dimension(16, 48));
        this.capacity = capacity;
        this.amount = amount;
    }

    @Override
    public Rectangle getBounds() {
        return this.bounds;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        GuiFluidRenderer.drawBackground(graphics, bounds.x, bounds.y, new FluidStack(ModFluids.SOUL_LAVA_SOURCE.get(), amount),
                capacity, bounds.width, bounds.height);
        graphics.blit(FastLoc.GUI_ELEMENTS_TEXTURE, bounds.x, bounds.y, 72, 0, bounds.width, bounds.height);
    }

    @Override
    public @Nullable Tooltip getTooltip(TooltipContext context) {
        return Tooltip.create(this.getFluidTooltips(context));
    }

    public List<Component> getFluidTooltips(TooltipContext context) {
        List<Component> tooltip = new ArrayList<>();
        Fluid fluid = ModFluids.SOUL_LAVA_SOURCE.get();

        Component displayName = Component.translatable("fluid_type.telluriumsrandomstuff.soul_lava");
        tooltip.add(displayName);

        if (context.getFlag().isAdvanced()) {
            ResourceLocation resourceLocation = ForgeRegistries.FLUIDS.getKey(fluid);
            if (resourceLocation != null) {
                MutableComponent advancedId = Component.literal(resourceLocation.toString())
                        .withStyle(ChatFormatting.DARK_GRAY);
                tooltip.add(advancedId);
            }
        }
        MutableComponent amount = Component.literal(this.amount + "/" + this.capacity + " mB")
                .withStyle(ChatFormatting.GRAY);
        tooltip.add(amount);

        return tooltip;
    }

    @Override
    public List<? extends GuiEventListener> children() {
        return Collections.emptyList();
    }

}
