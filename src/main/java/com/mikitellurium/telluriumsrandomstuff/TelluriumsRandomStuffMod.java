package com.mikitellurium.telluriumsrandomstuff;

import com.mikitellurium.telluriumsrandomstuff.block.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.fluid.ModFluidTypes;
import com.mikitellurium.telluriumsrandomstuff.fluid.ModFluids;
import com.mikitellurium.telluriumsrandomstuff.item.GrateBlocksCreativeTab;
import com.mikitellurium.telluriumsrandomstuff.item.ModItems;
import com.mikitellurium.telluriumsrandomstuff.particle.ModParticles;
import com.mojang.logging.LogUtils;
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

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TelluriumsRandomStuffMod.MOD_ID)
public class TelluriumsRandomStuffMod {
    public static final String MOD_ID = "telluriumsrandomstuff";
    private static final Logger LOGGER = LogUtils.getLogger();

    public TelluriumsRandomStuffMod() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(eventBus);
        ModBlocks.register(eventBus);
        ModFluidTypes.registerSoulLavaType(eventBus);
        ModFluids.register(eventBus);
        ModParticles.register(eventBus);

        eventBus.addListener(this::commonSetup);
        eventBus.addListener(this::addCreative);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    private void addCreative(CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == GrateBlocksCreativeTab.TAB_TELLURIUMSRANDOMSTUFF) {
            event.accept(ModBlocks.GRATE_MAGMA_BLOCK.get());
            event.accept(ModBlocks.GRATE_SOUL_SAND.get());
            event.accept(ModBlocks.HYDRODYNAMIC_RAIL.get());
            event.accept(ModItems.SOUL_LAVA_BUCKET.get());
            event.accept(ModBlocks.OPAL.get());
            event.accept(ModBlocks.OPAL_COBBLESTONE.get());
            event.accept(ModBlocks.OPAL_BRICKS.get());
            event.accept(ModBlocks.CUT_OPAL_BRICKS.get());
            event.accept(ModBlocks.CRACKED_OPAL_BRICKS.get());
            event.accept(ModBlocks.CRACKED_CUT_OPAL_BRICKS.get());
            event.accept(ModBlocks.CHISELED_OPAL_BRICKS.get());
            event.accept(ModBlocks.OPAL_SLAB.get());
            event.accept(ModBlocks.OPAL_COBBLESTONE_SLAB.get());
            event.accept(ModBlocks.OPAL_BRICK_SLAB.get());
            event.accept(ModBlocks.CUT_OPAL_BRICK_SLAB.get());
            event.accept(ModBlocks.CRACKED_OPAL_BRICK_SLAB.get());
            event.accept(ModBlocks.CRACKED_CUT_OPAL_BRICK_SLAB.get());
            event.accept(ModBlocks.OPAL_STAIRS.get());
            event.accept(ModBlocks.OPAL_COBBLESTONE_STAIRS.get());
            event.accept(ModBlocks.OPAL_BRICK_STAIRS.get());
            event.accept(ModBlocks.CUT_OPAL_BRICK_STAIRS.get());
            event.accept(ModBlocks.OPAL_COBBLESTONE_WALL.get());
            event.accept(ModBlocks.OPAL_BRICK_WALL.get());
            event.accept(ModBlocks.CUT_OPAL_BRICK_WALL.get());
            event.accept(ModBlocks.OPAL_PRESSURE_PLATE.get());
            event.accept(ModBlocks.OPAL_BUTTON.get());
        }
    }

    // Registering static method with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

        }
    }

}
