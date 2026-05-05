package com.mikitellurium.telluriumsrandomstuff;

import com.mikitellurium.telluriumsrandomstuff.api.CustomRegistries;
import com.mikitellurium.telluriumsrandomstuff.registry.ModRegistries;
import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(TelluriumsRandomStuffMod.MOD_ID)
public class TelluriumsRandomStuffMod {
    public static final String MOD_ID = "telluriumsrandomstuff";
    public static final Logger LOGGER = LogUtils.getLogger();

    public TelluriumsRandomStuffMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        CustomRegistries.init(modEventBus);
        ModRegistries.init(modEventBus);
        CommonSetup.registerForgeBusEvents();
        CommonSetup.registerModBusEvents(modEventBus);
    }
}

