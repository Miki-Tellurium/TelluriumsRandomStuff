package com.mikitellurium.telluriumsrandomstuff.setup;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.client.blockentity.ItemPedestalRenderer;
import com.mikitellurium.telluriumsrandomstuff.client.entity.layer.LavaGooglesLayer;
import com.mikitellurium.telluriumsrandomstuff.client.entity.model.GrapplingHookModel;
import com.mikitellurium.telluriumsrandomstuff.client.entity.model.LavaGooglesModel;
import com.mikitellurium.telluriumsrandomstuff.client.entity.render.GrapplingHookRenderer;
import com.mikitellurium.telluriumsrandomstuff.client.gui.screen.ExtractorScreen;
import com.mikitellurium.telluriumsrandomstuff.client.gui.screen.SoulAnchorScreen;
import com.mikitellurium.telluriumsrandomstuff.client.gui.screen.SoulFurnaceScreen;
import com.mikitellurium.telluriumsrandomstuff.client.gui.screen.SoulInfuserScreen;
import com.mikitellurium.telluriumsrandomstuff.common.particle.SoulLavaDripParticle;
import com.mikitellurium.telluriumsrandomstuff.registry.*;
import com.mikitellurium.telluriumsrandomstuff.util.ColorsUtil;
import com.mikitellurium.telluriumsrandomstuff.util.LevelUtils;
import com.mikitellurium.telluriumsrandomstuff.util.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.entity.ArmorStandRenderer;
import net.minecraft.client.renderer.entity.GiantMobRenderer;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LightLayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = TelluriumsRandomStuffMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(ModMenuTypes.SOUL_FURNACE_MENU.get(), SoulFurnaceScreen::new);
            MenuScreens.register(ModMenuTypes.SOUL_ANCHOR_MENU.get(), SoulAnchorScreen::new);
            MenuScreens.register(ModMenuTypes.EXTRACTOR_MENU.get(), ExtractorScreen::new);
            MenuScreens.register(ModMenuTypes.SOUL_INFUSER_MENU.get(), SoulInfuserScreen::new);
            ModItemProperties.register();
        });
    }

    @SubscribeEvent
    public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.ITEM_PEDESTAL.get(), ItemPedestalRenderer::new);
    }

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        // Opal crystals
        event.register((state, level, pos, tintIndex) -> ColorsUtil.getOpalCrystalColor(tintIndex,
                        LevelUtils.getHighestLightLevel(level, pos)
                ),
                ModBlocks.RAW_OPAL_CRYSTAL_BLOCK.get(), ModBlocks.OPAL_CRYSTAL_BLOCK.get());
        // Opal stones
        event.getBlockColors().register((state, level, pos, tintIndex) -> ColorsUtil.getOpalStoneColor(
                        LevelUtils.getHighestLightLevel(level, pos)
                ),
                ModBlocks.OPAL.get(), ModBlocks.OPAL_COBBLESTONE.get(), ModBlocks.OPAL_BRICKS.get(),
                ModBlocks.CUT_OPAL_BRICKS.get(), ModBlocks.CHISELED_OPAL_BRICKS.get(), ModBlocks.CRACKED_OPAL_BRICKS.get(),
                ModBlocks.CRACKED_CUT_OPAL_BRICKS.get(), ModBlocks.OPAL_SLAB.get(), ModBlocks.OPAL_COBBLESTONE_SLAB.get(),
                ModBlocks.OPAL_BRICK_SLAB.get(), ModBlocks.CUT_OPAL_BRICK_SLAB.get(), ModBlocks.CRACKED_OPAL_BRICK_SLAB.get(),
                ModBlocks.CRACKED_CUT_OPAL_BRICK_SLAB.get(), ModBlocks.OPAL_STAIRS.get(), ModBlocks.OPAL_COBBLESTONE_STAIRS.get(),
                ModBlocks.OPAL_BRICK_STAIRS.get(), ModBlocks.CUT_OPAL_BRICK_STAIRS.get(), ModBlocks.OPAL_COBBLESTONE_WALL.get(),
                ModBlocks.OPAL_BRICK_WALL.get(), ModBlocks.CUT_OPAL_BRICK_WALL.get(), ModBlocks.OPAL_PRESSURE_PLATE.get(),
                ModBlocks.OPAL_BUTTON.get(), ModBlocks.OPAL_CRYSTAL_ORE.get(), ModBlocks.OPAL_ITEM_PEDESTAL.get(),
                ModBlocks.OPAL_BRICK_ITEM_PEDESTAL.get(), ModBlocks.CUT_OPAL_BRICK_ITEM_PEDESTAL.get());
    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register(ColorsUtil::getGooglesColor, ModItems.LAVA_GOOGLES.get());
        // Opal crystals
        event.register((stack, tintIndex) -> ColorsUtil.getOpalCrystalColor(tintIndex,
                                Minecraft.getInstance().level.getBrightness(LightLayer.BLOCK, getColorPos(stack))
                        ),
                ModItems.OPAL_CRYSTAL.get(), ModItems.RAW_OPAL_CRYSTAL.get(), ModItems.OPAL_CRYSTAL_AXE.get(),
                ModItems.OPAL_CRYSTAL_SHOVEL.get(), ModItems.OPAL_CRYSTAL_HOE.get(), ModItems.OPAL_CRYSTAL_PICKAXE.get(),
                ModItems.OPAL_CRYSTAL_SWORD.get(), ModBlocks.RAW_OPAL_CRYSTAL_BLOCK.get(), ModBlocks.OPAL_CRYSTAL_BLOCK.get());
        // Opal stones
        event.register((stack, tintIndex) -> ColorsUtil.getOpalStoneColor(
                        Minecraft.getInstance().level.getBrightness(LightLayer.BLOCK, getColorPos(stack))
                ),
                ModBlocks.OPAL.get(), ModBlocks.OPAL_COBBLESTONE.get(), ModBlocks.OPAL_BRICKS.get(),
                ModBlocks.CUT_OPAL_BRICKS.get(), ModBlocks.CHISELED_OPAL_BRICKS.get(), ModBlocks.CRACKED_OPAL_BRICKS.get(),
                ModBlocks.CRACKED_CUT_OPAL_BRICKS.get(), ModBlocks.OPAL_SLAB.get(), ModBlocks.OPAL_COBBLESTONE_SLAB.get(),
                ModBlocks.OPAL_BRICK_SLAB.get(), ModBlocks.CUT_OPAL_BRICK_SLAB.get(), ModBlocks.CRACKED_OPAL_BRICK_SLAB.get(),
                ModBlocks.CRACKED_CUT_OPAL_BRICK_SLAB.get(), ModBlocks.OPAL_STAIRS.get(), ModBlocks.OPAL_COBBLESTONE_STAIRS.get(),
                ModBlocks.OPAL_BRICK_STAIRS.get(), ModBlocks.CUT_OPAL_BRICK_STAIRS.get(), ModBlocks.OPAL_COBBLESTONE_WALL.get(),
                ModBlocks.OPAL_BRICK_WALL.get(), ModBlocks.CUT_OPAL_BRICK_WALL.get(), ModBlocks.OPAL_PRESSURE_PLATE.get(),
                ModBlocks.OPAL_BUTTON.get(), ModBlocks.OPAL_CRYSTAL_ORE.get(), ModBlocks.OPAL_ITEM_PEDESTAL.get(),
                ModBlocks.OPAL_BRICK_ITEM_PEDESTAL.get(), ModBlocks.CUT_OPAL_BRICK_ITEM_PEDESTAL.get());
    }

    private static BlockPos getColorPos(ItemStack stack) {
        BlockPos pos = BlockPos.ZERO;

        if (stack.isFramed()) { // Check if the item is in item frame
            pos = stack.getFrame().getPos();
        } else if (stack.getEntityRepresentation() != null) { // Check if the item is dropped in the world
            pos = stack.getEntityRepresentation().getOnPos();
        } else {
            Player player = Minecraft.getInstance().player;
            if (player != null) {
                pos = player.getOnPos();
            }
        }
        return pos.above();
    }

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.SOUL_LAVA_FALL.get(), SoulLavaDripParticle.SoulLavaFallProvider::new);
        event.registerSpriteSet(ModParticles.SOUL_LAVA_HANG.get(), SoulLavaDripParticle.SoulLavaHangProvider::new);
        event.registerSpriteSet(ModParticles.SOUL_LAVA_LAND.get(), SoulLavaDripParticle.SoulLavaLandProvider::new);
    }

    @SubscribeEvent
    public static void registerEntityRenderer(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.GRAPPLING_HOOK.get(), GrapplingHookRenderer::new);
    }

    @SubscribeEvent
    public static void registerModels(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(LavaGooglesModel.LAYER_LOCATION, LavaGooglesModel::createLayerDefinition);
        event.registerLayerDefinition(GrapplingHookModel.LAYER_LOCATION, GrapplingHookModel::createLayerDefinition);
    }

    @SuppressWarnings("unchecked")
    @SubscribeEvent
    public static void addEntityModelLayers(EntityRenderersEvent.AddLayers event) {
        EntityModelSet modelSet = event.getEntityModels();
        for (EntityType<?> entityType : ForgeRegistries.ENTITY_TYPES) {
            try {
                if (entityType == EntityType.PLAYER) {
                    continue; // Exclude player, we add them later
                }

            LivingEntityRenderer<LivingEntity, HumanoidModel<LivingEntity>> renderer =
                    event.getRenderer((EntityType<LivingEntity>) entityType);
                addLayerToRenderer(renderer, entityType, modelSet);
            } catch (ClassCastException e) {
                // Non living entities can't be casted to LivingEntity,
                // no need to do anything
            }
        }
        // Player
        event.getSkins().forEach((skin) -> {
            PlayerRenderer playerRenderer = event.getSkin(skin);
            if (playerRenderer != null) {
                playerRenderer.addLayer(new LavaGooglesLayer<>(playerRenderer, modelSet));
                TelluriumsRandomStuffMod.LOGGER.info("Render layer added to player model: " + skin);
            } else {
                TelluriumsRandomStuffMod.LOGGER.error("Could not apply render layer to player model: " + skin);
            }
        });
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
