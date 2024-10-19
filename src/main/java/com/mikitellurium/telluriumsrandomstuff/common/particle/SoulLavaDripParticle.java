package com.mikitellurium.telluriumsrandomstuff.common.particle;

import com.mikitellurium.telluriumsrandomstuff.registry.ModFluids;
import com.mikitellurium.telluriumsrandomstuff.registry.ModParticles;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SoulLavaDripParticle extends TextureSheetParticle {

    private static final float[] PARTICLE_COLOR = new float[]{0.145F, 0.957F, 1.0F};

    private final Fluid fluid;
    protected boolean isGlowing;

    protected SoulLavaDripParticle(ClientLevel level, double x, double y, double z, Fluid type) {
        super(level, x, y, z);
        this.setColor(PARTICLE_COLOR[0], PARTICLE_COLOR[1], PARTICLE_COLOR[2]);
        this.setSize(0.01F, 0.01F);
        this.gravity = 0.06F;
        this.fluid = type;
    }

    public int getLightColor(float partialTick) {
        return this.isGlowing ? 240 : super.getLightColor(partialTick);
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        this.preMoveUpdate();
        if (!this.removed) {
            this.yd -= this.gravity;
            this.move(this.xd, this.yd, this.zd);
            this.postMoveUpdate();
            if (!this.removed) {
                this.xd *= 0.98F;
                this.yd *= 0.98F;
                this.zd *= 0.98F;
                BlockPos blockPos = new BlockPos((int)this.x, (int)this.y, (int)this.z);
                FluidState fluidState = this.level.getFluidState(blockPos);
                if (fluidState.getType() == this.fluid && this.y < (double)((float)blockPos.getY() + fluidState.getHeight(this.level, blockPos))) {
                    this.remove();
                }

            }
        }
    }

    protected void preMoveUpdate() {
        if (this.lifetime-- <= 0) {
            this.remove();
        }

    }

    protected void postMoveUpdate() {
    }

    @OnlyIn(Dist.CLIENT)
    static class FallingParticle extends SoulLavaDripParticle {
        FallingParticle(ClientLevel level, double x, double y, double z, Fluid fluid) {
            this(level, x, y, z, fluid, (int)(64.0D / (Math.random() * 0.8D + 0.2D)));
        }

        FallingParticle(ClientLevel level, double x, double y, double z, Fluid fluid, int pLifetime) {
            super(level, x, y, z, fluid);
            this.lifetime = pLifetime;
        }

        protected void postMoveUpdate() {
            if (this.onGround) {
                this.remove();
            }

        }
    }

    @OnlyIn(Dist.CLIENT)
    static class DripHangParticle extends SoulLavaDripParticle {
        private final ParticleOptions fallingParticle;

        DripHangParticle(ClientLevel level, double x, double y, double z, Fluid fluid, ParticleOptions pFallingParticle) {
            super(level, x, y, z, fluid);
            this.fallingParticle = pFallingParticle;
            this.gravity *= 0.02F;
            this.lifetime = 40;
        }

        protected void preMoveUpdate() {
            if (this.lifetime-- <= 0) {
                this.remove();
                this.level.addParticle(this.fallingParticle, this.x, this.y, this.z, this.xd, this.yd, this.zd);
            }

        }

        protected void postMoveUpdate() {
            this.xd *= 0.02D;
            this.yd *= 0.02D;
            this.zd *= 0.02D;
        }
    }

    @OnlyIn(Dist.CLIENT)
    static class DripLandParticle extends SoulLavaDripParticle {
        DripLandParticle(ClientLevel clientLevel, double x, double y, double z, Fluid fluid) {
            super(clientLevel, x, y, z, fluid);
            this.lifetime = (int)(16.0D / (Math.random() * 0.8D + 0.2D));
        }
    }

    @OnlyIn(Dist.CLIENT)
    static class CoolingDripHangParticle extends SoulLavaDripParticle.DripHangParticle {
        CoolingDripHangParticle(ClientLevel clientLevel, double x, double y, double z, Fluid fluid, ParticleOptions particleOptions) {
            super(clientLevel, x, y, z, fluid, particleOptions);
        }
        
    }

    @OnlyIn(Dist.CLIENT)
    static class FallAndLandParticle extends SoulLavaDripParticle.FallingParticle {
        protected final ParticleOptions landParticle;

        FallAndLandParticle(ClientLevel level, double x, double y, double z, Fluid fluid, ParticleOptions landParticle) {
            super(level, x, y, z, fluid);
            this.landParticle = landParticle;
        }

        protected void postMoveUpdate() {
            if (this.onGround) {
                this.remove();
                this.level.addParticle(this.landParticle, this.x, this.y, this.z, 0.0D, 0.0D, 0.0D);
            }

        }
    }

    public static class SoulLavaFallProvider implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet sprite;

        public SoulLavaFallProvider(SpriteSet sprite) {
            this.sprite = sprite;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType fluid, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            SoulLavaDripParticle dripParticle = new SoulLavaDripParticle.FallAndLandParticle(level, x, y, z, ModFluids.SOUL_LAVA_SOURCE.get(), ModParticles.SOUL_LAVA_LAND.get());
            dripParticle.pickSprite(this.sprite);
            return dripParticle;
        }

    }

    @OnlyIn(Dist.CLIENT)
    public static class SoulLavaHangProvider implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet sprite;

        public SoulLavaHangProvider(SpriteSet pSprites) {
            this.sprite = pSprites;
        }

        public Particle createParticle(SimpleParticleType fluid, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            SoulLavaDripParticle.CoolingDripHangParticle dripparticle$coolingdriphangparticle = new SoulLavaDripParticle.CoolingDripHangParticle(level, x, y, z, ModFluids.SOUL_LAVA_SOURCE.get(), ModParticles.SOUL_LAVA_FALL.get());
            dripparticle$coolingdriphangparticle.pickSprite(this.sprite);
            return dripparticle$coolingdriphangparticle;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class SoulLavaLandProvider implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet sprite;

        public SoulLavaLandProvider(SpriteSet sprite) {
            this.sprite = sprite;
        }

        public Particle createParticle(SimpleParticleType fluid, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            SoulLavaDripParticle dripParticle = new SoulLavaDripParticle.DripLandParticle(level, x, y, z, ModFluids.SOUL_LAVA_SOURCE.get());
            dripParticle.pickSprite(this.sprite);
            return dripParticle;
        }
    }

}
