package com.mikitellurium.telluriumsrandomstuff.setup;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.api.potionmixing.PotionMixingManager;
import com.mikitellurium.telluriumsrandomstuff.client.ClientEntityManager;
import com.mikitellurium.telluriumsrandomstuff.client.blockentity.ItemPedestalRenderer;
import com.mikitellurium.telluriumsrandomstuff.client.entity.layer.LavaGooglesLayer;
import com.mikitellurium.telluriumsrandomstuff.client.entity.model.GrapplingHookModel;
import com.mikitellurium.telluriumsrandomstuff.client.entity.model.LavaGooglesModel;
import com.mikitellurium.telluriumsrandomstuff.client.entity.model.SpiritedAllayModel;
import com.mikitellurium.telluriumsrandomstuff.client.entity.render.DummyPlayerRenderer;
import com.mikitellurium.telluriumsrandomstuff.client.entity.render.GrapplingHookRenderer;
import com.mikitellurium.telluriumsrandomstuff.client.entity.render.SpiritedAllayRenderer;
import com.mikitellurium.telluriumsrandomstuff.client.hud.screen.*;
import com.mikitellurium.telluriumsrandomstuff.client.item.GrapplingHookHandRenderer;
import com.mikitellurium.telluriumsrandomstuff.client.item.SoulStorageClientTooltip;
import com.mikitellurium.telluriumsrandomstuff.client.item.SoulStorageTooltip;
import com.mikitellurium.telluriumsrandomstuff.client.item.SpiritedAllayItemRenderer;
import com.mikitellurium.telluriumsrandomstuff.common.block.AlchemixerBlock;
import com.mikitellurium.telluriumsrandomstuff.common.block.CustomBubbleColumnBlock;
import com.mikitellurium.telluriumsrandomstuff.common.block.SoulAnchorBlock;
import com.mikitellurium.telluriumsrandomstuff.common.event.LootEvents;
import com.mikitellurium.telluriumsrandomstuff.common.item.GrapplingHookItem;
import com.mikitellurium.telluriumsrandomstuff.common.item.LavaGooglesItem;
import com.mikitellurium.telluriumsrandomstuff.common.particle.SoulLavaDripParticle;
import com.mikitellurium.telluriumsrandomstuff.registry.*;
import com.mikitellurium.telluriumsrandomstuff.util.ColorsUtil;
import com.mikitellurium.telluriumsrandomstuff.util.LevelUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LightLayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = TelluriumsRandomStuffMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {

    public static void registerForgeBusEvents() {
        MinecraftForge.EVENT_BUS.register(ClientEntityManager.class);
    }

    @SubscribeEvent
    public static void setup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(ModMenuTypes.SOUL_FURNACE.get(), SoulFurnaceScreen::new);
            MenuScreens.register(ModMenuTypes.SOUL_ANCHOR.get(), SoulAnchorScreen::new);
            MenuScreens.register(ModMenuTypes.EXTRACTOR.get(), ExtractorScreen::new);
            MenuScreens.register(ModMenuTypes.SOUL_INFUSER.get(), SoulInfuserScreen::new);
            MenuScreens.register(ModMenuTypes.ALCHEMIXER.get(), AlchemixerScreen::new);
            MenuScreens.register(ModMenuTypes.SOUL_COMPACTOR.get(), SoulCompactorScreen::new);
            MenuScreens.register(ModMenuTypes.SOUL_ASSEMBLY.get(), SoulAssemblyScreen::new);
            ModItemProperties.register();
        });
    }

    @SubscribeEvent
    public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.ITEM_PEDESTAL.get(), ItemPedestalRenderer::new);
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.GRAPPLING_HOOK.get(), GrapplingHookRenderer::new);
        event.registerEntityRenderer(ModEntities.DUMMY_PLAYER.get(), DummyPlayerRenderer::new);
        event.registerEntityRenderer(ModEntities.SPIRITED_ALLAY.get(), SpiritedAllayRenderer::new);
    }

    @SubscribeEvent
    public static void registerReloadListeners(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(GrapplingHookHandRenderer.INSTANCE);
        TelluriumsRandomStuffMod.LOGGER.info("Registered GrapplingHookHandRenderer instance");
        event.registerReloadListener(SpiritedAllayItemRenderer.INSTANCE);
        TelluriumsRandomStuffMod.LOGGER.info("Registered SpiritedAllayItemRenderer instance");
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
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(LavaGooglesModel.LAYER_LOCATION, LavaGooglesModel::createLayerDefinition);
        event.registerLayerDefinition(GrapplingHookModel.LAYER_LOCATION, GrapplingHookModel::createLayerDefinition);
        event.registerLayerDefinition(SpiritedAllayModel.LAYER_LOCATION, SpiritedAllayModel::createLayerDefinition);
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
                TelluriumsRandomStuffMod.LOGGER.info("Render layer added to player model: {}", skin);
            } else {
                TelluriumsRandomStuffMod.LOGGER.error("Could not apply render layer to player model: {}", skin);
            }
        });
    }

    private static void addLayerToRenderer(LivingEntityRenderer<LivingEntity, HumanoidModel<LivingEntity>> renderer,
                                           EntityType<?> entityType, EntityModelSet modelSet) {
        try {
            if (renderer instanceof HumanoidMobRenderer || entityType == EntityType.ARMOR_STAND ||
                    entityType == EntityType.GIANT) {
                renderer.addLayer(new LavaGooglesLayer<>(renderer, modelSet));
                TelluriumsRandomStuffMod.LOGGER.info("Render layer correctly added to {}", entityType.toShortString());
            }
        } catch (Exception e) {
            TelluriumsRandomStuffMod.LOGGER.error("Could not add layer to {}", entityType.toShortString());
        }
    }

    @SubscribeEvent
    public static void registerTooltips(RegisterClientTooltipComponentFactoriesEvent event) {
        event.register(SoulStorageTooltip.class, SoulStorageClientTooltip::new);
    }

}
