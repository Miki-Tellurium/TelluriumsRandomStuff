package com.mikitellurium.telluriumsrandomstuff.particle.custom;

import com.mikitellurium.telluriumsrandomstuff.fluid.ModFluids;
import com.mikitellurium.telluriumsrandomstuff.particle.ModParticles;
import com.mikitellurium.telluriumsrandomstuff.util.ColorsUtil;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SoulLavaDripParticle extends TextureSheetParticle {

    private final Fluid type;
    protected boolean isGlowing;

    protected SoulLavaDripParticle(ClientLevel pLevel, double pX, double pY, double pZ, Fluid type) {
        super(pLevel, pX, pY, pZ);
        this.setSize(0.01F, 0.01F);
        this.gravity = 0.06F;
        this.type = type;
    }

    protected Fluid getType() {
        return this.type;
    }

    public int getLightColor(float pPartialTick) {
        return this.isGlowing ? 240 : super.getLightColor(pPartialTick);
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
                BlockPos blockpos = new BlockPos(this.x, this.y, this.z);
                FluidState fluidstate = this.level.getFluidState(blockpos);
                if (fluidstate.getType() == this.type && this.y < (double)((float)blockpos.getY() + fluidstate.getHeight(this.level, blockpos))) {
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
        FallingParticle(ClientLevel pLevel, double pX, double pY, double pZ, Fluid pType) {
            this(pLevel, pX, pY, pZ, pType, (int)(64.0D / (Math.random() * 0.8D + 0.2D)));
        }

        FallingParticle(ClientLevel pLevel, double pX, double pY, double pZ, Fluid pType, int pLifetime) {
            super(pLevel, pX, pY, pZ, pType);
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

        DripHangParticle(ClientLevel pLevel, double pX, double pY, double pZ, Fluid pType, ParticleOptions pFallingParticle) {
            super(pLevel, pX, pY, pZ, pType);
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
        DripLandParticle(ClientLevel clientLevel, double pX, double pY, double pZ, Fluid fluid) {
            super(clientLevel, pX, pY, pZ, fluid);
            this.lifetime = (int)(16.0D / (Math.random() * 0.8D + 0.2D));
        }
    }

    @OnlyIn(Dist.CLIENT)
    static class CoolingDripHangParticle extends SoulLavaDripParticle.DripHangParticle {
        CoolingDripHangParticle(ClientLevel clientLevel, double pX, double pY, double pZ, Fluid fluid, ParticleOptions particleOptions) {
            super(clientLevel, pX, pY, pZ, fluid, particleOptions);
        }

        protected void preMoveUpdate() {
            this.setColor(ColorsUtil.soulRedColor(), ColorsUtil.soulGreenColor(), ColorsUtil.soulBlueColor());
            super.preMoveUpdate();
        }
    }

    @OnlyIn(Dist.CLIENT)
    static class FallAndLandParticle extends SoulLavaDripParticle.FallingParticle {
        protected final ParticleOptions landParticle;

        FallAndLandParticle(ClientLevel pLevel, double pX, double pY, double pZ, Fluid pType, ParticleOptions pLandParticle) {
            super(pLevel, pX, pY, pZ, pType);
            this.landParticle = pLandParticle;
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

        public SoulLavaFallProvider(SpriteSet pSprites) {
            this.sprite = pSprites;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            SoulLavaDripParticle dripParticle = new SoulLavaDripParticle.FallAndLandParticle(pLevel, pX, pY, pZ, ModFluids.SOUL_LAVA_SOURCE.get(), ModParticles.SOUL_LAVA_LAND.get());
            dripParticle.setColor(ColorsUtil.soulRedColor(), ColorsUtil.soulGreenColor(), ColorsUtil.soulBlueColor());
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

        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            SoulLavaDripParticle.CoolingDripHangParticle dripparticle$coolingdriphangparticle = new SoulLavaDripParticle.CoolingDripHangParticle(pLevel, pX, pY, pZ, ModFluids.SOUL_LAVA_SOURCE.get(), ModParticles.SOUL_LAVA_FALL.get());
            dripparticle$coolingdriphangparticle.pickSprite(this.sprite);
            return dripparticle$coolingdriphangparticle;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class SoulLavaLandProvider implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet sprite;

        public SoulLavaLandProvider(SpriteSet pSprites) {
            this.sprite = pSprites;
        }

        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            SoulLavaDripParticle dripParticle = new SoulLavaDripParticle.DripLandParticle(pLevel, pX, pY, pZ, ModFluids.SOUL_LAVA_SOURCE.get());
            dripParticle.setColor(ColorsUtil.soulRedColor(), ColorsUtil.soulGreenColor(), ColorsUtil.soulBlueColor());
            dripParticle.pickSprite(this.sprite);
            return dripParticle;
        }
    }

}
