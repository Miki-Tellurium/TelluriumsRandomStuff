package com.mikitellurium.telluriumsrandomstuff.setup;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.client.gui.screen.ExtractorScreen;
import com.mikitellurium.telluriumsrandomstuff.client.gui.screen.SoulAnchorScreen;
import com.mikitellurium.telluriumsrandomstuff.client.gui.screen.SoulFurnaceScreen;
import com.mikitellurium.telluriumsrandomstuff.client.render.LavaGooglesLayer;
import com.mikitellurium.telluriumsrandomstuff.client.render.LavaGooglesModel;
import com.mikitellurium.telluriumsrandomstuff.client.render.LavaGooglesOverlay;
import com.mikitellurium.telluriumsrandomstuff.common.content.particle.SoulLavaDripParticle;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import com.mikitellurium.telluriumsrandomstuff.registry.ModMenuTypes;
import com.mikitellurium.telluriumsrandomstuff.registry.ModParticles;
import com.mikitellurium.telluriumsrandomstuff.util.ColorsUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LightLayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = TelluriumsRandomStuffMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        MenuScreens.register(ModMenuTypes.SOUL_FURNACE_MENU.get(), SoulFurnaceScreen::new);
        MenuScreens.register(ModMenuTypes.SOUL_ANCHOR_MENU.get(), SoulAnchorScreen::new);
        MenuScreens.register(ModMenuTypes.EXTRACTOR_MENU.get(), ExtractorScreen::new);
    }

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        event.getBlockColors().register((state, world, pos, tintIndex) -> {
                    if (world != null && pos != null) {
                        return ColorsUtil.getMaterialColor(state.getBlock().asItem().getDefaultInstance(),
                                tintIndex, ColorsUtil.getHighestLightLevel(world, pos));
                    }
                    return 0xFFFFFF;},
                ModBlocks.OPAL.get(), ModBlocks.OPAL_COBBLESTONE.get(), ModBlocks.OPAL_BRICKS.get(),
                ModBlocks.CUT_OPAL_BRICKS.get(), ModBlocks.CHISELED_OPAL_BRICKS.get(), ModBlocks.CRACKED_OPAL_BRICKS.get(),
                ModBlocks.CRACKED_CUT_OPAL_BRICKS.get(), ModBlocks.OPAL_SLAB.get(), ModBlocks.OPAL_COBBLESTONE_SLAB.get(),
                ModBlocks.OPAL_BRICK_SLAB.get(), ModBlocks.CUT_OPAL_BRICK_SLAB.get(), ModBlocks.CRACKED_OPAL_BRICK_SLAB.get(),
                ModBlocks.CRACKED_CUT_OPAL_BRICK_SLAB.get(), ModBlocks.OPAL_STAIRS.get(), ModBlocks.OPAL_COBBLESTONE_STAIRS.get(),
                ModBlocks.OPAL_BRICK_STAIRS.get(), ModBlocks.CUT_OPAL_BRICK_STAIRS.get(), ModBlocks.OPAL_COBBLESTONE_WALL.get(),
                ModBlocks.OPAL_BRICK_WALL.get(), ModBlocks.CUT_OPAL_BRICK_WALL.get(), ModBlocks.OPAL_PRESSURE_PLATE.get(),
                ModBlocks.OPAL_BUTTON.get(), ModBlocks.OPAL_CRYSTAL_ORE.get(), ModBlocks.RAW_OPAL_CRYSTAL_BLOCK.get(),
                ModBlocks.OPAL_CRYSTAL_BLOCK.get());
    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.getItemColors().register((stack, tintIndex) -> {
                    // Lava googles
                    if (stack.is(ModItems.LAVA_GOOGLES.get())) {
                        return ColorsUtil.getGooglesColor(stack, tintIndex);
                    }

                    // Opal blocks
                    BlockPos pos;

                        if (stack.isFramed()) { //Check if the item is in item frame
                           pos = stack.getFrame().getPos();
                        } else if (stack.getEntityRepresentation() != null) { // Check if the item is dropped in the world
                            pos = stack.getEntityRepresentation().getOnPos();
                        } else {
                            Player player = Minecraft.getInstance().player;
                            if (player != null) {
                                pos = player.getOnPos();
                            } else {
                                pos = new BlockPos(0, 0, 0);
                            }
                        }
                    return ColorsUtil.getMaterialColor(stack, tintIndex,
                            Minecraft.getInstance().level.getBrightness(LightLayer.BLOCK, pos.above())); },
                ModBlocks.OPAL.get(), ModBlocks.OPAL_COBBLESTONE.get(), ModBlocks.OPAL_BRICKS.get(),
                ModBlocks.CUT_OPAL_BRICKS.get(), ModBlocks.CHISELED_OPAL_BRICKS.get(), ModBlocks.CRACKED_OPAL_BRICKS.get(),
                ModBlocks.CRACKED_CUT_OPAL_BRICKS.get(), ModBlocks.OPAL_SLAB.get(), ModBlocks.OPAL_COBBLESTONE_SLAB.get(),
                ModBlocks.OPAL_BRICK_SLAB.get(), ModBlocks.CUT_OPAL_BRICK_SLAB.get(), ModBlocks.CRACKED_OPAL_BRICK_SLAB.get(),
                ModBlocks.CRACKED_CUT_OPAL_BRICK_SLAB.get(), ModBlocks.OPAL_STAIRS.get(), ModBlocks.OPAL_COBBLESTONE_STAIRS.get(),
                ModBlocks.OPAL_BRICK_STAIRS.get(), ModBlocks.CUT_OPAL_BRICK_STAIRS.get(), ModBlocks.OPAL_COBBLESTONE_WALL.get(),
                ModBlocks.OPAL_BRICK_WALL.get(), ModBlocks.CUT_OPAL_BRICK_WALL.get(), ModBlocks.OPAL_PRESSURE_PLATE.get(),
                ModBlocks.OPAL_BUTTON.get(), ModBlocks.OPAL_CRYSTAL_ORE.get(), ModItems.RAW_OPAL_CRYSTAL.get(),
                ModItems.OPAL_CRYSTAL.get(), ModBlocks.RAW_OPAL_CRYSTAL_BLOCK.get(), ModBlocks.OPAL_CRYSTAL_BLOCK.get(),
                ModItems.OPAL_CRYSTAL_SWORD.get(), ModItems.OPAL_CRYSTAL_PICKAXE.get(), ModItems.OPAL_CRYSTAL_SHOVEL.get(),
                ModItems.OPAL_CRYSTAL_AXE.get(), ModItems.OPAL_CRYSTAL_HOE.get(), ModItems.LAVA_GOOGLES.get());
    }

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.SOUL_LAVA_FALL.get(), SoulLavaDripParticle.SoulLavaFallProvider::new);
        event.registerSpriteSet(ModParticles.SOUL_LAVA_HANG.get(), SoulLavaDripParticle.SoulLavaHangProvider::new);
        event.registerSpriteSet(ModParticles.SOUL_LAVA_LAND.get(), SoulLavaDripParticle.SoulLavaLandProvider::new);
    }

    @SubscribeEvent
    public static void registerOverlays(RegisterGuiOverlaysEvent event) {
        event.registerBelowAll(TelluriumsRandomStuffMod.MOD_ID + "_lava_googles_overlay", new LavaGooglesOverlay());
    }

    @SuppressWarnings("unchecked")
    @SubscribeEvent
    public static void addLayers(EntityRenderersEvent.AddLayers event) {
        for (EntityType<?> entityType : ForgeRegistries.ENTITY_TYPES) {
            try {
                if (entityType == EntityType.PLAYER) {
                    continue; // Exclude player, we add them later
                }

            LivingEntityRenderer<LivingEntity, HumanoidModel<LivingEntity>> renderer =
                    event.getRenderer((EntityType<LivingEntity>) entityType);
                addLayerToRenderer(renderer, entityType, event.getContext().getModelSet());
            } catch (ClassCastException e) {
                // Non living entities can't be casted to LivingEntity,
                // no need to do anything
            }
        }
        // Player
        event.getSkins().forEach((skin) -> {
            PlayerRenderer playerRenderer = event.getSkin(skin);
            if (playerRenderer != null) {
                playerRenderer.addLayer(new LavaGooglesLayer<>(playerRenderer, event.getContext().getModelSet()));
                TelluriumsRandomStuffMod.LOGGER.info("Render layer added to player model: " + skin);
            } else {
                TelluriumsRandomStuffMod.LOGGER.error("Could not apply render layer to player model: " + skin);
            }
        });
    }

    @SubscribeEvent
    public static void addModels(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(LavaGooglesModel.LAVA_GOOGLES, LavaGooglesModel::createLayerDefinition);
    }

    private static void addLayerToRenderer(LivingEntityRenderer<LivingEntity, HumanoidModel<LivingEntity>> renderer,
                                           EntityType<?> entityType, EntityModelSet modelSet) {
        try {
            if (renderer instanceof HumanoidMobRenderer || entityType == EntityType.ARMOR_STAND ||
                    entityType == EntityType.GIANT) {
                renderer.addLayer(new LavaGooglesLayer<>(renderer, modelSet));
                TelluriumsRandomStuffMod.LOGGER.info("Render layer correctly added to " + entityType.toShortString());
            }
        } catch (Exception e) {
            TelluriumsRandomStuffMod.LOGGER.error("Could not add layer to " + entityType.toShortString());
        }
    }

}
