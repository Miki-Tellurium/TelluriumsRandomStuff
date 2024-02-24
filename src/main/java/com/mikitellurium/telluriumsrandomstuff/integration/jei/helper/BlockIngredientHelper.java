package com.mikitellurium.telluriumsrandomstuff.integration.jei.helper;

import com.mikitellurium.telluriumsrandomstuff.integration.jei.util.ModIngredientTypes;
import mezz.jei.api.ingredients.IIngredientHelper;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

public class BlockIngredientHelper implements IIngredientHelper<BlockState> {

    @Override
    public IIngredientType<BlockState> getIngredientType() {
        return ModIngredientTypes.BLOCK_STATE;
    }

    @Override
    public String getDisplayName(BlockState ingredient) {
        return ingredient.getBlock().getName().getString();
    }

    @Override
    public String getUniqueId(BlockState ingredient, UidContext context) {
        return ingredient.getBlock().getDescriptionId();
    }

    @Override
    public ResourceLocation getResourceLocation(BlockState ingredient) {
        return ForgeRegistries.BLOCKS.getKey(ingredient.getBlock());
    }

    @Override
    public BlockState copyIngredient(BlockState ingredient) {
        return ingredient.getBlock().defaultBlockState();
    }

    @Override
    public ItemStack getCheatItemStack(BlockState ingredient) {
        return ingredient.getBlock().asItem().getDefaultInstance();
    }

    @Override
    public String getErrorInfo(@Nullable BlockState ingredient) {
        if (ingredient == null) {
            return "debug ingredient: null";
        }
        return getDisplayName(ingredient);
    }

}
