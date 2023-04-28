package com.mikitellurium.telluriumsrandomstuff.setup;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.block.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.item.ModItems;
import com.mikitellurium.telluriumsrandomstuff.particle.ModParticles;
import com.mikitellurium.telluriumsrandomstuff.particle.custom.SoulLavaDripParticle;
import com.mikitellurium.telluriumsrandomstuff.util.ColorsUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TelluriumsRandomStuffMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        event.getBlockColors().register((state, world, pos, tintIndex) -> {
                    if (world != null && pos != null) {
                        if (ColorsUtil.isMaterialOpalium(state.getBlock().asItem().getDefaultInstance())) {
                            return ColorsUtil.getOpaliumRainbowColor(pos, 0.8f, 1.0f);
                        }

                        return ColorsUtil.getOpalRainbowColor(pos, 0.65f, 0.9f);
                    }
                    return 0xFFFFFF;},
                ModBlocks.OPAL.get(), ModBlocks.OPAL_COBBLESTONE.get(), ModBlocks.OPAL_BRICKS.get(),
                ModBlocks.CUT_OPAL_BRICKS.get(), ModBlocks.CHISELED_OPAL_BRICKS.get(), ModBlocks.CRACKED_OPAL_BRICKS.get(),
                ModBlocks.CRACKED_CUT_OPAL_BRICKS.get(), ModBlocks.OPAL_SLAB.get(), ModBlocks.OPAL_COBBLESTONE_SLAB.get(),
                ModBlocks.OPAL_BRICK_SLAB.get(), ModBlocks.CUT_OPAL_BRICK_SLAB.get(), ModBlocks.CRACKED_OPAL_BRICK_SLAB.get(),
                ModBlocks.CRACKED_CUT_OPAL_BRICK_SLAB.get(), ModBlocks.OPAL_STAIRS.get(), ModBlocks.OPAL_COBBLESTONE_STAIRS.get(),
                ModBlocks.OPAL_BRICK_STAIRS.get(), ModBlocks.CUT_OPAL_BRICK_STAIRS.get(), ModBlocks.OPAL_COBBLESTONE_WALL.get(),
                ModBlocks.OPAL_BRICK_WALL.get(), ModBlocks.CUT_OPAL_BRICK_WALL.get(), ModBlocks.OPAL_PRESSURE_PLATE.get(),
                ModBlocks.OPAL_BUTTON.get(), ModBlocks.OPALIUM_ORE.get(), ModBlocks.RAW_OPALIUM_BLOCK.get(),
                ModBlocks.OPALIUM_BLOCK.get());
    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.getItemColors().register((stack, tintIndex) -> {
            Player player = Minecraft.getInstance().player;
            ItemEntity itemEntity = (ItemEntity) stack.getEntityRepresentation();
                    // If the item is dropped in the world
                    if (itemEntity != null) {
                        if (ColorsUtil.isMaterialOpalium(stack)) {
                            return tintIndex == 0 ? ColorsUtil.getOpaliumRainbowColor(
                                    itemEntity.getOnPos(), 0.8f, 1.0f) : 0xFFFFFF;
                        }

                        return ColorsUtil.getOpalRainbowColor(itemEntity.getOnPos(), 0.65f, 0.9f);
                    // If the item is inside a inventory
                    } else if (player != null) {
                        if (ColorsUtil.isMaterialOpalium(stack)) {
                            return tintIndex == 0 ? ColorsUtil.getOpaliumRainbowColor(
                                    player.getOnPos(), 0.8f, 1.0f) : 0xFFFFFF;
                        }

                        return ColorsUtil.getOpalRainbowColor(player.getOnPos(), 0.65f, 0.9f);
                    }
                    return 0xFFFFFF;},
                ModBlocks.OPAL.get(), ModBlocks.OPAL_COBBLESTONE.get(), ModBlocks.OPAL_BRICKS.get(),
                ModBlocks.CUT_OPAL_BRICKS.get(), ModBlocks.CHISELED_OPAL_BRICKS.get(), ModBlocks.CRACKED_OPAL_BRICKS.get(),
                ModBlocks.CRACKED_CUT_OPAL_BRICKS.get(), ModBlocks.OPAL_SLAB.get(), ModBlocks.OPAL_COBBLESTONE_SLAB.get(),
                ModBlocks.OPAL_BRICK_SLAB.get(), ModBlocks.CUT_OPAL_BRICK_SLAB.get(), ModBlocks.CRACKED_OPAL_BRICK_SLAB.get(),
                ModBlocks.CRACKED_CUT_OPAL_BRICK_SLAB.get(), ModBlocks.OPAL_STAIRS.get(), ModBlocks.OPAL_COBBLESTONE_STAIRS.get(),
                ModBlocks.OPAL_BRICK_STAIRS.get(), ModBlocks.CUT_OPAL_BRICK_STAIRS.get(), ModBlocks.OPAL_COBBLESTONE_WALL.get(),
                ModBlocks.OPAL_BRICK_WALL.get(), ModBlocks.CUT_OPAL_BRICK_WALL.get(), ModBlocks.OPAL_PRESSURE_PLATE.get(),
                ModBlocks.OPAL_BUTTON.get(), ModBlocks.OPALIUM_ORE.get(), ModItems.RAW_OPALIUM.get(),
                ModItems.OPALIUM_INGOT.get(), ModBlocks.RAW_OPALIUM_BLOCK.get(), ModBlocks.OPALIUM_BLOCK.get(),
                ModItems.OPALIUM_SWORD.get(), ModItems.OPALIUM_PICKAXE.get(), ModItems.OPALIUM_SHOVEL.get(),
                ModItems.OPALIUM_AXE.get(), ModItems.OPALIUM_HOE.get());
    }

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.SOUL_LAVA_FALL.get(), SoulLavaDripParticle.SoulLavaFallProvider::new);
        event.registerSpriteSet(ModParticles.SOUL_LAVA_HANG.get(), SoulLavaDripParticle.SoulLavaHangProvider::new);
        event.registerSpriteSet(ModParticles.SOUL_LAVA_LAND.get(), SoulLavaDripParticle.SoulLavaLandProvider::new);
    }

}
