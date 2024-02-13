package com.mikitellurium.telluriumsrandomstuff;

import com.mikitellurium.telluriumsrandomstuff.common.block.interaction.ModCauldronInteractions;
import com.mikitellurium.telluriumsrandomstuff.common.block.interaction.ModDispenserBehaviours;
import com.mikitellurium.telluriumsrandomstuff.common.block.interaction.ModFluidInteractions;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.registry.Registries;
import com.mikitellurium.telluriumsrandomstuff.setup.CommonSetup;
import com.mojang.logging.LogUtils;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(TelluriumsRandomStuffMod.MOD_ID)
public class TelluriumsRandomStuffMod {

    // todo rei and mei compatibility
    // todo colored soul fragments
    // todo update changelog
    // todo opal armor trims
    // todo change soul infused soul sand texture
    // todo add strider to soul lava goal
    // todo change all rl with FastLoc

    public static final String MOD_ID = "telluriumsrandomstuff";
    public static final Logger LOGGER = LogUtils.getLogger();

    public TelluriumsRandomStuffMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
        CommonSetup.registerForgeBusEvents();

        Registries.init(modEventBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ModFluidInteractions.register();
            ModCauldronInteractions.register();
            ModDispenserBehaviours.register();
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.BRIGHT_TORCHFLOWER.getId(), ModBlocks.POTTED_BRIGHT_TORCHFLOWER);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.SOUL_TORCHFLOWER.getId(), ModBlocks.POTTED_SOUL_TORCHFLOWER);
        });
    }

}

