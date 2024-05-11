package com.mikitellurium.telluriumsrandomstuff.integration.rei.util;

import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import me.shedaniel.rei.api.client.entry.renderer.EntryRenderer;
import me.shedaniel.rei.api.common.entry.EntrySerializer;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.comparison.ComparisonContext;
import me.shedaniel.rei.api.common.entry.type.EntryDefinition;
import me.shedaniel.rei.api.common.entry.type.EntryType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class FluidBlockEntryType implements EntryDefinition<Fluid> {

    public static EntryType<Fluid> TYPE = EntryType.deferred(FastLoc.modLoc("fluid_block"));

    private final EntryRenderer<Fluid> renderer;

    public FluidBlockEntryType() {
        this.renderer = new FluidBlockRenderer();
    }

    @Override
    public Class<Fluid> getValueType() {
        return Fluid.class;
    }

    @Override
    public EntryType<Fluid> getType() {
        return TYPE;
    }

    @Override
    public EntryRenderer<Fluid> getRenderer() {
        return this.renderer;
    }

    @Override
    public @Nullable ResourceLocation getIdentifier(EntryStack<Fluid> entryStack, Fluid fluid) {
        return ForgeRegistries.FLUIDS.getKey(fluid);
    }

    @Override
    public boolean isEmpty(EntryStack<Fluid> entryStack, Fluid fluid) {
        return fluid.getFluidType().isAir();
    }

    @Override
    public Fluid copy(EntryStack<Fluid> entryStack, Fluid fluid) {
        return new FluidStack(fluid, 0).getFluid();
    }

    @Override
    public Fluid normalize(EntryStack<Fluid> entryStack, Fluid fluid) {
        return this.copy(entryStack, fluid);
    }

    @Override
    public Fluid wildcard(EntryStack<Fluid> entryStack, Fluid fluid) {
        return this.copy(entryStack, fluid);
    }

    @Override
    public @Nullable ItemStack cheatsAs(EntryStack<Fluid> entry, Fluid value) {
        return value.getBucket().getDefaultInstance();
    }

    @Override
    public long hash(EntryStack<Fluid> entryStack, Fluid fluidStack, ComparisonContext comparisonContext) {
        return fluidStack.hashCode();
    }

    @Override
    public boolean equals(Fluid fluid, Fluid fluid1, ComparisonContext comparisonContext) {
        return fluid == fluid1;
    }

    @Override
    public @Nullable EntrySerializer<Fluid> getSerializer() {
        return null;
    }

    @Override
    public Component asFormattedText(EntryStack<Fluid> entryStack, Fluid fluid) {
        return Component.translatable(fluid.getFluidType().getDescriptionId());
    }

    @Override
    public Stream<? extends TagKey<?>> getTagsFor(EntryStack<Fluid> entryStack, Fluid fluid) {
        return fluid.defaultFluidState().getTags();
    }

}
