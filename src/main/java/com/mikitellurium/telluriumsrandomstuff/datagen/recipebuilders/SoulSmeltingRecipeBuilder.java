package com.mikitellurium.telluriumsrandomstuff.datagen.recipebuilders;

import com.google.gson.JsonObject;
import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.common.recipe.SoulFurnaceRecipe;
import com.mikitellurium.telluriumsrandomstuff.registry.ModRecipeSerializers;
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

public class SoulSmeltingRecipeBuilder implements RecipeBuilder {

    private final Item result;
    private final Ingredient ingredient;
    private final int extraCost;

    private SoulSmeltingRecipeBuilder(Ingredient ingredient, ItemLike result, int extraCost) {
        this.result = result.asItem();
        this.ingredient = ingredient;
        this.extraCost = extraCost;
    }

    public static SoulSmeltingRecipeBuilder addRecipe(Ingredient ingredient, ItemLike result, int extraCost) {
        return new SoulSmeltingRecipeBuilder(ingredient, result, extraCost);
    }

    @Override
    public SoulSmeltingRecipeBuilder unlockedBy(String name, CriterionTriggerInstance criterion) {
        // No advancement
        return this;
    }

    @Override
    public SoulSmeltingRecipeBuilder group(@Nullable String group) {
        // No group
        return this;
    }

    @Override
    public Item getResult() {
        return this.result;
    }

    @Override
    public void save(Consumer<FinishedRecipe> consumer, ResourceLocation resourceLocation) {
        consumer.accept(new Result(resourceLocation, this.ingredient, this.result, this.extraCost));
    }

    @Override
    public void save(Consumer<FinishedRecipe> consumer, String id) {
        this.save(consumer, new ResourceLocation(TelluriumsRandomStuffMod.MOD_ID, id));
    }

    private static class Result implements FinishedRecipe {
        private final ResourceLocation resourceLocation;
        private final Ingredient ingredient;
        private final Item result;
        private final int extraCost;

        public Result(ResourceLocation resourceLocation, Ingredient ingredient, Item result, int extraCost){
            this.resourceLocation = resourceLocation;
            this.ingredient = ingredient;
            this.result = result;
            this.extraCost = extraCost;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            json.add("ingredient", this.ingredient.toJson());
            json.addProperty("cost", this.extraCost);
            json.addProperty("result", ForgeRegistries.ITEMS.getKey(this.result).toString());
        }

        @Override
        public ResourceLocation getId() {
            return this.resourceLocation;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return ModRecipeSerializers.SOUL_FURNACE_SMELTING_SERIALIZER.get();
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
