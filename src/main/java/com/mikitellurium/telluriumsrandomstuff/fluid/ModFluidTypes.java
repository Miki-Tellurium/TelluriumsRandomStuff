package com.mikitellurium.telluriumsrandomstuff.fluid;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
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

    public static final RegistryObject<FluidType> SOUL_LAVA_FLUID_TYPE = register("soul_lava_fluid",
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

    private static RegistryObject<FluidType> register(String name, FluidType.Properties properties) {
        return FLUID_TYPES.register(name, () -> new BaseFluidType(SOUL_LAVA_STILL_RL, SOUL_LAVA_FLOWING_RL, SOUL_LAVA_FLOWING_RL,
                null, 0xFFFFFFFF, 0.05f, 0.8f, new Vector3f(0f / 255f, 120f / 255f, 120f / 255f), properties));
    }

    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
    }

}
