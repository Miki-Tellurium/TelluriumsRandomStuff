package com.mikitellurium.telluriumsrandomstuff.datagen.recipebuilders;

import com.google.gson.JsonObject;
import net.minecraft.data.recipes.CraftingRecipeBuilder;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.function.Consumer;

public class ModdedSpecialRecipeBuilder extends CraftingRecipeBuilder {

    private final String modId;
    private final RecipeSerializer<?> serializer;

    public ModdedSpecialRecipeBuilder(String modId, RecipeSerializer<?> serializer) {
        this.modId = modId;
        this.serializer = serializer;
    }

    public static ModdedSpecialRecipeBuilder special(String modId, RecipeSerializer<? extends CraftingRecipe> serializer) {
        return new ModdedSpecialRecipeBuilder(modId, serializer);
    }

    public void save(Consumer<FinishedRecipe> consumer, final String recipeId) {
        consumer.accept(new CraftingRecipeBuilder.CraftingResult(CraftingBookCategory.MISC) {
            public RecipeSerializer<?> getType() {
                return serializer;
            }

            public ResourceLocation getId() {
                return new ResourceLocation(modId, recipeId);
            }

            public JsonObject serializeAdvancement() {
                return null;
            }

            public ResourceLocation getAdvancementId() {
                return new ResourceLocation("");
            }
        });
    }

}
