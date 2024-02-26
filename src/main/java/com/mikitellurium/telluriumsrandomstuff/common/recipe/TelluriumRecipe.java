package com.mikitellurium.telluriumsrandomstuff.common.recipe;

import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public abstract class TelluriumRecipe implements Recipe<Container> {

    private final ResourceLocation id;
    private final ItemStack output;
    private final Ingredient ingredient;

    protected TelluriumRecipe(ResourceLocation id, ItemStack output, Ingredient ingredient) {
        this.id = id;
        this.output = output;
        this.ingredient = ingredient;
    }

    public Ingredient getIngredient() {
        return this.ingredient;
    }

    public int getMatchingItemCount(ItemStack input) {
        for (int i = 0; i < this.ingredient.getItems().length; i++) {
            ItemStack ingredientItem = ingredient.getItems()[i];
            if (ingredientItem.getItem() == input.getItem()) {
                return ingredientItem.getCount();
            }
        }
        return 0;
    }

    @Override
    public abstract boolean matches(Container pContainer, Level pLevel);

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(Ingredient.EMPTY, this.ingredient);
    }

    @Override
    public ItemStack assemble(Container pContainer, RegistryAccess pRegistryAccess) {
        return this.output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return this.output;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public abstract RecipeSerializer<?> getSerializer();

    @Override
    public abstract RecipeType<?> getType();

}
