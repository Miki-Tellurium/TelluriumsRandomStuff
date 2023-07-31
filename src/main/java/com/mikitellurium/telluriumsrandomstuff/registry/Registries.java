package com.mikitellurium.telluriumsrandomstuff.registry;

import com.mikitellurium.telluriumsrandomstuff.common.config.ModCommonConfig;
import com.mikitellurium.telluriumsrandomstuff.common.networking.ModMessages;
import net.minecraftforge.eventbus.api.IEventBus;

public class Registries {

    public static void init(IEventBus modEventBus) {
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModCreativeTab.register(modEventBus);
        ModFluidTypes.register(modEventBus);
        ModFluids.register(modEventBus);
        ModParticles.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModRecipeSerializers.register(modEventBus);
        ModEnchantments.register(modEventBus);
        ModFeatures.register(modEventBus);
        ModMessages.register();
        ModCommonConfig.register();
    }

}
