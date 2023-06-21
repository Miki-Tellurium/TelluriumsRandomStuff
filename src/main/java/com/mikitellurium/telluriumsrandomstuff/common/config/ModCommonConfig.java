package com.mikitellurium.telluriumsrandomstuff.common.config;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.common.content.blockentity.SoulAnchorBlockEntity;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class ModCommonConfig {

    public static ForgeConfigSpec.BooleanValue ENABLE_MOD_DISPENSER_BEHAVIOR;
    public static ForgeConfigSpec.IntValue ZOMBIE_RIDER_SPAWN_CHANCE;

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

        ZOMBIE_RIDER_SPAWN_CHANCE = builder
                .comment("The chance of a zombie riding a zombie horse spawning in a village zombie siege.",
                         "100 to always spawn, 0 to never spawn.")
                .defineInRange("zombieRiderSpawnChance", 25, 0, 100);

        SoulAnchorBlockEntity.ITEM_VOID_CHANCE = builder
                .comment("The chance of a stack getting lost when recovering the inventory with a soul anchor")
                .defineInRange("soulAnchorItemVoidChance", 0, 0, 100);

        builder.pop();
    }

}