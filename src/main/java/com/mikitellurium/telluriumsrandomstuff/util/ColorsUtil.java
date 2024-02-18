package com.mikitellurium.telluriumsrandomstuff.util;

import com.mikitellurium.telluriumsrandomstuff.common.item.LavaGooglesItem;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

import java.awt.*;

public class ColorsUtil {

    private static final int opalBaseColor = FastColor.ARGB32.color(255, 160, 220, 220);
    private static final int opalCrystalBaseColor = FastColor.ARGB32.color(255, 140, 255, 255);
    private static final int blank = FastColor.ARGB32.color(255, 255, 255, 255);
    private static final int alpha0 = FastColor.ARGB32.color(0, 255, 255, 255);

    public static int getGooglesColor(ItemStack stack, int tintIndex) {
        if (tintIndex == 1) {
            DyeColor color = LavaGooglesItem.getColor(stack);
            return color == null ? alpha0 : getIntDyeColor(color);
        } else {
            return blank;
        }
    }

    public static int getOpalStoneColor(int lightLevel) {
        return getRainbowColor(-1, lightLevel, 0.6f, 0.9f, false);
    }

    public static int getOpalCrystalColor(int tintIndex, int lightLevel) {
        return switch (tintIndex) {
            case 0 -> getRainbowColor(tintIndex, lightLevel, 0.75f, 1.0f, true);
            case 1 -> getRainbowColor(tintIndex, lightLevel, 0.4F, 1.0f, true);
            default -> blank;
        };
    }

    public static int getRainbowColor(int tintIndex, int lightLevel, float saturation, float brightness, boolean isCrystal) {
        if (lightLevel == 0) {
            saturation = tintIndex == 0 ? saturation * 0.75F : saturation;
            return isCrystal ? RGBtoHSB(opalCrystalBaseColor, saturation, brightness) : opalBaseColor;
        }

        double inverted = 15.5D - lightLevel;
        float hue = ((float) inverted / 15) * 0.575f; // Adjust the multiplier to control the spectrum range

        Color color = Color.getHSBColor(hue, saturation, brightness);
        return color.getRGB();
    }

    private static int RGBtoHSB(int rgb, float saturation, float brightness) {
        return Color.getHSBColor(extractHue(rgb), saturation, brightness).getRGB();
    }

    private static float extractHue(int rgb) {
        float[] hsbValues = Color.RGBtoHSB((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF, null);
        return hsbValues[0];
    }

    public static int getIntDyeColor(DyeColor dyeColor) {
        float[] floats = dyeColor.getTextureDiffuseColors();
        int r = (int) (floats[0] * 255.0f);
        int g = (int) (floats[1] * 255.0f);
        int b = (int) (floats[2] * 255.0f);
        return FastColor.ABGR32.color(255, r, g, b);
    }

}
