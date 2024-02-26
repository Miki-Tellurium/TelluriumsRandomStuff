package com.mikitellurium.telluriumsrandomstuff.common.recipe;

import com.google.gson.JsonObject;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import com.mikitellurium.telluriumsrandomstuff.util.RecipeHelper;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class CompactingRecipe extends TelluriumRecipe {

    private final int recipeCost;

    public CompactingRecipe(ResourceLocation id, ItemStack output, Ingredient ingredient, int recipeCost) {
        super(id, output, ingredient);
        this.recipeCost = recipeCost;
    }

    @Override
    public boolean matches(Container container, Level level) {
        if(level.isClientSide()) {
            return false;
        }
        ItemStack input = container.getItem(0);
        return this.getIngredient().test(input) && input.getCount() >= this.getMatchingItemCount(input);
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

    public static class Type implements RecipeType<CompactingRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "compacting";
    }

    public static class Serializer implements RecipeSerializer<CompactingRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = FastLoc.modLoc("compacting");

        @Override
        public CompactingRecipe fromJson(ResourceLocation id, JsonObject recipe) {
            RecipeHelper.validateJsonElement(recipe, "ingredient", "result", "cost");
            Ingredient ingredient = RecipeHelper.ingredientFromJson(recipe.get("ingredient"));
            ItemStack result = RecipeHelper.itemStackFromJson(recipe, "result");
            int count = GsonHelper.getAsInt(recipe, "count", 1);
            result.setCount(count);
            int recipeCost = GsonHelper.getAsInt(recipe, "cost");

            return new CompactingRecipe(id, result, ingredient, recipeCost);
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
            recipe.getIngredient().toNetwork(buf);
            buf.writeItemStack(recipe.getResultItem(RegistryAccess.EMPTY), false);
            buf.writeInt(recipe.getRecipeCost());
        }
    }

}