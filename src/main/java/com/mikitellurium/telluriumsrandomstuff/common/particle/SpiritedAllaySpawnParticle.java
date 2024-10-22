package com.mikitellurium.telluriumsrandomstuff.common.particle;

import com.mikitellurium.telluriumsrandomstuff.util.ColorsUtil;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import org.jetbrains.annotations.Nullable;

public class SpiritedAllaySpawnParticle extends TextureSheetParticle {

    public SpiritedAllaySpawnParticle(ClientLevel level, double x, double y, double z, double xSpeed,
                                      double ySpeed, double zSpeed, float red, float green, float blue) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);
        this.setColor(red, green, blue);
        this.setParticleSpeed(xSpeed, ySpeed, zSpeed);
        this.setLifetime(4);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public static class Provider implements ParticleProvider<ColoredParticleOption> {

        private final SpriteSet sprite;

        public Provider(SpriteSet sprite) {
            this.sprite = sprite;
        }

        @Nullable
        @Override
        public Particle createParticle(ColoredParticleOption option, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            float[] rgb = ColorsUtil.getRgbComponents(option.color().getTextColor());
            float mul = 1.2F;
            float r = Math.min(rgb[0] * mul, 1.0F);
            float g = Math.min(rgb[1] * mul, 1.0F);
            float b = Math.min(rgb[2] * mul, 1.0F);
            SpiritedAllaySpawnParticle particle = new SpiritedAllaySpawnParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, r, g, b);
            particle.pickSprite(this.sprite);
            return particle;
        }
    }

}
