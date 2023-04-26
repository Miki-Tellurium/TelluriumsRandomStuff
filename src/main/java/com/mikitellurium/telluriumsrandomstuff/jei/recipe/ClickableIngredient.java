package com.mikitellurium.telluriumsrandomstuff.jei.recipe;

import mezz.jei.api.ingredients.ITypedIngredient;
import mezz.jei.api.runtime.IClickableIngredient;
import net.minecraft.client.renderer.Rect2i;

public class ClickableIngredient<T> implements IClickableIngredient<T> {
    private final ITypedIngredient<T> typedIngredient;
    private final Rect2i area;

    public ClickableIngredient(ITypedIngredient<T> typedIngredient, Rect2i area) {
        this.typedIngredient = typedIngredient;
        this.area = area;
    }

    public ITypedIngredient<T> getTypedIngredient() {
        return this.typedIngredient;
    }

    public Rect2i getArea() {
        return this.area;
    }
}
