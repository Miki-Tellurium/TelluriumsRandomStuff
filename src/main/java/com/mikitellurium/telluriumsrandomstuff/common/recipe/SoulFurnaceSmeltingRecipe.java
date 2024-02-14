package com.mikitellurium.telluriumsrandomstuff.common.recipe;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

public class SoulFurnaceSmeltingRecipe implements Recipe<Container> {

    private final ResourceLocation id;
    private final ItemStack output;
    private final Ingredient ingredient;

    public SoulFurnaceSmeltingRecipe(ResourceLocation id, ItemStack output, Ingredient ingredient) {
        this.id = id;
        this.output = output;
        this.ingredient = ingredient;
    }

    @Override
    public boolean matches(Container container, Level level) {
        if(level.isClientSide()) {
            return false;
        }

        return ingredient.test(container.getItem(0));
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(Ingredient.EMPTY, ingredient);
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

    public static class Type implements RecipeType<SoulFurnaceSmeltingRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "soul_furnace_smelting";
    }

    public static class Serializer implements RecipeSerializer<SoulFurnaceSmeltingRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = FastLoc.modLoc("soul_furnace_smelting");

        @Override
        public SoulFurnaceSmeltingRecipe fromJson(ResourceLocation recipeId, JsonObject serializedRecipe) {
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

            return new SoulFurnaceSmeltingRecipe(recipeId, output, ingredient);
        }

        @Override
        public @Nullable SoulFurnaceSmeltingRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            Ingredient ingredient = Ingredient.fromNetwork(buf);
            ItemStack output = buf.readItem();

            return new SoulFurnaceSmeltingRecipe(id, output, ingredient);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, SoulFurnaceSmeltingRecipe recipe) {
            for (Ingredient ing : recipe.getIngredients()) {
                ing.toNetwork(buf);
            }
            buf.writeItemStack(recipe.getResultItem(RegistryAccess.EMPTY), false);
        }
    }

}