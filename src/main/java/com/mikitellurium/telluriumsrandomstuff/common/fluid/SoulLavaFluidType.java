package com.mikitellurium.telluriumsrandomstuff.common.fluid;

import com.mikitellurium.telluriumsrandomstuff.util.BaseFluidType;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import org.joml.Vector3f;

public class SoulLavaFluidType extends BaseFluidType {

    private static final ResourceLocation SOUL_LAVA_STILL_RL = FastLoc.modLoc("block/soul_lava_still");
    private static final ResourceLocation SOUL_LAVA_FLOWING_RL = FastLoc.modLoc("block/soul_lava_flow");

    public SoulLavaFluidType() {
        super(SOUL_LAVA_STILL_RL, SOUL_LAVA_FLOWING_RL, SOUL_LAVA_FLOWING_RL,
                null, 0xFFFFFFFF, 0.05f, 0.8f,
                new Vector3f(0f / 255f, 210f / 255f, 225f / 255f),
                FluidType.Properties.create()
                        .lightLevel(15)
                        .density(3000)
                        .viscosity(6000)
                        .temperature(1300)
                        .canSwim(false)
                        .canDrown(false)
                        .canExtinguish(false)
                        .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA)
                        .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA));
    }

    @Override
    public double motionScale(Entity entity) {
        return entity.level().dimensionType().ultraWarm() ? 0.007 : 0.0023333333333333335;
    }

    @Override
    public void setItemMovement(ItemEntity entity) {
        Vec3 vec3 = entity.getDeltaMovement();
        entity.setDeltaMovement(vec3.x * (double)0.95F, vec3.y + (double)(vec3.y < (double)0.06F ? 5.0E-4F : 0.0F), vec3.z * (double)0.95F);
    }

    @Override
    public boolean move(FluidState state, LivingEntity entity, Vec3 movementVector, double gravity) {
        return SoulLavaFluid.applyMovementLogic(entity, movementVector, gravity);
    }

}
