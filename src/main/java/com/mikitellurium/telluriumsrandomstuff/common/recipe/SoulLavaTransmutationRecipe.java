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

public class SoulLavaTransmutationRecipe extends TelluriumRecipe {

    public SoulLavaTransmutationRecipe(ResourceLocation id, ItemStack output, Ingredient ingredient) {
        super(id, output, ingredient);
    }

    @Override
    public boolean matches(Container container, Level level) {
        if(level.isClientSide()) {
            return false;
        }

        return this.getIngredient().test(container.getItem(0));
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<SoulLavaTransmutationRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "soul_lava_transmutation";
    }

    public static class Serializer implements RecipeSerializer<SoulLavaTransmutationRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = FastLoc.modLoc("soul_lava_transmutation");

        @Override
        public SoulLavaTransmutationRecipe fromJson(ResourceLocation recipeId, JsonObject serializedRecipe) {
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

            return new SoulLavaTransmutationRecipe(recipeId, output, ingredient);
        }

        @Override
        public @Nullable SoulLavaTransmutationRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            Ingredient ingredient = Ingredient.fromNetwork(buf);
            ItemStack output = buf.readItem();

            return new SoulLavaTransmutationRecipe(id, output, ingredient);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, SoulLavaTransmutationRecipe recipe) {
            for (Ingredient ing : recipe.getIngredients()) {
                ing.toNetwork(buf);
            }
            buf.writeItemStack(recipe.getResultItem(RegistryAccess.EMPTY), false);
        }
    }

}