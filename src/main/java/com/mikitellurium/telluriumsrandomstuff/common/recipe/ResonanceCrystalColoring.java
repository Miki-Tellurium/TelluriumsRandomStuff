package com.mikitellurium.telluriumsrandomstuff.common.recipe;

import com.mikitellurium.telluriumsrandomstuff.common.item.ResonanceCrystalItem;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import com.mikitellurium.telluriumsrandomstuff.registry.ModRecipeSerializers;
import net.minecraft.Util;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags;

import java.util.HashMap;
import java.util.Map;

public class ResonanceCrystalColoring extends CustomRecipe {

    public static final String ID = "resonance_crystal_coloring";
    private static final Map<DyeColor, Item> RESULTS = Util.make(new HashMap<>(), (map) -> {
        map.put(DyeColor.WHITE, ModItems.RESONANCE_CRYSTAL_WHITE.get());
        map.put(DyeColor.ORANGE, ModItems.RESONANCE_CRYSTAL_ORANGE.get());
        map.put(DyeColor.MAGENTA, ModItems.RESONANCE_CRYSTAL_MAGENTA.get());
        map.put(DyeColor.LIGHT_BLUE, ModItems.RESONANCE_CRYSTAL_LIGHT_BLUE.get());
        map.put(DyeColor.YELLOW, ModItems.RESONANCE_CRYSTAL_YELLOW.get());
        map.put(DyeColor.LIME, ModItems.RESONANCE_CRYSTAL_LIME.get());
        map.put(DyeColor.PINK, ModItems.RESONANCE_CRYSTAL_PINK.get());
        map.put(DyeColor.GRAY, ModItems.RESONANCE_CRYSTAL_GRAY.get());
        map.put(DyeColor.LIGHT_GRAY, ModItems.RESONANCE_CRYSTAL_LIGHT_GRAY.get());
        map.put(DyeColor.CYAN, ModItems.RESONANCE_CRYSTAL_CYAN.get());
        map.put(DyeColor.PURPLE, ModItems.RESONANCE_CRYSTAL_PURPLE.get());
        map.put(DyeColor.BLUE, ModItems.RESONANCE_CRYSTAL_BLUE.get());
        map.put(DyeColor.BROWN, ModItems.RESONANCE_CRYSTAL_BROWN.get());
        map.put(DyeColor.GREEN, ModItems.RESONANCE_CRYSTAL_GREEN.get());
        map.put(DyeColor.RED, ModItems.RESONANCE_CRYSTAL_RED.get());
        map.put(DyeColor.BLACK, ModItems.RESONANCE_CRYSTAL_BLACK.get());
    });


    public ResonanceCrystalColoring(ResourceLocation id) {
        super(id, CraftingBookCategory.MISC);
    }

    @Override
    public boolean matches(CraftingContainer container, Level level) {
        int empty = 0;
        ResonanceCrystalItem crystal = null;
        DyeColor dyeColor = null;

        for(int i = 0; i < container.getContainerSize(); ++i) {
            ItemStack itemstack = container.getItem(i);
            if (!itemstack.isEmpty()) {
                if (itemstack.getItem() instanceof ResonanceCrystalItem) {
                    crystal = (ResonanceCrystalItem) itemstack.getItem();
                } else {
                    if (itemstack.is(Tags.Items.DYES)) {
                        dyeColor = DyeColor.getColor(itemstack);
                    } else {
                        return false;
                    }
                }
            } else {
                empty++;
            }
        }
        if (crystal == null || dyeColor == null) {
            return false;
        }

        return empty == container.getContainerSize() - 2 && crystal.getColor() != dyeColor;
    }

    @Override
    public ItemStack assemble(CraftingContainer container, RegistryAccess registryAccess) {
        DyeColor dyeColor = null;
        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack itemStack = container.getItem(i);
            if (itemStack.is(Tags.Items.DYES)) {
                dyeColor = DyeColor.getColor(itemStack);
                break;
            }
        }
        return dyeColor == null ? ItemStack.EMPTY : RESULTS.get(dyeColor).getDefaultInstance();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.RESONANCE_CRYSTAL_COLORING.get();
    }

}
