package com.mikitellurium.telluriumsrandomstuff.setup;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.block.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.util.ColorsUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TelluriumsRandomStuffMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        event.getBlockColors().register((state, world, pos, tintIndex) ->
                        world != null && pos != null ? ColorsUtil.getRainbowColor(pos) : 0xFFFFFF,
                ModBlocks.OPAL.get(), ModBlocks.OPAL_COBBLESTONE.get(), ModBlocks.OPAL_BRICKS.get(),
                ModBlocks.CUT_OPAL_BRICKS.get(), ModBlocks.CHISELED_OPAL_BRICKS.get(), ModBlocks.CRACKED_OPAL_BRICKS.get(),
                ModBlocks.CRACKED_CUT_OPAL_BRICKS.get(), ModBlocks.OPAL_SLAB.get(), ModBlocks.OPAL_COBBLESTONE_SLAB.get(),
                ModBlocks.OPAL_BRICK_SLAB.get(), ModBlocks.CUT_OPAL_BRICK_SLAB.get(), ModBlocks.CRACKED_OPAL_BRICK_SLAB.get(),
                ModBlocks.CRACKED_CUT_OPAL_BRICK_SLAB.get(), ModBlocks.OPAL_STAIRS.get(), ModBlocks.OPAL_COBBLESTONE_STAIRS.get(),
                ModBlocks.OPAL_BRICK_STAIRS.get(), ModBlocks.CUT_OPAL_BRICK_STAIRS.get(), ModBlocks.OPAL_COBBLESTONE_WALL.get(),
                ModBlocks.OPAL_BRICK_WALL.get(), ModBlocks.CUT_OPAL_BRICK_WALL.get(), ModBlocks.OPAL_PRESSURE_PLATE.get(),
                ModBlocks.OPAL_BUTTON.get());
    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.getItemColors().register((stack, tintIndex) -> {
                    Player player = Minecraft.getInstance().player;
                    if (player != null) {
                        BlockPos pos = player.getOnPos();
                        return ColorsUtil.getRainbowColor(pos);
                    }
                    return 0xFFFFFF;},
                ModBlocks.OPAL.get(), ModBlocks.OPAL_COBBLESTONE.get(), ModBlocks.OPAL_BRICKS.get(),
                ModBlocks.CUT_OPAL_BRICKS.get(), ModBlocks.CHISELED_OPAL_BRICKS.get(), ModBlocks.CRACKED_OPAL_BRICKS.get(),
                ModBlocks.CRACKED_CUT_OPAL_BRICKS.get(), ModBlocks.OPAL_SLAB.get(), ModBlocks.OPAL_COBBLESTONE_SLAB.get(),
                ModBlocks.OPAL_BRICK_SLAB.get(), ModBlocks.CUT_OPAL_BRICK_SLAB.get(), ModBlocks.CRACKED_OPAL_BRICK_SLAB.get(),
                ModBlocks.CRACKED_CUT_OPAL_BRICK_SLAB.get(), ModBlocks.OPAL_STAIRS.get(), ModBlocks.OPAL_COBBLESTONE_STAIRS.get(),
                ModBlocks.OPAL_BRICK_STAIRS.get(), ModBlocks.CUT_OPAL_BRICK_STAIRS.get(), ModBlocks.OPAL_COBBLESTONE_WALL.get(),
                ModBlocks.OPAL_BRICK_WALL.get(), ModBlocks.CUT_OPAL_BRICK_WALL.get(), ModBlocks.OPAL_PRESSURE_PLATE.get(),
                ModBlocks.OPAL_BUTTON.get());
    }

}
