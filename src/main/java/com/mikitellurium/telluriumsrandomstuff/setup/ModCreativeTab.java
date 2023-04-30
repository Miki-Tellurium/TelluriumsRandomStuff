package com.mikitellurium.telluriumsrandomstuff.setup;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.block.ModBlocks;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TelluriumsRandomStuffMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCreativeTab {

    public static CreativeModeTab TAB_TELLURIUMSRANDOMSTUFF;

    @SubscribeEvent
    public static void registerCreativeModeTabs(CreativeModeTabEvent.Register event) {
        TAB_TELLURIUMSRANDOMSTUFF = event.registerCreativeModeTab(
                new ResourceLocation(TelluriumsRandomStuffMod.MOD_ID, "telluriumsrandomstuff_creative_tab"),
                builder -> builder.icon(() -> new ItemStack(ModBlocks.SOUL_MAGMA_BLOCK.get()))
                        .title(Component.translatable("creativemodetab.telluriumsrandomstuff_creative_tab")));
    }

}