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

import java.util.List;

public class CompactingRecipe implements Recipe<Container> {

    private final ResourceLocation id;
    private final ItemStack output;
    private final Ingredient ingredient;
    private final int recipeCost;

    public CompactingRecipe(ResourceLocation id, ItemStack output, Ingredient ingredient, int recipeCost) {
        this.id = id;
        this.output = output;
        this.ingredient = ingredient;
        this.recipeCost = recipeCost;
    }

    @Override
    public boolean matches(Container container, Level level) {
        if(level.isClientSide()) {
            return false;
        }
        if (container.getContainerSize() < 8) return false;
        boolean matches = true;
        for (int i = 0; i < container.getContainerSize(); i++) {
            if (!this.ingredient.test(container.getItem(i))) {
                matches = false;
            }
        }
        return matches;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(Ingredient.EMPTY, this.ingredient);
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

    public static class Type implements RecipeType<CompactingRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "compacting";
    }

    public static class Serializer implements RecipeSerializer<CompactingRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = FastLoc.modLoc("compacting");
        // todo add exception handling for all recipes
        @Override
        public CompactingRecipe fromJson(ResourceLocation recipeId, JsonObject serializedRecipe) {
            Ingredient ingredient = Ingredient.of(RecipeHelper.stackFromJson(serializedRecipe, "ingredient"));
            ItemStack result = RecipeHelper.stackFromJson(serializedRecipe, "result");
            int count = GsonHelper.getAsInt(serializedRecipe, "count");
            result.setCount(count);
            int recipeCost = GsonHelper.getAsInt(serializedRecipe, "cost");

            return new CompactingRecipe(recipeId, result, ingredient, recipeCost);
        }

        @Override
        public @Nullable CompactingRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            Ingredient ingredient = Ingredient.fromNetwork(buf);
            ItemStack output = buf.readItem();
            int recipeCost = buf.readInt();

            return new CompactingRecipe(id, output, ingredient, recipeCost);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, CompactingRecipe recipe) {
            recipe.ingredient.toNetwork(buf);
            buf.writeItemStack(recipe.getResultItem(RegistryAccess.EMPTY), false);
            buf.writeInt(recipe.getRecipeCost());
        }
    }

}