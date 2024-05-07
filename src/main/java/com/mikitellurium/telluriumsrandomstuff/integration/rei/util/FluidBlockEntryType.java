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
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class FluidBlockEntryType implements EntryDefinition<FluidStack> {

    public static EntryType<FluidStack> TYPE = EntryType.deferred(FastLoc.modLoc("fluid_block"));

    private final EntryRenderer<FluidStack> renderer;

    public FluidBlockEntryType() {
        this.renderer = new FluidBlockRenderer();
    }

    @Override
    public Class<FluidStack> getValueType() {
        return FluidStack.class;
    }

    @Override
    public EntryType<FluidStack> getType() {
        return TYPE;
    }

    @Override
    public EntryRenderer<FluidStack> getRenderer() {
        return this.renderer;
    }

    @Override
    public @Nullable ResourceLocation getIdentifier(EntryStack<FluidStack> entryStack, FluidStack fluidStack) {
        return ForgeRegistries.FLUIDS.getKey(fluidStack.getFluid());
    }

    @Override
    public boolean isEmpty(EntryStack<FluidStack> entryStack, FluidStack fluidStack) {
        return fluidStack.isEmpty();
    }

    @Override
    public FluidStack copy(EntryStack<FluidStack> entryStack, FluidStack fluidStack) {
        return new FluidStack(fluidStack.getFluid(), fluidStack.getAmount());
    }

    @Override
    public FluidStack normalize(EntryStack<FluidStack> entryStack, FluidStack fluidStack) {
        return this.copy(entryStack, fluidStack);
    }

    @Override
    public FluidStack wildcard(EntryStack<FluidStack> entryStack, FluidStack fluidStack) {
        return this.copy(entryStack, fluidStack);
    }

    @Override
    public @Nullable ItemStack cheatsAs(EntryStack<FluidStack> entry, FluidStack value) {
        return value.getFluid().getBucket().getDefaultInstance();
    }

    @Override
    public long hash(EntryStack<FluidStack> entryStack, FluidStack fluidStack, ComparisonContext comparisonContext) {
        return fluidStack.hashCode();
    }

    @Override
    public boolean equals(FluidStack fluidStack, FluidStack fluidStack1, ComparisonContext comparisonContext) {
        return fluidStack.getFluid() == fluidStack1.getFluid();
    }

    @Override
    public @Nullable EntrySerializer<FluidStack> getSerializer() {
        return null;
    }

    @Override
    public Component asFormattedText(EntryStack<FluidStack> entryStack, FluidStack fluidStack) {
        return Component.translatable(fluidStack.getFluid().getFluidType().getDescriptionId());
    }

    @Override
    public Stream<? extends TagKey<?>> getTagsFor(EntryStack<FluidStack> entryStack, FluidStack fluidStack) {
        return fluidStack.getFluid().defaultFluidState().getTags();
    }

}
