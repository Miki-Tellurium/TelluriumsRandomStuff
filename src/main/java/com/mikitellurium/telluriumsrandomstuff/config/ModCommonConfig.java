package com.mikitellurium.telluriumsrandomstuff.config;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class ModCommonConfig {

    public static ForgeConfigSpec.BooleanValue ENABLE_MOD_DISPENSER_BEHAVIOR;

    public static void registerCommonConfig() {
        ForgeConfigSpec.Builder CONFIG_BUILDER = new ForgeConfigSpec.Builder();
        registerConfig(CONFIG_BUILDER);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CONFIG_BUILDER.build());
    }

    private static void registerConfig(ForgeConfigSpec.Builder builder) {
        builder.comment("Tellurium's Random Stuff Config").push(TelluriumsRandomStuffMod.MOD_ID);

        ENABLE_MOD_DISPENSER_BEHAVIOR = builder
                .comment("Enable dispenser interactions with cauldrons.")
                .define("enableDispenserCauldronInteractions", true);

        builder.pop();
    }

}
