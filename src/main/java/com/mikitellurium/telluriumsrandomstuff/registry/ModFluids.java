package com.mikitellurium.telluriumsrandomstuff.registry;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.common.fluid.SoulLavaFluid;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFluids {

    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(ForgeRegistries.FLUIDS, TelluriumsRandomStuffMod.MOD_ID);

    public static final RegistryObject<FlowingFluid> SOUL_LAVA_SOURCE = FLUIDS.register("soul_lava",
            () -> new SoulLavaFluid.Source(ModFluids.SOUL_LAVA_PROPERTIES));
    public static final RegistryObject<FlowingFluid> SOUL_LAVA_FLOWING = FLUIDS.register("soul_lava_flowing",
            () -> new SoulLavaFluid.Flowing(ModFluids.SOUL_LAVA_PROPERTIES));


    public static final ForgeFlowingFluid.Properties SOUL_LAVA_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluidTypes.SOUL_LAVA_FLUID_TYPE, SOUL_LAVA_SOURCE, SOUL_LAVA_FLOWING);


    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }

}
