package com.mikitellurium.telluriumsrandomstuff.common.worldgen.structure.processor;

import com.mikitellurium.telluriumsrandomstuff.registry.ModStructureProcessors;
import com.mikitellurium.telluriumsrandomstuff.test.RandomCounter;
import com.mikitellurium.telluriumsrandomstuff.util.ColorsUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FastColor;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Stream;

public class WriteSignProcessor extends StructureProcessor {

    private static final Codec<WriteSignProcessor> CODEC = RecordCodecBuilder.create((instance) ->
            instance.group(
                    Codec.FLOAT.optionalFieldOf("probability", 1.0F).forGetter((processor) -> processor.probability),
                    Codec.FLOAT.optionalFieldOf("glowing_chance", 0.01F).forGetter((processor) -> processor.glowingChance),
                    Codec.list(ColoredText.TEXT_CODEC).fieldOf("values").forGetter((processor) -> processor.words)
            ).apply(instance, WriteSignProcessor::new));

    public static Codec<WriteSignProcessor> codec() {
        return CODEC;
    }

    private final float probability;
    private final float glowingChance;
    private final List<ColoredText> words;

    public WriteSignProcessor(float probability, float glowingChance, List<ColoredText> words) {
        this.probability = probability;
        this.glowingChance = glowingChance;
        this.words = words;
    }

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo process(LevelReader level, BlockPos offsetPos, BlockPos pos,
                                                        StructureTemplate.StructureBlockInfo blockInfo,
                                                        StructureTemplate.StructureBlockInfo relativeInfo,
                                                        StructurePlaceSettings settings, @Nullable StructureTemplate template) {
        if (relativeInfo.state().getBlock() instanceof SignBlock) {
            RandomSource random = settings.getRandom(pos.offset(relativeInfo.pos()));
            if (random.nextFloat() < probability) {
                if (relativeInfo.nbt() != null) {
                    CompoundTag tag = this.updateSignTag(relativeInfo.nbt(), random);
                    return new StructureTemplate.StructureBlockInfo(relativeInfo.pos(), relativeInfo.state(), tag);
                }
            }
        }

        return relativeInfo;
    }

    private CompoundTag updateSignTag(CompoundTag tag, RandomSource random) {
        boolean isGlowing = random.nextFloat() < this.glowingChance;
        List<Integer> rows = Util.toShuffledList(Stream.of(0, 1, 2, 3), random);
        float chance = 1.0F;
        for (Integer row : rows) {
            if (random.nextFloat() < chance) {
                try {
                    Component component = Util.getRandom(this.words, random).getColoredText(random, isGlowing);
                    CompoundTag textTag = tag.getCompound("front_text");
                    textTag.putBoolean("has_glowing_text", isGlowing);
                    ListTag messagesTag = textTag.getList("messages", Tag.TAG_STRING);
                    StringTag stringTag = StringTag.valueOf(Component.Serializer.toJson(component));
                    messagesTag.set(row, stringTag);
                    tag.putBoolean("is_waxed", true);
                } catch (Exception e) {
                    throw new RuntimeException("Unable to update sign tag");
                }
            }
            chance /= 4;
        }
        return tag;
    }



    @Override
    protected StructureProcessorType<?> getType() {
        return ModStructureProcessors.WRITE_SIGN.get();
    }

    public record ColoredText(String text, String color) {

        private static final Codec<ColoredText> TEXT_CODEC = RecordCodecBuilder.create((instance) ->
                instance.group(
                        Codec.STRING.fieldOf("text").forGetter((coloredText) -> coloredText.text),
                        Codec.STRING.optionalFieldOf("color", "black").forGetter((coloredText) -> coloredText.text)
                ).apply(instance, ColoredText::new));

        private Component getColoredText(RandomSource random, boolean isGlowing) {
            return Component.literal(this.text)
                    .withStyle(Style.EMPTY.withColor(this.getDarkColor(this.getColor(random), isGlowing)));
        }

        private int getColor(RandomSource random) {
            return this.color.equals("random") ?
                    ColorsUtil.getRandomDyeColor(random).getTextColor() : this.parseColor().getTextColor();
        }

        private DyeColor parseColor() {
            return DyeColor.byName(this.color, DyeColor.byId(15));
        }

        private int getDarkColor(int color, boolean isGlowing) {
            if (color == DyeColor.BLACK.getTextColor() && isGlowing) {
                return -988212;
            } else {
                double mul = 0.4D;
                int red = (int) ((double) FastColor.ARGB32.red(color) * mul);
                int green = (int) ((double) FastColor.ARGB32.green(color) * mul);
                int blue = (int) ((double) FastColor.ARGB32.blue(color) * mul);
                return FastColor.ARGB32.color(0, red, green, blue);
            }
        }

    }

}
