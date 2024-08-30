package com.mikitellurium.telluriumsrandomstuff.datagen.recipebuilders;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mikitellurium.telluriumsrandomstuff.registry.ModRecipeSerializers;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class CompactingRecipeBuilder implements RecipeBuilder {

    private final Item result;
    private final int count;
    private final Ingredient ingredient;
    private final int recipeCost;

    private CompactingRecipeBuilder(Ingredient ingredient, ItemLike result, int count, int recipeCost) {
        this.result = result.asItem();
        this.ingredient = ingredient;
        this.count = count;
        this.recipeCost = recipeCost;
    }

    public static CompactingRecipeBuilder addRecipe(Ingredient ingredient, ItemLike result, int count, int recipeCost) {
        return new CompactingRecipeBuilder(ingredient, result, count, recipeCost);
    }

    @Override
    public CompactingRecipeBuilder unlockedBy(String name, CriterionTriggerInstance criterion) {
        // No advancement
        return this;
    }

    @Override
    public CompactingRecipeBuilder group(@Nullable String group) {
        // No group
        return this;
    }

    @Override
    public Item getResult() {
        return this.result;
    }

    @Override
    public void save(Consumer<FinishedRecipe> consumer, ResourceLocation resourceLocation) {
        consumer.accept(new Result(resourceLocation, this.ingredient, this.result, this.count, this.recipeCost));
    }

    @Override
    public void save(Consumer<FinishedRecipe> consumer, String id) {
        this.save(consumer, FastLoc.modLoc(id));
    }

    private static class Result implements FinishedRecipe {
        private final ResourceLocation resourceLocation;
        private final Ingredient ingredient;
        private final Item result;
        private final int count;
        private final int recipeCost;

        public Result(ResourceLocation resourceLocation, Ingredient ingredient, Item result, int count, int recipeCost) {
            this.resourceLocation = resourceLocation;
            this.ingredient = ingredient;
            this.count = count;
            this.result = result;
            this.recipeCost = recipeCost;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            JsonElement ingredient = this.ingredient.toJson();
            if (ingredient.isJsonObject()) {
                ingredient.getAsJsonObject().addProperty("count", this.ingredient.getItems()[0].getCount());
            } else {
                JsonArray elements = ingredient.getAsJsonArray();
                for (int i = 0; i < elements.size(); i++) {
                    elements.get(i).getAsJsonObject().addProperty("count", this.ingredient.getItems()[i].getCount());
                }
            }
            json.add("ingredient", ingredient);
            json.addProperty("count", this.count);
            json.addProperty("result", ForgeRegistries.ITEMS.getKey(this.result).toString());
            json.addProperty("cost", this.recipeCost);
        }

        @Override
        public ResourceLocation getId() {
            return this.resourceLocation;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return ModRecipeSerializers.COMPACTING.get();
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
