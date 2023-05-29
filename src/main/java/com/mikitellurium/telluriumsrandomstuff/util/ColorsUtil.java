package com.mikitellurium.telluriumsrandomstuff.util;

import com.mikitellurium.telluriumsrandomstuff.block.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;

import java.awt.*;

public class ColorsUtil {

    private static final int opalBaseColor = FastColor.ABGR32.color(255, 180, 210, 210);
    private static final int opaliumBaseColor = FastColor.ABGR32.color(255, 170, 255, 255);
    private static final int blank = 0xFFFFFF;

    public static int getMaterialColor(ItemStack stack, int tintIndex, int lightLevel) {
        if (ColorsUtil.isCrystal(stack)) {
            return tintIndex == 0 ? ColorsUtil.getRainbowColor(lightLevel, 0.75f, 1.0f, true) : blank;
        }

        return ColorsUtil.getRainbowColor(lightLevel, 0.5f, 0.9f, false);
    }

    // test
    public static int getRainbowColor(int lightLevel, float saturation, float brightness, boolean isMetal) {
        // Return base color if lightLevel is 0
        if (lightLevel == 0) {
            return isMetal ? opaliumBaseColor : opalBaseColor;
        }

        int inverted = 15 - lightLevel;
        float hue = ((float) inverted / 15) * 0.575f; // Adjust the multiplier to control the spectrum range

        Color color = Color.getHSBColor(hue, saturation, brightness);
        return color.getRGB();
    }

    public static int getHighestLightLevel(BlockAndTintGetter level, BlockPos pos) {
        int highestLightLevel = 0;

        for (Direction direction : Direction.values()) {
            BlockPos sidePos = pos.relative(direction);
            BlockState adjacentBlock = level.getBlockState(sidePos);
            if (adjacentBlock.isFaceSturdy(level, pos, direction.getOpposite()) && adjacentBlock.isViewBlocking(level, pos)) {
                continue; // If this face face is obstructed skip this direction
            }

            int lightLevel = level.getBrightness(LightLayer.BLOCK, sidePos);

            if (lightLevel > highestLightLevel) {
                highestLightLevel = lightLevel;
            }
        }

        return highestLightLevel;
    }

    /*
    public static int getLegacyRainbowColor(BlockPos pos, float saturation, float brightness) {
        int waveSize = 75; // How big are the color waves

        Color color = Color.getHSBColor(
                (((pos.getX() + pos.getZ() + pos.getY() * 1.05f)) / waveSize) % 1.0f,
                saturation, brightness);
        return color.getRGB();
    }*/

    public static boolean isCrystal(ItemStack itemStack) {
        return  itemStack.is(ModItems.RAW_OPAL_CRYSTAL.get()) ||
                itemStack.is(ModBlocks.RAW_OPAL_CRYSTAL_BLOCK.get().asItem()) ||
                itemStack.is(ModItems.OPAL_CRYSTAL.get()) ||
                itemStack.is(ModBlocks.OPAL_CRYSTAL_BLOCK.get().asItem()) ||
                itemStack.is(ModItems.OPALIUM_SWORD.get()) ||
                itemStack.is(ModItems.OPALIUM_SHOVEL.get()) ||
                itemStack.is(ModItems.OPALIUM_PICKAXE.get()) ||
                itemStack.is(ModItems.OPALIUM_AXE.get()) ||
                itemStack.is(ModItems.OPALIUM_HOE.get());
    }

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
