package com.mikitellurium.telluriumsrandomstuff.util;

import com.mikitellurium.telluriumsrandomstuff.common.item.LavaGooglesItem;
import com.mikitellurium.telluriumsrandomstuff.registry.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;

import java.awt.*;

public class ColorsUtil {

    private static final int opalBaseColor = FastColor.ABGR32.color(255, 160, 210, 210);
    private static final int opalCrystalBaseColor = FastColor.ABGR32.color(255, 140, 255, 255);
    private static final int blank = FastColor.ABGR32.color(255, 255, 255, 255);
    private static final int alpha0 = FastColor.ABGR32.color(0, 255, 255, 255);

    public static int getGooglesColor(ItemStack stack, int tintIndex) {
        if (tintIndex == 1) {
            DyeColor color = LavaGooglesItem.getColor(stack);
            return color == null ? alpha0 : getIntDyeColor(color);
        } else {
            return blank;
        }
    }

    public static int getOpalStoneColor(int lightLevel) {
        return ColorsUtil.getRainbowColor(lightLevel, 0.55f, 0.9f, false);
    }

    public static int getOpalCrystalColor(int tintIndex, int lightLevel) {
        return tintIndex == 0 ? ColorsUtil.getRainbowColor(lightLevel, 0.75f, 1.0f, true) : blank;
    }

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

    public static int getIntDyeColor(DyeColor dyeColor) {
        float[] floats = dyeColor.getTextureDiffuseColors();
        int r = (int) (floats[0] * 255.0f);
        int g = (int) (floats[1] * 255.0f);
        int b = (int) (floats[2] * 255.0f);
        return FastColor.ABGR32.color(255, r, g, b);
    }

}
