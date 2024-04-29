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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class BlockStateEntryType implements EntryDefinition<BlockState> {

    public static EntryType<BlockState> BLOCK_STATE = EntryType.deferred(FastLoc.modLoc("block_state"));

    private final EntryRenderer<BlockState> renderer;

    public BlockStateEntryType() {
        this.renderer = new BlockStateRenderer();
    }

    @Override
    public Class<BlockState> getValueType() {
        return BlockState.class;
    }

    @Override
    public EntryType<BlockState> getType() {
        return BLOCK_STATE;
    }

    @Override
    public EntryRenderer<BlockState> getRenderer() {
        return this.renderer;
    }

    @Override
    public @Nullable ResourceLocation getIdentifier(EntryStack<BlockState> entryStack, BlockState blockState) {
        return ForgeRegistries.BLOCKS.getKey(blockState.getBlock());
    }

    @Override
    public boolean isEmpty(EntryStack<BlockState> entryStack, BlockState blockState) {
        return false;
    }

    @Override
    public BlockState copy(EntryStack<BlockState> entryStack, BlockState blockState) {
        return blockState.getBlock().defaultBlockState();
    }

    @Override
    public BlockState normalize(EntryStack<BlockState> entryStack, BlockState blockState) {
        return this.copy(entryStack, blockState);
    }

    @Override
    public BlockState wildcard(EntryStack<BlockState> entryStack, BlockState blockState) {
        return this.copy(entryStack, blockState);
    }

    @Override
    public @Nullable ItemStack cheatsAs(EntryStack<BlockState> entry, BlockState value) {
        return value.getBlock().asItem().getDefaultInstance();
    }

    @Override
    public long hash(EntryStack<BlockState> entryStack, BlockState blockState, ComparisonContext comparisonContext) {
        return blockState.hashCode();
    }

    @Override
    public boolean equals(BlockState blockState, BlockState blockState1, ComparisonContext comparisonContext) {
        return blockState.getBlock() == blockState1.getBlock();
    }

    @Override
    public @Nullable EntrySerializer<BlockState> getSerializer() {
        return null;
    }

    @Override
    public Component asFormattedText(EntryStack<BlockState> entryStack, BlockState blockState) {
        return Component.translatable(blockState.getBlock().getDescriptionId());
    }

    @Override
    public Stream<? extends TagKey<?>> getTagsFor(EntryStack<BlockState> entryStack, BlockState blockState) {
        return blockState.getTags();
    }

}
