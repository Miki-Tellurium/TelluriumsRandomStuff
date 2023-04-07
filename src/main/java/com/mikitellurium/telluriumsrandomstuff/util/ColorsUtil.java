package com.mikitellurium.telluriumsrandomstuff.util;

import com.sun.jna.platform.win32.WinBase;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;

import java.awt.*;

public class ColorsUtil {

    public static int getRainbowColor(BlockPos pos) {
        int waveSize = 75; // How big are the color waves
        Color color = Color.getHSBColor(
                (((pos.getX() + pos.getZ() + pos.getY() * 1.05f)) / waveSize) % 1.0f,
                0.65F, 0.8F);
        return color.getRGB();
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
}
