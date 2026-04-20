package com.mikitellurium.telluriumsrandomstuff.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.FastColor;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.concurrent.ExecutionException;

public class ColorsUtil {
    public static final int OPAL_BASE_COLOR = 0xFFA0DCDC;
    public static final int OPAL_CRYSTAL_BASE_COLOR_0 = 0xFF66FFFF;
    public static final int OPAL_CRYSTAL_BASE_COLOR_1 = 0xFFB3FFFF;
    public static final int BLANK = 0xFFFFFFFF;
    public static final int ALPHA_0 = 0x00FFFFFF;

    private static final int MAX_CACHE_SIZE = 50_000;
    private static final Cache<Long, Float> CACHE = CacheBuilder.newBuilder()
            .maximumSize(MAX_CACHE_SIZE).recordStats().build();
//    private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
//    static {
//        scheduler.scheduleAtFixedRate(() -> System.out.println("OpalColorCache Stats: " + CACHE.stats()), 0, 10, TimeUnit.SECONDS);
//    }
    private static final Float[] SATURATION_VALUES = new Float[16];

    public static BlockColor getOpalBlockColor(boolean isCrystal) {
        return (state, level, pos, tintIndex) -> {
            int lightLevel = LevelUtils.getHighestLightLevel(level, pos);
            return getOpalColorFromPos(pos, lightLevel, tintIndex, isCrystal);
        };
    }

    public static ItemColor getOpalItemColor(boolean isCrystal) {
        return new ItemColor() {
            @Override
            public int getColor(ItemStack stack, int tintIndex) {
                ClientLevel level = Minecraft.getInstance().level;
                if (level == null) {
                    if (isCrystal) {
                        return tintIndex == 0 ? OPAL_CRYSTAL_BASE_COLOR_0 : OPAL_CRYSTAL_BASE_COLOR_1;
                    }
                    return OPAL_BASE_COLOR;
                }

                BlockPos pos = getBlockPos(stack);
                int lightLevel = LevelUtils.getHighestLightLevel(level, pos);
                return getOpalColorFromPos(pos, lightLevel, tintIndex, isCrystal);
            }

            private static BlockPos getBlockPos(ItemStack stack) {
                BlockPos pos = BlockPos.ZERO;
                if (stack.isFramed()) { // Check if the item is in item frame
                    pos = stack.getFrame().getPos();
                } else if (stack.getEntityRepresentation() != null) { // Check if the item is dropped in the world
                    pos = stack.getEntityRepresentation().getOnPos();
                } else {
                    Player player = Minecraft.getInstance().player;
                    if (player != null) {
                        pos = player.getOnPos();
                    }
                }
                pos = pos.above();
                return pos;
            }
        };
    }

    private static int getOpalColorFromPos(BlockPos pos, int lightLevel, int tintIndex, boolean isCrystal) {
        if (lightLevel == 0) {
            if (isCrystal) {
                return tintIndex == 0 ? OPAL_CRYSTAL_BASE_COLOR_0 : OPAL_CRYSTAL_BASE_COLOR_1;
            }
            return OPAL_BASE_COLOR;
        }

        float hue;
        try {
            hue = CACHE.get(pos.asLong(), ()-> computeColor(pos));
        } catch (ExecutionException e) {
            hue = computeColor(pos);
        }

        float saturation = computeSaturation(lightLevel);
        if (isCrystal) {
            saturation = tintIndex == 0 ? 0.7f : 0.45f;
        }

        return Color.getHSBColor(hue, saturation, 1.0f).getRGB();
    }

    private static float computeColor(BlockPos pos) {
        double period = 0.0275d;
        double x = pos.getX() * period;
        double y = pos.getY() * period;
        double z = pos.getZ() * period;
        double avgSin = (Math.sin(x) + Math.sin(z) + Math.sin(y)) / 3.0; // Average sin of the coordinates
        float hue = (float) (avgSin + 0.5); // Wrap to [-0.5, 1.5]
        hue = hue - (float) Math.floor(hue); // Wrap hue to [0,1)
        return hue;
    }

    private static float computeSaturation(int lightLevel) {
        Float value = SATURATION_VALUES[lightLevel];
        if (value != null) {
            return value;
        }
        float minValue = 0.35f;
        float maxValue = 0.65f;
        float range = maxValue - minValue;
        return SATURATION_VALUES[lightLevel] = minValue + (lightLevel - 1) * (range / 14);
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

    public static DyeColor getRandomDyeColor() {
        return getRandomDyeColor(RandomSource.create());
    }

    public static DyeColor getRandomDyeColor(RandomSource random) {
        int i = DyeColor.values().length;
        return DyeColor.byId(random.nextInt(i));
    }
}
