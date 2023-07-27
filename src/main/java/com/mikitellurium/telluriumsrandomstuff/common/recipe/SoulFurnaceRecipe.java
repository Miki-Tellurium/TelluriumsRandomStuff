package com.mikitellurium.telluriumsrandomstuff.common.recipe;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
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

public class SoulFurnaceRecipe implements Recipe<SimpleContainer> {

    private final ResourceLocation id;
    private final ItemStack output;
    private final Ingredient ingredient;
    private final int recipeCost;

    public SoulFurnaceRecipe(ResourceLocation id, ItemStack output, Ingredient ingredient, int recipeCost) {
        this.id = id;
        this.output = output;
        this.ingredient = ingredient;
        this.recipeCost = recipeCost;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if(pLevel.isClientSide()) {
            return false;
        }

        return ingredient.test(pContainer.getItem(0));
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(ingredient);
        return list;
    }

    @Override
    public ItemStack assemble(SimpleContainer container, RegistryAccess registryAccess) {
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return output;
    }

    public int getRecipeCost() {
        return recipeCost;
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

    public static class Type implements RecipeType<SoulFurnaceRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "soul_furnace_smelting";
    }

    public static class Serializer implements RecipeSerializer<SoulFurnaceRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(TelluriumsRandomStuffMod.MOD_ID, "soul_furnace_smelting");

        @Override
        public SoulFurnaceRecipe fromJson(ResourceLocation recipeId, JsonObject serializedRecipe) {
            JsonObject ingredientJson = GsonHelper.getAsJsonObject(serializedRecipe, "ingredient");
            Ingredient ingredient = Ingredient.of(CraftingHelper.getItemStack(ingredientJson, true, true));

            if (!serializedRecipe.has("result")) throw new JsonSyntaxException("Missing result, expected to find a string or object");
            ItemStack output;
            if (serializedRecipe.get("result").isJsonObject()) {
                JsonObject resultJson = GsonHelper.getAsJsonObject(serializedRecipe, "result");
                output = ShapedRecipe.itemStackFromJson(resultJson);
            } else {
                String result = GsonHelper.getAsString(serializedRecipe, "result");
                ResourceLocation resourcelocation = new ResourceLocation(result);
                output = new ItemStack(ForgeRegistries.ITEMS.getDelegateOrThrow(resourcelocation));
            }
            int recipeCost = GsonHelper.getAsInt(serializedRecipe, "cost");

            return new SoulFurnaceRecipe(recipeId, output, ingredient, recipeCost);
        }

        @Override
        public @Nullable SoulFurnaceRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            Ingredient ingredient = Ingredient.fromNetwork(buf);
            ItemStack output = buf.readItem();
            int recipeCost = buf.readInt();

            return new SoulFurnaceRecipe(id, output, ingredient, recipeCost);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, SoulFurnaceRecipe recipe) {
            for (Ingredient ing : recipe.getIngredients()) {
                ing.toNetwork(buf);
            }
            buf.writeItemStack(recipe.getResultItem(RegistryAccess.EMPTY), false);
            buf.writeInt(recipe.getRecipeCost());
        }
    }

}