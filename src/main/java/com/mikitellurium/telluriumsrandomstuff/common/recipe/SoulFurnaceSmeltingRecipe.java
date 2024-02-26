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
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

public class SoulFurnaceSmeltingRecipe extends TelluriumRecipe {

    public SoulFurnaceSmeltingRecipe(ResourceLocation id, ItemStack output, Ingredient ingredient) {
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

    public static class Type implements RecipeType<SoulFurnaceSmeltingRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "soul_furnace_smelting";
    }

    public static class Serializer implements RecipeSerializer<SoulFurnaceSmeltingRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = FastLoc.modLoc("soul_furnace_smelting");

        @Override
        public SoulFurnaceSmeltingRecipe fromJson(ResourceLocation recipeId, JsonObject recipe) {
            RecipeHelper.validateJsonElement(recipe, "ingredient", "result");

            Ingredient ingredient = RecipeHelper.ingredientFromJson(recipe.get("ingredient"));
            ItemStack output = RecipeHelper.itemStackFromJson(recipe, "result");

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