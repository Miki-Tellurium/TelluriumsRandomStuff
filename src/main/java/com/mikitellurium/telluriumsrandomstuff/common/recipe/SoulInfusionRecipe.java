package com.mikitellurium.telluriumsrandomstuff.common.recipe;

import com.google.gson.JsonObject;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import com.mikitellurium.telluriumsrandomstuff.util.RecipeHelper;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class SoulInfusionRecipe implements Recipe<Container> {

    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> ingredients;
    private final int recipeCost;

    public SoulInfusionRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> ingredients, int recipeCost) {
        this.id = id;
        this.output = output;
        this.ingredients = ingredients;
        this.recipeCost = recipeCost;
    }

    @Override
    public boolean matches(Container container, Level level) {
        if(level.isClientSide()) {
            return false;
        }

        boolean matches = true;
        for (Ingredient ingredient : ingredients) {

            boolean ingredientIsPresent = false;
            for (int i = 0; i < container.getContainerSize(); i++) {
                if (ingredient.test(container.getItem(i))) {
                    ingredientIsPresent = true; // If the ingredient is present in any slot return true
                }
            }

            if (!ingredientIsPresent) {
                matches = false; // If an ingredient is not present return false
            }

        }

        return matches;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return ingredients;
    }

    public int getRecipeCost() {
        return recipeCost;
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return output;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<SoulInfusionRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "soul_infusion";
    }

    public static class Serializer implements RecipeSerializer<SoulInfusionRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = FastLoc.modLoc("soul_infusion");

        @Override
        public SoulInfusionRecipe fromJson(ResourceLocation recipeId, JsonObject serializedRecipe) {
            Ingredient ingredient = Ingredient.of(RecipeHelper.itemStackFromJson(serializedRecipe, "ingredient"));
            Ingredient catalystIngredient = Ingredient.of(RecipeHelper.itemStackFromJson(serializedRecipe, "catalyst"));
            ItemStack result = RecipeHelper.itemStackFromJson(serializedRecipe, "result");
            int recipeCost = GsonHelper.getAsInt(serializedRecipe, "cost");

            return new SoulInfusionRecipe(recipeId, result,
                    NonNullList.of(Ingredient.EMPTY, ingredient, catalystIngredient), recipeCost);
        }

        @Override
        public @Nullable SoulInfusionRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            NonNullList<Ingredient> ingredients = NonNullList.withSize(2, Ingredient.EMPTY);
            ingredients.replaceAll(ignored -> Ingredient.fromNetwork(buf));
            ItemStack output = buf.readItem();
            int recipeCost = buf.readInt();

            return new SoulInfusionRecipe(id, output, ingredients, recipeCost);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, SoulInfusionRecipe recipe) {
            for (Ingredient ingredient : recipe.getIngredients()) {
                ingredient.toNetwork(buf);
            }
            buf.writeItemStack(recipe.getResultItem(RegistryAccess.EMPTY), false);
            buf.writeInt(recipe.getRecipeCost());
        }
    }

}