package com.mikitellurium.telluriumsrandomstuff.integration.jei.helper;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.integration.jei.JeiIntegration;
import mezz.jei.api.ingredients.IIngredientHelper;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.jetbrains.annotations.Nullable;

public class BlockIngredientHelper implements IIngredientHelper<Block> {

    @Override
    public IIngredientType<Block> getIngredientType() {
        return JeiIntegration.BLOCK;
    }

    @Override
    public String getDisplayName(Block ingredient) {
        return ingredient.getName().getString();
    }

    @Override
    public String getUniqueId(Block ingredient, UidContext context) {
        return ingredient.getDescriptionId();
    }

    @Override
    public ResourceLocation getResourceLocation(Block ingredient) {
        return new ResourceLocation(TelluriumsRandomStuffMod.MOD_ID, ingredient.getDescriptionId());
    }

    @Override
    public Block copyIngredient(Block ingredient) {
        return new Block(BlockBehaviour.Properties.copy(ingredient));
    }

    @Override
    public String getErrorInfo(@Nullable Block ingredient) {
        if (ingredient == null) {
            return "debug ingredient: null";
        }
        return getDisplayName(ingredient);
    }

}
