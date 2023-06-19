package com.mikitellurium.telluriumsrandomstuff.common.datagen;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.common.datagen.providers.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = TelluriumsRandomStuffMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeServer(), new ModWorldGenProvider(packOutput, lookupProvider));
        generator.addProvider(true, new ModRecipeProvider(generator));
        generator.addProvider(true, ModLootTableProvider.create(packOutput));
        generator.addProvider(true, new ModTranslationProvider(packOutput, "en_us"));

        // Armor trim stuff
        //generator.addProvider(event.includeServer(), new ModRegistryProvider(packOutput, lookupProvider));
    }

}
