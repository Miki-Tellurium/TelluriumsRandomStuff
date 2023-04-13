package com.mikitellurium.telluriumsrandomstuff.fluid;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.joml.Vector3f;

public class ModFluidTypes {

    private static final ResourceLocation SOUL_LAVA_STILL_RL = new ResourceLocation(TelluriumsRandomStuffMod.MOD_ID, "block/soul_lava_still");
    private static final ResourceLocation SOUL_LAVA_FLOWING_RL = new ResourceLocation(TelluriumsRandomStuffMod.MOD_ID,"block/soul_lava_flow");

    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, TelluriumsRandomStuffMod.MOD_ID);

    public static final RegistryObject<FluidType> SOUL_LAVA_FLUID_TYPE = registerSoulLavaType("soul_lava_fluid",
            FluidType.Properties.create()
                    .lightLevel(15)
                    .density(3000)
                    .viscosity(6000)
                    .temperature(1300)
                    .canSwim(true)
                    .canDrown(false)
                    .canExtinguish(false)
                    .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA)
                    .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA));

    // TODO This is jank. Make this flexible
    private static RegistryObject<FluidType> registerSoulLavaType(String name, FluidType.Properties properties) {
        return FLUID_TYPES.register(name, () -> new BaseFluidType(SOUL_LAVA_STILL_RL, SOUL_LAVA_FLOWING_RL, SOUL_LAVA_FLOWING_RL,
                null, 0xFFFFFFFF, 0.05f, 0.8f, new Vector3f(0f / 255f, 206 / 255f, 242f / 255f),
                properties) {

            @Override
            public double motionScale(Entity entity) {
                return entity.level.dimensionType().ultraWarm() ? 0.007D : 0.0023333333333333335D;
            }

            @Override
            public void setItemMovement(ItemEntity entity) {
                Vec3 vec3 = entity.getDeltaMovement();
                entity.setDeltaMovement(vec3.x * (double)0.95F, vec3.y + (double)(vec3.y < (double)0.06F ? 5.0E-4F : 0.0F), vec3.z * (double)0.95F);
            }

        });
    }

    public static void registerSoulLavaType(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
    }

}
