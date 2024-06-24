package com.mikitellurium.telluriumsrandomstuff.datagen;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.datagen.providers.*;
import com.mikitellurium.telluriumsrandomstuff.datagen.providers.tags.ForgeTagProvider;
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

        generator.addProvider(true, new ModBlockModelProvider(packOutput, existingFileHelper));
        generator.addProvider(true, new ModItemModelProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModWorldGenProvider(packOutput, lookupProvider));
        generator.addProvider(true, new ModRecipeProvider(generator));
        generator.addProvider(true, ModBlockLootTableProvider.create(packOutput));
        generator.addProvider(true, new ModTranslationProvider(packOutput));
        generator.addProvider(true, new ModLootModifierProvider(packOutput));
        generator.addProvider(true, new ModPotionMixingFunctionProvider(packOutput));

        // Vanilla tags
        VanillaTagProvider.Blocks vanillaBlockTags = new VanillaTagProvider.Blocks(packOutput, lookupProvider, existingFileHelper);
        generator.addProvider(true, vanillaBlockTags);
        generator.addProvider(true, new VanillaTagProvider.Items(packOutput, lookupProvider, vanillaBlockTags.contentsGetter(), existingFileHelper));
        // Mod tags
        ModTagProvider.Blocks modBlockTags = new ModTagProvider.Blocks(packOutput, lookupProvider, existingFileHelper);
        generator.addProvider(true, modBlockTags);
        generator.addProvider(true, new ModTagProvider.Items(packOutput, lookupProvider, modBlockTags.contentsGetter(), existingFileHelper));
        generator.addProvider(true, new ModTagProvider.EntityTypes(packOutput, lookupProvider, existingFileHelper));
        // Forge tags
        ForgeTagProvider.Blocks forgeBlockTags = new ForgeTagProvider.Blocks(packOutput, lookupProvider, existingFileHelper);
        generator.addProvider(true, forgeBlockTags);
        generator.addProvider(true, new ForgeTagProvider.Items(packOutput, lookupProvider, forgeBlockTags.contentsGetter(), existingFileHelper));
    }

}
