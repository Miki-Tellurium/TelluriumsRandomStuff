package com.mikitellurium.telluriumsrandomstuff.util;

import com.mikitellurium.telluriumsrandomstuff.block.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;

import java.awt.*;

public class ColorsUtil {

    public static int getOpalRainbowColor(BlockPos pos, float saturation, float brightness) {
        int waveSize = 75; // How big are the color waves

        Color color = Color.getHSBColor(
                (((pos.getX() + pos.getZ() + pos.getY() * 1.05f)) / waveSize) % 1.0f,
                saturation, brightness);
        return color.getRGB();
    }

    public static boolean isMaterialOpalium(ItemStack itemStack) {
        return  itemStack.is(ModItems.RAW_OPALIUM.get()) ||
                itemStack.is(ModBlocks.RAW_OPALIUM_BLOCK.get().asItem()) ||
                itemStack.is(ModItems.OPALIUM_INGOT.get()) ||
                itemStack.is(ModBlocks.OPALIUM_BLOCK.get().asItem()) ||
                itemStack.is(ModItems.OPALIUM_SWORD.get()) ||
                itemStack.is(ModItems.OPALIUM_SHOVEL.get()) ||
                itemStack.is(ModItems.OPALIUM_PICKAXE.get()) ||
                itemStack.is(ModItems.OPALIUM_AXE.get()) ||
                itemStack.is(ModItems.OPALIUM_HOE.get());
    }

    /*
    public static int legacyGetOpalColor(BlockPos pos) {
        Color color = Color.getHSBColor((((
                        pos.getX() + Mth.sin(((float)pos.getY() + (float)pos.getZ()) / 20) +
                                pos.getZ() + Mth.cos(((float)pos.getX() + (float)pos.getY()) / 20)
                                * 10) % 65) / 65) ,
                0.5F, 0.8F);
        return color.getRGB();
    }
    */

    public static float soulRedColor() {
        return 37f / 255f;
    }

    public static float soulGreenColor() {
        return 244f / 255f;
    }

    public static float soulBlueColor() {
        return 255f / 255f;
    }

}
