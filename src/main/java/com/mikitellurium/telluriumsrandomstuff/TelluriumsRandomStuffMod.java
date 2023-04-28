package com.mikitellurium.telluriumsrandomstuff;

import com.mikitellurium.telluriumsrandomstuff.block.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.block.interaction.ModCauldronInteractions;
import com.mikitellurium.telluriumsrandomstuff.block.interaction.ModDispenserBehaviours;
import com.mikitellurium.telluriumsrandomstuff.block.interaction.ModFluidInteractions;
import com.mikitellurium.telluriumsrandomstuff.blockentity.ModBlockEntities;
import com.mikitellurium.telluriumsrandomstuff.config.ModCommonConfig;
import com.mikitellurium.telluriumsrandomstuff.fluid.ModFluidTypes;
import com.mikitellurium.telluriumsrandomstuff.fluid.ModFluids;
import com.mikitellurium.telluriumsrandomstuff.gui.ModMenuTypes;
import com.mikitellurium.telluriumsrandomstuff.gui.SoulFurnaceGui;
import com.mikitellurium.telluriumsrandomstuff.item.GrateBlocksCreativeTab;
import com.mikitellurium.telluriumsrandomstuff.item.ModItems;
import com.mikitellurium.telluriumsrandomstuff.jei.recipe.ModRecipes;
import com.mikitellurium.telluriumsrandomstuff.networking.ModMessages;
import com.mikitellurium.telluriumsrandomstuff.particle.ModParticles;
import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
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
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(eventBus);
        ModBlocks.register(eventBus);
        ModBlockEntities.register(eventBus);
        ModFluidTypes.registerSoulLavaType(eventBus);
        ModFluids.register(eventBus);
        ModParticles.register(eventBus);
        ModMenuTypes.register(eventBus);
        ModRecipes.register(eventBus);
        ModMessages.register();
        ModCommonConfig.registerCommonConfig();

        eventBus.addListener(this::commonSetup);
        eventBus.addListener(this::addCreative);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        ModFluidInteractions.register();
        ModCauldronInteractions.register();
        ModDispenserBehaviours.register();
    }

    private void addCreative(CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == GrateBlocksCreativeTab.TAB_TELLURIUMSRANDOMSTUFF) {
            event.accept(ModBlocks.GRATE_MAGMA_BLOCK);
            event.accept(ModBlocks.GRATE_SOUL_SAND);
            event.accept(ModBlocks.HYDRODYNAMIC_RAIL);
            event.accept(ModItems.SOUL_LAVA_BUCKET);
            event.accept(ModBlocks.SOUL_MAGMA_BLOCK);
            event.accept(ModBlocks.GRATE_SOUL_MAGMA_BLOCK);
            event.accept(ModBlocks.SOUL_FURNACE);
            event.accept(ModBlocks.SOUL_MAGMA_BRICKS);
            event.accept(ModBlocks.SOUL_MAGMA_BRICK_SLAB);
            event.accept(ModItems.MYSTIC_POTATO);
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
            event.accept(ModBlocks.OPALIUM_BLOCK);
            event.accept(ModBlocks.OPALIUM_ORE);
            event.accept(ModBlocks.RAW_OPALIUM_BLOCK);
            event.accept(ModItems.RAW_OPALIUM);
            event.accept(ModItems.OPALIUM_INGOT);
            event.accept(ModItems.OPALIUM_SHOVEL);
            event.accept(ModItems.OPALIUM_PICKAXE);
            event.accept(ModItems.OPALIUM_AXE);
            event.accept(ModItems.OPALIUM_HOE);
            event.accept(ModItems.OPALIUM_SWORD);
        }
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(ModMenuTypes.SOUL_FURNACE_MENU.get(), SoulFurnaceGui::new);
        }
    }

}

