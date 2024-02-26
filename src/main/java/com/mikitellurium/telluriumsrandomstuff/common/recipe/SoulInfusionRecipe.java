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

public class SoulInfusionRecipe extends TelluriumRecipe {

    private final Ingredient catalyst;
    private final int recipeCost;

    public SoulInfusionRecipe(ResourceLocation id, ItemStack output, Ingredient ingredient, Ingredient catalyst, int recipeCost) {
        super(id, output, ingredient);
        this.catalyst = catalyst;
        this.recipeCost = recipeCost;
    }

    @Override
    public boolean matches(Container container, Level level) {
        if(level.isClientSide()) {
            return false;
        }

        boolean matches = true;
        for (Ingredient ingredient : this.getIngredients()) {

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
        return NonNullList.of(Ingredient.EMPTY, this.getIngredient(), this.catalyst);
    }

    public Ingredient getCatalyst() {
        return catalyst;
    }

    public int getRecipeCost() {
        return recipeCost;
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
        public SoulInfusionRecipe fromJson(ResourceLocation recipeId, JsonObject recipe) {
            RecipeHelper.validateJsonElement(recipe, "ingredient", "catalyst", "result", "cost");
            Ingredient ingredient = RecipeHelper.ingredientFromJson(recipe.get("ingredient"));
            Ingredient catalyst = RecipeHelper.ingredientFromJson(recipe.get("catalyst"));
            ItemStack result = RecipeHelper.itemStackFromJson(recipe, "result");
            int recipeCost = GsonHelper.getAsInt(recipe, "cost");

            return new SoulInfusionRecipe(recipeId, result, ingredient, catalyst, recipeCost);
        }

        @Override
        public @Nullable SoulInfusionRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            Ingredient ingredient = Ingredient.fromNetwork(buf);
            Ingredient catalyst = Ingredient.fromNetwork(buf);
            ItemStack output = buf.readItem();
            int recipeCost = buf.readInt();

            return new SoulInfusionRecipe(id, output, ingredient, catalyst, recipeCost);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, SoulInfusionRecipe recipe) {
            recipe.getIngredient().toNetwork(buf);
            recipe.getCatalyst().toNetwork(buf);
            buf.writeItemStack(recipe.getResultItem(RegistryAccess.EMPTY), false);
            buf.writeInt(recipe.getRecipeCost());
        }
    }

}