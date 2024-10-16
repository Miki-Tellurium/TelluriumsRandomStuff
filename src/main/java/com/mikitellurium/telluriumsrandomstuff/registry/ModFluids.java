package com.mikitellurium.telluriumsrandomstuff.registry;

import com.mikitellurium.telluriumsrandomstuff.common.fluid.SoulLavaFluid;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModFluids {

    public static final RegistryObject<FlowingFluid> SOUL_LAVA_SOURCE = registerFluid("soul_lava",
            () -> new SoulLavaFluid.Source(ModFluids.SOUL_LAVA_PROPERTIES));
    public static final RegistryObject<FlowingFluid> SOUL_LAVA_FLOWING = registerFluid("soul_lava_flowing",
            () -> new SoulLavaFluid.Flowing(ModFluids.SOUL_LAVA_PROPERTIES));

    public static final ForgeFlowingFluid.Properties SOUL_LAVA_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluidTypes.SOUL_LAVA_FLUID_TYPE, SOUL_LAVA_SOURCE, SOUL_LAVA_FLOWING)
            .bucket(ModItems.SOUL_LAVA_BUCKET)
            .block(ModBlocks.SOUL_LAVA_BLOCK);

    public static <F extends Fluid> RegistryObject<F> registerFluid(String name, Supplier<F> fluid) {
        return ModRegistries.FLUIDS.register(name, fluid);
    }

    protected static void register(IEventBus eventBus) {
        ModRegistries.FLUIDS.register(eventBus);
    }

}
