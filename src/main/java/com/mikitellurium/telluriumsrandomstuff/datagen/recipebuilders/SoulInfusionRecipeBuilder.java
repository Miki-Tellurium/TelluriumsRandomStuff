package com.mikitellurium.telluriumsrandomstuff.datagen.recipebuilders;

import com.google.gson.JsonObject;
import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
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

public class SoulInfusionRecipeBuilder implements RecipeBuilder {

    private final Item result;
    private final Ingredient ingredient;
    private final Ingredient catalyst;
    private final int recipeCost;

    private SoulInfusionRecipeBuilder(Ingredient ingredient, Ingredient catalyst, ItemLike result, int recipeCost) {
        this.result = result.asItem();
        this.ingredient = ingredient;
        this.catalyst = catalyst;
        this.recipeCost = recipeCost;
    }

    public static SoulInfusionRecipeBuilder addRecipe(Ingredient ingredient, Ingredient catalyst, ItemLike result,
                                                      int recipeCost) {
        return new SoulInfusionRecipeBuilder(ingredient, catalyst, result, recipeCost);
    }

    @Override
    public SoulInfusionRecipeBuilder unlockedBy(String name, CriterionTriggerInstance criterion) {
        // No advancement
        return this;
    }

    @Override
    public SoulInfusionRecipeBuilder group(@Nullable String group) {
        // No group
        return this;
    }

    @Override
    public Item getResult() {
        return this.result;
    }

    @Override
    public void save(Consumer<FinishedRecipe> consumer, ResourceLocation resourceLocation) {
        consumer.accept(new Result(resourceLocation, this.ingredient, this.catalyst, this.result, this.recipeCost));
    }

    @Override
    public void save(Consumer<FinishedRecipe> consumer, String id) {
        this.save(consumer, new ResourceLocation(TelluriumsRandomStuffMod.MOD_ID, id));
    }

    private static class Result implements FinishedRecipe {
        private final ResourceLocation resourceLocation;
        private final Ingredient ingredient;
        private final Ingredient catalyst;
        private final Item result;
        private final int recipeCost;

        public Result(ResourceLocation resourceLocation, Ingredient ingredient, Ingredient catalyst, Item result,
                      int recipeCost) {
            this.resourceLocation = resourceLocation;
            this.ingredient = ingredient;
            this.catalyst = catalyst;
            this.result = result;
            this.recipeCost = recipeCost;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            json.add("ingredient", this.ingredient.toJson());
            json.add("catalyst", this.catalyst.toJson());
            json.addProperty("result", ForgeRegistries.ITEMS.getKey(this.result).toString());
            json.addProperty("cost", this.recipeCost);
        }

        @Override
        public ResourceLocation getId() {
            return this.resourceLocation;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return ModRecipeSerializers.SOUL_INFUSION_SERIALIZER.get();
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
