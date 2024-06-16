package com.mikitellurium.telluriumsrandomstuff.setup;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.common.block.AlchemixerBlock;
import com.mikitellurium.telluriumsrandomstuff.common.block.CustomBubbleColumnBlock;
import com.mikitellurium.telluriumsrandomstuff.common.block.SoulAnchorBlock;
import com.mikitellurium.telluriumsrandomstuff.common.command.LavaGooglesCommand;
import com.mikitellurium.telluriumsrandomstuff.common.command.SoulAnchorCommand;
import com.mikitellurium.telluriumsrandomstuff.common.event.LootEvents;
import com.mikitellurium.telluriumsrandomstuff.common.item.GrapplingHookItem;
import com.mikitellurium.telluriumsrandomstuff.common.item.LavaGooglesItem;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TelluriumsRandomStuffMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonSetup {

    public static void registerForgeBusEvents() {
        MinecraftForge.EVENT_BUS.addListener(CommonSetup::registerCommands);
        MinecraftForge.EVENT_BUS.register(CustomBubbleColumnBlock.class);
        MinecraftForge.EVENT_BUS.register(SoulAnchorBlock.class);
        MinecraftForge.EVENT_BUS.register(LavaGooglesItem.class);
        MinecraftForge.EVENT_BUS.register(GrapplingHookItem.class);
        MinecraftForge.EVENT_BUS.register(LootEvents.class);
        MinecraftForge.EVENT_BUS.register(AlchemixerBlock.class);
    }

    private static void registerCommands(RegisterCommandsEvent event){
        SoulAnchorCommand.register(event.getDispatcher());
        LavaGooglesCommand.register(event.getDispatcher());
    }

}
