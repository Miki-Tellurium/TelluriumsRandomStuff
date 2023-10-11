package com.mikitellurium.telluriumsrandomstuff.common.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.util.RecipeHelper;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class SoulInfusionRecipe implements Recipe<SimpleContainer> {

    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> ingredients;

    public SoulInfusionRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> ingredients) {
        this.id = id;
        this.output = output;
        this.ingredients = ingredients;
    }

    @Override
    public boolean matches(SimpleContainer container, Level level) {
        if(level.isClientSide()) {
            return false;
        }

        for (int i = 0; i < container.getContainerSize(); i++) {
            if (!ingredients.get(i).test(container.getItem(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public ItemStack assemble(SimpleContainer container, RegistryAccess registryAccess) {
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
        public static final ResourceLocation ID =
                new ResourceLocation(TelluriumsRandomStuffMod.MOD_ID, "soul_infusion");

        @Override
        public SoulInfusionRecipe fromJson(ResourceLocation recipeId, JsonObject serializedRecipe) {
            Ingredient ingredient = Ingredient.of(RecipeHelper.stackFromJson(serializedRecipe, "ingredient"));
            Ingredient catalystIngredient = Ingredient.of(RecipeHelper.stackFromJson(serializedRecipe, "catalyst"));
            ItemStack result = RecipeHelper.stackFromJson(serializedRecipe, "result");

            return new SoulInfusionRecipe(recipeId, result, NonNullList.of(Ingredient.EMPTY, ingredient, catalystIngredient));
        }

        @Override
        public @Nullable SoulInfusionRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            NonNullList<Ingredient> ingredients = NonNullList.create();
            ingredients.replaceAll(ignored -> Ingredient.fromNetwork(buf));
            ItemStack output = buf.readItem();

            return new SoulInfusionRecipe(id, output, ingredients);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, SoulInfusionRecipe recipe) {
            for (Ingredient ingredient : recipe.getIngredients()) {
                ingredient.toNetwork(buf);
            }
            buf.writeItemStack(recipe.getResultItem(RegistryAccess.EMPTY), false);
        }
    }

}