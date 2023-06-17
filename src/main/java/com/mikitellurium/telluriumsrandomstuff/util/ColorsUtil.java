package com.mikitellurium.telluriumsrandomstuff.util;

import com.mikitellurium.telluriumsrandomstuff.tag.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;

import java.awt.*;

public class ColorsUtil {

    private static final int opalBaseColor = FastColor.ABGR32.color(255, 160, 210, 210);
    private static final int opalCrystalBaseColor = FastColor.ABGR32.color(255, 140, 255, 255);
    private static final int blank = 0xFFFFFF;

    public static int getMaterialColor(ItemStack stack, int tintIndex, int lightLevel) {
        if (stack.is(ModTags.Items.OPAL_CRYSTALS)) {
            return tintIndex == 0 ? ColorsUtil.getRainbowColor(lightLevel, 0.75f, 1.0f, true) : blank;
        }

        return ColorsUtil.getRainbowColor(lightLevel, 0.55f, 0.9f, false);
    }

    // test
    public static int getRainbowColor(int lightLevel, float saturation, float brightness, boolean isCrystal) {
        // Return base color if lightLevel is 0
        if (lightLevel == 0) {
            return isCrystal ? opalCrystalBaseColor : opalBaseColor;
        }

        double inverted = 15.5D - lightLevel;
        float hue = ((float) inverted / 15) * 0.575f; // Adjust the multiplier to control the spectrum range

        Color color = Color.getHSBColor(hue, saturation, brightness);
        return color.getRGB();
    }

    public static int getHighestLightLevel(BlockAndTintGetter level, BlockPos pos) {
        int highestLightLevel = 0;

        for (Direction direction : Direction.values()) {
            BlockPos sidePos = pos.relative(direction);
            BlockState adjacentBlock = level.getBlockState(sidePos);

            int lightLevel;
            if (adjacentBlock.getLightEmission() > 0) {
                lightLevel = adjacentBlock.getLightEmission();
            } else if (adjacentBlock.isFaceSturdy(level, pos, direction.getOpposite()) && adjacentBlock.isViewBlocking(level, pos)) {
                continue; // If this face face is obstructed skip this direction
            } else {
                lightLevel = level.getBrightness(LightLayer.BLOCK, sidePos);
            }

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

    public static class SoulColor {
        private static final Color soulColor = new Color(37f / 255f, 244f / 255f, 255f / 255f);

        public static float getRed() {
            return soulColor.getRed() / 255f;
        }

        public static float getGreen() {
            return soulColor.getGreen() / 255f;
        }

        public static float getBlue() {
            return soulColor.getBlue() / 255f;
        }

    }

}
