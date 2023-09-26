package com.mikitellurium.telluriumsrandomstuff.datagen;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.datagen.providers.*;
import com.mikitellurium.telluriumsrandomstuff.datagen.providers.tags.ModTagProvider;
import com.mikitellurium.telluriumsrandomstuff.datagen.providers.tags.VanillaTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
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
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        VanillaTagProvider.Blocks vanillaBlockTagsProvider = new VanillaTagProvider.Blocks(packOutput, lookupProvider, existingFileHelper);
        ModTagProvider.Blocks modBlockTagsProvider = new ModTagProvider.Blocks(packOutput, lookupProvider, existingFileHelper);
        generator.addProvider(true, new ModBlockModelProvider(packOutput, existingFileHelper));
        generator.addProvider(true, new ModItemModelProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModWorldGenProvider(packOutput, lookupProvider));
        generator.addProvider(true, new ModRecipeProvider(generator));
        generator.addProvider(true, ModBlockLootTableProvider.create(packOutput));
        generator.addProvider(true, new ModTranslationProvider(packOutput));
        generator.addProvider(true, vanillaBlockTagsProvider);
        generator.addProvider(true, new VanillaTagProvider.Items(packOutput, lookupProvider, vanillaBlockTagsProvider.contentsGetter(), existingFileHelper));
        generator.addProvider(true, modBlockTagsProvider);
        generator.addProvider(true, new ModTagProvider.Items(packOutput, lookupProvider, modBlockTagsProvider.contentsGetter(), existingFileHelper));
        generator.addProvider(true, new ModTagProvider.EntityTypes(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(true, new ModLootModifierProvider(packOutput));
    }

}
