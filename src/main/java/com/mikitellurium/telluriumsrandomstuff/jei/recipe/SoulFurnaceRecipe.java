package com.mikitellurium.telluriumsrandomstuff.jei.recipe;

import com.google.gson.JsonElement;
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
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

public class SoulFurnaceRecipe implements Recipe<SimpleContainer> {

    private final ResourceLocation id;
    private final ItemStack output;
    private final Ingredient ingredient;

    public SoulFurnaceRecipe(ResourceLocation id, ItemStack output, Ingredient ingredient) {
        this.id = id;
        this.output = output;
        this.ingredient = ingredient;
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
        return null;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
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
        public SoulFurnaceRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            JsonElement jsonelement = GsonHelper.isArrayNode(pSerializedRecipe, "ingredient") ?
                    GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredient") : GsonHelper.getAsJsonObject(pSerializedRecipe, "ingredient");
            Ingredient ingredient = Ingredient.fromJson(jsonelement);
            if (!pSerializedRecipe.has("result")) throw new JsonSyntaxException("Missing result, expected to find a string or object");
            ItemStack output;
            if (pSerializedRecipe.get("result").isJsonObject()) {
                output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "result"));
            } else {
                String s1 = GsonHelper.getAsString(pSerializedRecipe, "result");
                ResourceLocation resourcelocation = new ResourceLocation(s1);
                output = new ItemStack(ForgeRegistries.ITEMS.getDelegateOrThrow(resourcelocation));
            }

            return new SoulFurnaceRecipe(pRecipeId, output, ingredient);
        }

        @Override
        public @Nullable SoulFurnaceRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            Ingredient ingredient = Ingredient.fromNetwork(buf);
            ItemStack output = buf.readItem();

            return new SoulFurnaceRecipe(id, output, ingredient);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, SoulFurnaceRecipe recipe) {
            for (Ingredient ing : recipe.getIngredients()) {
                ing.toNetwork(buf);
            }
            buf.writeItemStack(recipe.getResultItem(RegistryAccess.EMPTY), false);
        }
    }

}