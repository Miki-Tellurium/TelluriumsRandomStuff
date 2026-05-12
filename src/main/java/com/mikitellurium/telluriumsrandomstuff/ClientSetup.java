package com.mikitellurium.telluriumsrandomstuff;

import com.mikitellurium.telluriumsrandomstuff.client.blockentity.ItemPedestalRenderer;
import com.mikitellurium.telluriumsrandomstuff.client.entity.layer.LavaGooglesLayer;
import com.mikitellurium.telluriumsrandomstuff.client.entity.model.GrapplingHookModel;
import com.mikitellurium.telluriumsrandomstuff.client.entity.model.LavaGooglesModel;
import com.mikitellurium.telluriumsrandomstuff.client.entity.render.DummyPlayerRenderer;
import com.mikitellurium.telluriumsrandomstuff.client.entity.render.GrapplingHookRenderer;
import com.mikitellurium.telluriumsrandomstuff.client.hud.screen.*;
import com.mikitellurium.telluriumsrandomstuff.client.item.GrapplingHookHandRenderer;
import com.mikitellurium.telluriumsrandomstuff.common.block.OpalBlock;
import com.mikitellurium.telluriumsrandomstuff.common.item.LavaGooglesItem;
import com.mikitellurium.telluriumsrandomstuff.common.particle.SoulLavaDripParticle;
import com.mikitellurium.telluriumsrandomstuff.registry.*;
import com.mikitellurium.telluriumsrandomstuff.util.ColorsUtil;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = TelluriumsRandomStuffMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {
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
    }

    @SubscribeEvent
    public static void registerReloadListeners(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(GrapplingHookHandRenderer.INSTANCE);
        TelluriumsRandomStuffMod.LOGGER.info("Registered GrapplingHookHandRenderer instance");
    }

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register((state, level, pos, tintIndex) -> {
            Block block = state.getBlock();
            return block instanceof OpalBlock ? ((OpalBlock)block).getColor(state) : ColorsUtil.BLANK;
        }, ModBlocks.OPAL.get());
    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register(LavaGooglesItem::getItemTintColor, ModItems.LAVA_GOOGLES.get());
        event.register((stack, tintIndex) -> {
                    Block block = Block.byItem(stack.getItem());
                    if (block instanceof OpalBlock && stack.hasTag() && stack.getTag().contains("hue")) {
                        return ColorsUtil.getOpalColor(stack.getTag().getInt("hue"));
                    }
                    return ColorsUtil.BLANK;
                }, ModBlocks.OPAL.get());
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

            LivingEntityRenderer<LivingEntity, HumanoidModel<LivingEntity>> renderer = event.getRenderer((EntityType<LivingEntity>) entityType);
                addLayerToRenderer(renderer, entityType, modelSet);
            } catch (ClassCastException e) {
                // Non-living entities can't be cast to LivingEntity,
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
            if (renderer instanceof HumanoidMobRenderer || entityType == EntityType.ARMOR_STAND || entityType == EntityType.GIANT) {
                renderer.addLayer(new LavaGooglesLayer<>(renderer, modelSet));
                TelluriumsRandomStuffMod.LOGGER.info("Render layer correctly added to {}", entityType.toShortString());
            }
        } catch (Exception e) {
            TelluriumsRandomStuffMod.LOGGER.error("Could not add layer to {}", entityType.toShortString());
        }
    }
}
