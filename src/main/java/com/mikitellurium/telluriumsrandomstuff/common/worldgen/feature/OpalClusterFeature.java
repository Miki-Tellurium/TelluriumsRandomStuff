package com.mikitellurium.telluriumsrandomstuff.common.worldgen.feature;

import com.mikitellurium.telluriumsrandomstuff.common.block.RGBTinted;
import com.mikitellurium.telluriumsrandomstuff.common.block.RGBTintedBlock;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.BulkSectionAccess;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.BitSet;

public class OpalClusterFeature extends Feature<OpalClusterConfiguration> {
    public OpalClusterFeature(Codec<OpalClusterConfiguration> codec) {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<OpalClusterConfiguration> context) {
        RandomSource random = context.random();
        BlockPos origin = context.origin();
        WorldGenLevel level = context.level();
        BlockState finalState = withRandomHue(random);
        OpalClusterConfiguration config = context.config();
        float f = random.nextFloat() * (float)Math.PI;
        float f1 = (float)config.size() / 8.0F;
        int i = Mth.ceil(((float)config.size() / 16.0F * 2.0F + 1.0F) / 2.0F);
        double d0 = (double)origin.getX() + Math.sin(f) * (double)f1;
        double d1 = (double)origin.getX() - Math.sin(f) * (double)f1;
        double d2 = (double)origin.getZ() + Math.cos(f) * (double)f1;
        double d3 = (double)origin.getZ() - Math.cos(f) * (double)f1;
        int j = 2;
        double d4 = origin.getY() + random.nextInt(3) - 2;
        double d5 = origin.getY() + random.nextInt(3) - 2;
        int k = origin.getX() - Mth.ceil(f1) - i;
        int l = origin.getY() - 2 - i;
        int i1 = origin.getZ() - Mth.ceil(f1) - i;
        int j1 = 2 * (Mth.ceil(f1) + i);
        int k1 = 2 * (2 + i);

        for(int l1 = k; l1 <= k + j1; ++l1) {
            for(int i2 = i1; i2 <= i1 + j1; ++i2) {
                if (l <= level.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, l1, i2)) {
                    return this.doPlace(level, random, config, finalState, d0, d1, d2, d3, d4, d5, k, l, i1, j1, k1);
                }
            }
        }

        return false;
    }

    private boolean doPlace(WorldGenLevel level, RandomSource random, OpalClusterConfiguration config, BlockState finalState,
                            double minX, double maxX, double minZ, double maxZ, double minY, double maxY,
                            int x, int y, int z, int width, int height) {
        int i = 0;
        BitSet bitset = new BitSet(width * height * width);
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
        int j = config.size();
        double[] doubles = new double[j * 4];

        for(int k = 0; k < j; ++k) {
            float f = (float)k / (float)j;
            double d0 = Mth.lerp(f, minX, maxX);
            double d1 = Mth.lerp(f, minY, maxY);
            double d2 = Mth.lerp(f, minZ, maxZ);
            double d3 = random.nextDouble() * (double)j / 16.0D;
            double d4 = ((double)(Mth.sin((float)Math.PI * f) + 1.0F) * d3 + 1.0D) / 2.0D;
            doubles[k * 4 + 0] = d0;
            doubles[k * 4 + 1] = d1;
            doubles[k * 4 + 2] = d2;
            doubles[k * 4 + 3] = d4;
        }

        for(int l3 = 0; l3 < j - 1; ++l3) {
            if (!(doubles[l3 * 4 + 3] <= 0.0D)) {
                for(int i4 = l3 + 1; i4 < j; ++i4) {
                    if (!(doubles[i4 * 4 + 3] <= 0.0D)) {
                        double d8 = doubles[l3 * 4 + 0] - doubles[i4 * 4 + 0];
                        double d10 = doubles[l3 * 4 + 1] - doubles[i4 * 4 + 1];
                        double d12 = doubles[l3 * 4 + 2] - doubles[i4 * 4 + 2];
                        double d14 = doubles[l3 * 4 + 3] - doubles[i4 * 4 + 3];
                        if (d14 * d14 > d8 * d8 + d10 * d10 + d12 * d12) {
                            if (d14 > 0.0D) {
                                doubles[i4 * 4 + 3] = -1.0D;
                            } else {
                                doubles[l3 * 4 + 3] = -1.0D;
                            }
                        }
                    }
                }
            }
        }

        try (BulkSectionAccess bulkSectionAccess = new BulkSectionAccess(level)) {
            for(int j4 = 0; j4 < j; ++j4) {
                double d9 = doubles[j4 * 4 + 3];
                if (!(d9 < 0.0D)) {
                    double d11 = doubles[j4 * 4 + 0];
                    double d13 = doubles[j4 * 4 + 1];
                    double d15 = doubles[j4 * 4 + 2];
                    int k4 = Math.max(Mth.floor(d11 - d9), x);
                    int l = Math.max(Mth.floor(d13 - d9), y);
                    int i1 = Math.max(Mth.floor(d15 - d9), z);
                    int j1 = Math.max(Mth.floor(d11 + d9), k4);
                    int k1 = Math.max(Mth.floor(d13 + d9), l);
                    int l1 = Math.max(Mth.floor(d15 + d9), i1);

                    for(int i2 = k4; i2 <= j1; ++i2) {
                        double d5 = ((double)i2 + 0.5D - d11) / d9;
                        if (d5 * d5 < 1.0D) {
                            for(int j2 = l; j2 <= k1; ++j2) {
                                double d6 = ((double)j2 + 0.5D - d13) / d9;
                                if (d5 * d5 + d6 * d6 < 1.0D) {
                                    for(int k2 = i1; k2 <= l1; ++k2) {
                                        double d7 = ((double)k2 + 0.5D - d15) / d9;
                                        if (d5 * d5 + d6 * d6 + d7 * d7 < 1.0D && !level.isOutsideBuildHeight(j2)) {
                                            int l2 = i2 - x + (j2 - y) * width + (k2 - z) * width * height;
                                            if (!bitset.get(l2)) {
                                                bitset.set(l2);
                                                blockpos$mutableblockpos.set(i2, j2, k2);
                                                if (level.ensureCanWrite(blockpos$mutableblockpos)) {
                                                    LevelChunkSection levelchunksection = bulkSectionAccess.getSection(blockpos$mutableblockpos);
                                                    if (levelchunksection != null) {
                                                        int i3 = SectionPos.sectionRelative(i2);
                                                        int j3 = SectionPos.sectionRelative(j2);
                                                        int k3 = SectionPos.sectionRelative(k2);
                                                        BlockState blockstate = levelchunksection.getBlockState(i3, j3, k3);
                                                        if (config.target().test(blockstate, random)) {
                                                            levelchunksection.setBlockState(i3, j3, k3, finalState, false);
                                                            ++i;
                                                            break;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return i > 0;
    }

    private BlockState withRandomHue(RandomSource random) {
        return ModBlocks.OPAL.get().defaultBlockState().setValue(RGBTinted.HUE, random.nextInt(32));
    }
}
