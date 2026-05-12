package com.mikitellurium.telluriumsrandomstuff.util;

import com.mikitellurium.telluriumsrandomstuff.common.block.OpalBlock;
import net.minecraft.Util;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.util.FastColor;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;

import java.awt.*;

public class ColorsUtil {
    public static final int BLANK = 0xFFFFFFFF;
    public static final int ALPHA_0 = 0x00FFFFFF;
    private static final Integer[] OPAL_COLORS = Util.make(new Integer[OpalBlock.HUE_RANGE], (array) -> {
        for (int i = 0; i < OpalBlock.HUE_RANGE; i++) {
            float newHue = (float) i / (OpalBlock.HUE_RANGE - 1);
            array[i] = Color.getHSBColor(newHue, 0.55f, 0.9f).getRGB();
        }
    });

    public static int getOpalColor(int hue) {
        return OPAL_COLORS[hue];
    }

    public static float[] getRgbComponents(int rgb) {
        float red = (rgb >> 16) & 0xFF;
        float green = (rgb >> 8) & 0xFF;
        float blue = rgb & 0xFF;
        return new float[] {red / 255.0F, green / 255.0F, blue / 255.0F};
    }

    public static int getRandomRgb(RandomSource random) {
        return getRandomRgb(random, 255);
    }

    public static int getRandomRgb(RandomSource random, int alpha) {
        int red = (int) (random.nextFloat() * 255.0F);
        int green = (int) (random.nextFloat() * 255.0F);
        int blue = (int) (random.nextFloat() * 255.0F);
        return FastColor.ARGB32.color(alpha, red, green, blue);
    }

    public static int getDyeColorAsInt(DyeColor dyeColor) {
        float[] floats = dyeColor.getTextureDiffuseColors();
        int r = (int) (floats[0] * 255.0F);
        int g = (int) (floats[1] * 255.0F);
        int b = (int) (floats[2] * 255.0F);
        return FastColor.ABGR32.color(255, r, g, b);
    }

    public static DyeColor getRandomDyeColor(RandomSource random) {
        int i = DyeColor.values().length;
        return DyeColor.byId(random.nextInt(i));
    }
}
