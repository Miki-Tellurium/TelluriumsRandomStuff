package com.mikitellurium.telluriumsrandomstuff.registry;

import com.mikitellurium.telluriumsrandomstuff.common.fluid.SoulLavaFluidType;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModFluidTypes {

    public static final RegistryObject<FluidType> SOUL_LAVA_FLUID_TYPE = registerFluidType("soul_lava", SoulLavaFluidType::new);

    public static <T extends FluidType> RegistryObject<T> registerFluidType(String name, Supplier<T> fluid) {
        return ModRegistries.FLUID_TYPES.register(name, fluid);
    }

    protected static void register(IEventBus eventBus) {
        ModRegistries.FLUID_TYPES.register(eventBus);
    }

}
