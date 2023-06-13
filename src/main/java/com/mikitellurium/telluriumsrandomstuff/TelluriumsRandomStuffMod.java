package com.mikitellurium.telluriumsrandomstuff;

import com.mikitellurium.telluriumsrandomstuff.block.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.block.interaction.ModCauldronInteractions;
import com.mikitellurium.telluriumsrandomstuff.block.interaction.ModDispenserBehaviours;
import com.mikitellurium.telluriumsrandomstuff.block.interaction.ModFluidInteractions;
import com.mikitellurium.telluriumsrandomstuff.blockentity.ModBlockEntities;
import com.mikitellurium.telluriumsrandomstuff.command.TelluriumsRandomStuffCommand;
import com.mikitellurium.telluriumsrandomstuff.config.ModCommonConfig;
import com.mikitellurium.telluriumsrandomstuff.enchantment.ModEnchantments;
import com.mikitellurium.telluriumsrandomstuff.fluid.ModFluidTypes;
import com.mikitellurium.telluriumsrandomstuff.fluid.ModFluids;
import com.mikitellurium.telluriumsrandomstuff.gui.ModMenuTypes;
import com.mikitellurium.telluriumsrandomstuff.gui.screen.ExtractorScreen;
import com.mikitellurium.telluriumsrandomstuff.gui.screen.SoulAnchorScreen;
import com.mikitellurium.telluriumsrandomstuff.gui.screen.SoulFurnaceScreen;
import com.mikitellurium.telluriumsrandomstuff.setup.ModCreativeTab;
import com.mikitellurium.telluriumsrandomstuff.item.ModItems;
import com.mikitellurium.telluriumsrandomstuff.jei.recipe.ModRecipes;
import com.mikitellurium.telluriumsrandomstuff.networking.ModMessages;
import com.mikitellurium.telluriumsrandomstuff.particle.ModParticles;
import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(TelluriumsRandomStuffMod.MOD_ID)
public class TelluriumsRandomStuffMod {

    public static final String MOD_ID = "telluriumsrandomstuff";
    private static final Logger LOGGER = LogUtils.getLogger();

    public TelluriumsRandomStuffMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModCreativeTab.register(modEventBus);
        ModFluidTypes.registerSoulLavaType(modEventBus);
        ModFluids.register(modEventBus);
        ModParticles.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModRecipes.register(modEventBus);
        ModEnchantments.register(modEventBus);
        ModMessages.register();
        ModCommonConfig.registerCommonConfig();

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::addCreativeTab);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        ModFluidInteractions.register();
        ModCauldronInteractions.register();
        ModDispenserBehaviours.register();
    }

    private void addCreativeTab(BuildCreativeModeTabContentsEvent event) {
        ItemStack soulHarvestingI = new ItemStack(Items.ENCHANTED_BOOK);
        ItemStack soulHarvestingII = soulHarvestingI.copy();
        ItemStack soulHarvestingIII = soulHarvestingI.copy();
        EnchantedBookItem.addEnchantment(soulHarvestingI, new EnchantmentInstance(ModEnchantments.SOUL_HARVESTING.get(), 1));
        EnchantedBookItem.addEnchantment(soulHarvestingII, new EnchantmentInstance(ModEnchantments.SOUL_HARVESTING.get(), 2));
        EnchantedBookItem.addEnchantment(soulHarvestingIII, new EnchantmentInstance(ModEnchantments.SOUL_HARVESTING.get(), 3));

        if (event.getTab() == ModCreativeTab.TAB_TELLURIUMSRANDOMSTUFF.get()) {
            event.accept(ModBlocks.GRATE_MAGMA_BLOCK);
            event.accept(ModBlocks.GRATE_SOUL_SAND);
            event.accept(ModBlocks.HYDRODYNAMIC_RAIL);
            event.accept(ModItems.SOUL_LAVA_BUCKET);
            event.accept(ModBlocks.SOUL_MAGMA_BLOCK);
            event.accept(ModBlocks.GRATE_SOUL_MAGMA_BLOCK);
            event.accept(ModBlocks.SOUL_FURNACE_BLOCK);
            event.accept(ModBlocks.SOUL_MAGMA_BRICKS);
            event.accept(ModBlocks.SOUL_MAGMA_BRICK_SLAB);
            event.accept(ModBlocks.SOUL_OBSIDIAN_BLOCK);
            event.accept(ModBlocks.SOUL_ANCHOR_BLOCK);
            event.accept(ModItems.MYSTIC_POTATO);
            event.accept(soulHarvestingI);
            event.accept(soulHarvestingII);
            event.accept(soulHarvestingIII);
            event.accept(ModBlocks.OPAL);
            event.accept(ModBlocks.OPAL_COBBLESTONE);
            event.accept(ModBlocks.OPAL_BRICKS);
            event.accept(ModBlocks.CUT_OPAL_BRICKS);
            event.accept(ModBlocks.CRACKED_OPAL_BRICKS);
            event.accept(ModBlocks.CRACKED_CUT_OPAL_BRICKS);
            event.accept(ModBlocks.CHISELED_OPAL_BRICKS);
            event.accept(ModBlocks.OPAL_SLAB);
            event.accept(ModBlocks.OPAL_COBBLESTONE_SLAB);
            event.accept(ModBlocks.OPAL_BRICK_SLAB);
            event.accept(ModBlocks.CUT_OPAL_BRICK_SLAB);
            event.accept(ModBlocks.CRACKED_OPAL_BRICK_SLAB);
            event.accept(ModBlocks.CRACKED_CUT_OPAL_BRICK_SLAB);
            event.accept(ModBlocks.OPAL_STAIRS);
            event.accept(ModBlocks.OPAL_COBBLESTONE_STAIRS);
            event.accept(ModBlocks.OPAL_BRICK_STAIRS);
            event.accept(ModBlocks.CUT_OPAL_BRICK_STAIRS);
            event.accept(ModBlocks.OPAL_COBBLESTONE_WALL);
            event.accept(ModBlocks.OPAL_BRICK_WALL);
            event.accept(ModBlocks.CUT_OPAL_BRICK_WALL);
            event.accept(ModBlocks.OPAL_PRESSURE_PLATE);
            event.accept(ModBlocks.OPAL_BUTTON);
            event.accept(ModBlocks.OPAL_CRYSTAL_ORE);
            event.accept(ModBlocks.OPAL_CRYSTAL_BLOCK);
            event.accept(ModBlocks.RAW_OPAL_CRYSTAL_BLOCK);
            event.accept(ModItems.RAW_OPAL_CRYSTAL);
            event.accept(ModItems.OPAL_CRYSTAL);
            event.accept(ModItems.OPAL_CRYSTAL_SHOVEL);
            event.accept(ModItems.OPAL_CRYSTAL_PICKAXE);
            event.accept(ModItems.OPAL_CRYSTAL_AXE);
            event.accept(ModItems.OPAL_CRYSTAL_HOE);
            event.accept(ModItems.OPAL_CRYSTAL_SWORD);
            event.accept(ModItems.FILTER);
            event.accept(ModBlocks.EXTRACTOR_BLOCK);
        }
    }

    @SubscribeEvent
    public void registerCommands(RegisterCommandsEvent event){
        TelluriumsRandomStuffCommand.register(event.getDispatcher());
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(ModMenuTypes.SOUL_FURNACE_MENU.get(), SoulFurnaceScreen::new);
            MenuScreens.register(ModMenuTypes.SOUL_ANCHOR_MENU.get(), SoulAnchorScreen::new);
            MenuScreens.register(ModMenuTypes.EXTRACTOR_MENU.get(), ExtractorScreen::new);
        }

    }

}

