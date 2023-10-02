package com.mikitellurium.telluriumsrandomstuff.datagen.recipebuilders;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import com.mikitellurium.telluriumsrandomstuff.util.RecipeUtils;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StainedGlassBlock;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class LavaGooglesRecipeBuilder implements RecipeBuilder {

    private final Block glassBlock;
    private final List<String> rows = Lists.newArrayList();
    private final Map<Character, Ingredient> key = Maps.newLinkedHashMap();
    private final Advancement.Builder advancement = Advancement.Builder.recipeAdvancement();
    private final InventoryChangeTrigger.TriggerInstance unlockCriterion =
            new InventoryChangeTrigger.TriggerInstance(ContextAwarePredicate.ANY, MinMaxBounds.Ints.ANY,
                    MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY,
                    new ItemPredicate[] {(ItemPredicate.Builder.item().of(ModItems.LAVA_GOOGLES.get()).build())});

    public LavaGooglesRecipeBuilder(Block glassBlock) {
        if (!(glassBlock instanceof StainedGlassBlock)) {
            throw new IllegalArgumentException("Block has to be a stained glass block");
        }
        this.glassBlock = glassBlock;
        this.advancement.addCriterion("has_lava_googles", unlockCriterion);
            this.pattern("sLs")
                .pattern("#X#")
                .define('X', this.glassBlock.asItem())
                .define('#', ModItems.AMETHYST_LENS.get())
                .define('s', Items.STRING)
                .define('L', Items.LEATHER);
    }

    public static LavaGooglesRecipeBuilder googles(Block glassBlock) {
        return new LavaGooglesRecipeBuilder(glassBlock);

    }

    public LavaGooglesRecipeBuilder define(Character symbol, ItemLike itemLike) {
        this.define(symbol, Ingredient.of(itemLike));
        return this;
    }

    public LavaGooglesRecipeBuilder define(Character symbol, Ingredient ingredient) {
        if (this.key.containsKey(symbol)) {
            throw new IllegalArgumentException("Symbol '" + symbol + "' is already defined!");
        } else if (symbol == ' ') {
            throw new IllegalArgumentException("Symbol ' ' (whitespace) is reserved and cannot be defined");
        } else {
            this.key.put(symbol, ingredient);
            return this;
        }
    }

    public LavaGooglesRecipeBuilder pattern(String pattern) {
        if (!this.rows.isEmpty() && pattern.length() != this.rows.get(0).length()) {
            throw new IllegalArgumentException("Pattern must be the same width on every line!");
        } else {
            this.rows.add(pattern);
            return this;
        }
    }

    @Override
    public RecipeBuilder unlockedBy(String criterionName, CriterionTriggerInstance criterionTrigger) {
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String groupName) {
        return this;
    }

    @Override
    public Item getResult() {
        return ModItems.LAVA_GOOGLES.get();
    }

    @Override
    public void save(Consumer<FinishedRecipe> recipeConsumer, ResourceLocation resourceLocation) {
        recipeConsumer.accept(new Result(resourceLocation, RecipeUtils.getGlassColor(this.glassBlock),
                this.rows, this.key, this.advancement));
    }

    @Override
    public void save(Consumer<FinishedRecipe> recipeConsumer, String recipeId) {
        this.save(recipeConsumer, new ResourceLocation(TelluriumsRandomStuffMod.MOD_ID, recipeId));
    }

    protected static class Result implements FinishedRecipe {
        private final ResourceLocation resourceLocation;
        private final Item result = ModItems.LAVA_GOOGLES.get();
        private final DyeColor color;
        private final String category = "equipment";
        private final String group = "lava_googles";
        private final List<String> pattern;
        private final Map<Character, Ingredient> key;
        private final Advancement.Builder advancement;
        private final boolean showNotification = true;

        public Result(ResourceLocation resourceLocation, DyeColor color, List<String> pattern,
                      Map<Character, Ingredient> key, Advancement.Builder advancement) {
            this.resourceLocation = resourceLocation;
            this.color = color;
            this.pattern = pattern;
            this.key = key;
            this.advancement = advancement;
        }

        public void serializeRecipeData(JsonObject pJson) {
            pJson.addProperty("category", this.category);
            pJson.addProperty("group", this.group);

            JsonArray jsonPattern = new JsonArray();
            for(String s : this.pattern) {
                jsonPattern.add(s);
            }
            pJson.add("pattern", jsonPattern);

            JsonObject jsonKeys = new JsonObject();
            for(Map.Entry<Character, Ingredient> entry : this.key.entrySet()) {
                jsonKeys.add(String.valueOf(entry.getKey()), entry.getValue().toJson());
            }
            pJson.add("key", jsonKeys);

            JsonObject jsonResult = new JsonObject();
            JsonObject jsonNbt = new JsonObject();
            jsonNbt.addProperty("color", this.color.getSerializedName());
            jsonResult.addProperty("type", "minecraft:item_nbt");
            jsonResult.addProperty("item", BuiltInRegistries.ITEM.getKey(this.result).toString());
            jsonResult.add("nbt", jsonNbt);

            pJson.add("result", jsonResult);
            pJson.addProperty("show_notification", this.showNotification);
        }

        @Override
        public ResourceLocation getId() {
            return this.resourceLocation;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return RecipeSerializer.SHAPED_RECIPE;
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return null;
        }

    }

}
