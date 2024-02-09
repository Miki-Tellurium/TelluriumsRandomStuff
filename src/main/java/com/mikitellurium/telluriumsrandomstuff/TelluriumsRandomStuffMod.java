package com.mikitellurium.telluriumsrandomstuff;

import com.mikitellurium.telluriumsrandomstuff.common.command.LavaGooglesCommand;
import com.mikitellurium.telluriumsrandomstuff.registry.*;
import com.mikitellurium.telluriumsrandomstuff.common.block.interaction.ModCauldronInteractions;
import com.mikitellurium.telluriumsrandomstuff.common.block.interaction.ModDispenserBehaviours;
import com.mikitellurium.telluriumsrandomstuff.common.block.interaction.ModFluidInteractions;
import com.mikitellurium.telluriumsrandomstuff.common.command.SoulAnchorCommand;
import com.mikitellurium.telluriumsrandomstuff.setup.CommonSetup;
import com.mojang.logging.LogUtils;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
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
        ModFluidInteractions.register();
        ModCauldronInteractions.register();
        ModDispenserBehaviours.register();
        ((FlowerPotBlock)Blocks.FLOWER_POT).addPlant(ModBlocks.BRIGHT_TORCHFLOWER.getId(), ModBlocks.POTTED_BRIGHT_TORCHFLOWER);
        ((FlowerPotBlock)Blocks.FLOWER_POT).addPlant(ModBlocks.SOUL_TORCHFLOWER.getId(), ModBlocks.POTTED_SOUL_TORCHFLOWER);
    }


    // todo move command registration
    @SubscribeEvent
    public void registerCommands(RegisterCommandsEvent event){
        SoulAnchorCommand.register(event.getDispatcher());
        LavaGooglesCommand.register(event.getDispatcher());
    }

}

