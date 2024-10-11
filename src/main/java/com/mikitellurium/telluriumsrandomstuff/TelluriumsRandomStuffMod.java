package com.mikitellurium.telluriumsrandomstuff;

import com.mikitellurium.telluriumsrandomstuff.api.CustomRegistries;
import com.mikitellurium.telluriumsrandomstuff.registry.ModRegistries;
import com.mikitellurium.telluriumsrandomstuff.setup.ClientSetup;
import com.mikitellurium.telluriumsrandomstuff.setup.CommonSetup;
import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(TelluriumsRandomStuffMod.MOD_ID)
public class TelluriumsRandomStuffMod {

    // todo rei and emi compatibility
    // todo update changelog
    // todo add armor trims

    public static final String MOD_ID = "telluriumsrandomstuff";
    public static final Logger LOGGER = LogUtils.getLogger();

    public TelluriumsRandomStuffMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        CustomRegistries.init(modEventBus);
        ModRegistries.init(modEventBus);
        CommonSetup.registerForgeBusEvents();
        CommonSetup.registerModBusEvents(modEventBus);
    }

    @Mod.EventBusSubscriber(modid = TelluriumsRandomStuffMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class Client {

        @SubscribeEvent
        public static void clientSetup(final FMLClientSetupEvent event) {
            ClientSetup.registerForgeBusEvents();
        }

    }

}

