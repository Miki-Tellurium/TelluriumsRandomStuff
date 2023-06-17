package com.mikitellurium.telluriumsrandomstuff.common.event;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.common.capability.SoulAnchorCapabilityProvider;
import com.mikitellurium.telluriumsrandomstuff.common.capability.SoulAnchorLevelData;
import com.mikitellurium.telluriumsrandomstuff.common.config.ModCommonConfig;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.common.content.block.CustomBubbleColumnBlock;
import com.mikitellurium.telluriumsrandomstuff.registry.ModFluidTypes;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.horse.ZombieHorse;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.village.VillageSiegeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Vector3f;

import java.util.Arrays;

@Mod.EventBusSubscriber(modid = TelluriumsRandomStuffMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GameplayEvents {
    private static boolean wasInBubbleColumn;
    private static boolean firstTick = true;

    // Custom bubble columns
    @SubscribeEvent
    public static void onBubbleColumnEnterSoundEvent(TickEvent.PlayerTickEvent event) {
        // Play a sound when entering bubble columns
        if (event.phase != TickEvent.Phase.START || event.player == null) return;
        if (event.player.level().isClientSide) {
            BlockState blockstate = event.player.level().getBlockStatesIfLoaded(event.player.getBoundingBox().inflate(0.0D, -0.4F, 0.0D)
                            .deflate(1.0E-6D)).filter((block) -> block.is(ModBlocks.CUSTOM_BUBBLE_COLUMN.get()))
                    .findFirst().orElse(null);
            if (blockstate != null) {
                if (!wasInBubbleColumn && !firstTick && blockstate.is(ModBlocks.CUSTOM_BUBBLE_COLUMN.get()) && !event.player.isSpectator()) {
                    boolean flag = blockstate.getValue(CustomBubbleColumnBlock.DRAG_DOWN);
                    if (flag) {
                        event.player.playSound(SoundEvents.BUBBLE_COLUMN_WHIRLPOOL_INSIDE, 1.0F, 1.0F);
                    } else {
                        event.player.playSound(SoundEvents.BUBBLE_COLUMN_UPWARDS_INSIDE, 1.0F, 1.0F);
                    }
                }

                wasInBubbleColumn = true;
            } else {
                wasInBubbleColumn = false;
            }

            firstTick = false;
        }

    }

    @SubscribeEvent
    public static void livingTickEvent(LivingEvent.LivingTickEvent event) {
        // Increase entity air supply when inside bubble column
        LivingEntity entity = event.getEntity();
        if (entity.level().getBlockState(new BlockPos((int)entity.getX(), (int)entity.getEyeY(), (int)entity.getZ())).is(ModBlocks.CUSTOM_BUBBLE_COLUMN.get())) {
            if (entity.getAirSupply() < entity.getMaxAirSupply()) {
                entity.setAirSupply(Math.min(entity.getAirSupply() + 5, entity.getMaxAirSupply()));
            }
        }
    }

    // Soul anchor
    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        if (event.getEntity().level().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY)) {
            return;
        }
        if (event.getEntity() instanceof Player player) {
            player.getCapability(SoulAnchorCapabilityProvider.SOUL_ANCHOR_CAPABILITY).ifPresent((soulAnchor) -> {
                if (soulAnchor.hasChargedAnchor()) {
                    soulAnchor.saveInventory(player.getInventory());
                    soulAnchor.setCanRecoverInventory(true);
                }
            });
        }
    }

    @SubscribeEvent
    public static void onPlayerDropInventory(LivingDropsEvent event) {
        if (!event.getEntity().level().isClientSide) {
            if (event.getEntity().level().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY)) {
                return;
            }
            if (event.getEntity() instanceof Player player) {
                player.getCapability(SoulAnchorCapabilityProvider.SOUL_ANCHOR_CAPABILITY).ifPresent((soulAnchor) -> {
                    if (soulAnchor.hasChargedAnchor()) {
                        event.setCanceled(true);
                    }
                });
            }
        }
    }

    @SubscribeEvent
    public static void onAttachPlayerCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player player) {
            if (!player.getCapability(SoulAnchorCapabilityProvider.SOUL_ANCHOR_CAPABILITY).isPresent()) {
                event.addCapability(new ResourceLocation(TelluriumsRandomStuffMod.MOD_ID, "properties"),
                        new SoulAnchorCapabilityProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if (!event.getEntity().level().isClientSide) {
            if (event.isWasDeath()) {
                event.getOriginal().reviveCaps();
                event.getOriginal().getCapability(SoulAnchorCapabilityProvider.SOUL_ANCHOR_CAPABILITY).ifPresent((old) -> {
                    event.getEntity().getCapability(SoulAnchorCapabilityProvider.SOUL_ANCHOR_CAPABILITY).ifPresent((newClone) -> {
                        newClone.copyFrom(old);
                    });
                });
                event.getOriginal().invalidateCaps();
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerJoinLevel(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide) {
            return;
        }
        if (event.getLevel().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY)) {
            return;
        }

        if (event.getEntity() instanceof Player player) {
            if(SoulAnchorLevelData.get(event.getLevel()).removePlayer(player)) {
                player.getCapability(SoulAnchorCapabilityProvider.SOUL_ANCHOR_CAPABILITY).ifPresent((soulAnchor) -> {
                    soulAnchor.setChargedAnchor(false);
                    soulAnchor.clearInventory();
                });
            }
        }
    }

    // Zombie rider
    @SubscribeEvent
    public static void onZombieSiegeSpawn(VillageSiegeEvent event) {
        if (event.getLevel().isClientSide) {
            return;
        }

        ServerLevel level = (ServerLevel) event.getLevel();
        RandomSource random = level.random;
        if (random.nextInt(100) + 1 < ModCommonConfig.ZOMBIE_RIDER_SPAWN_CHANCE.get()) {
            Vec3 vec = event.getAttemptedSpawnPos();
            BlockPos spawnPos = new BlockPos((int) vec.x, (int) vec.y, (int) vec.z).above();
            Zombie rider = EntityType.ZOMBIE.spawn(level, spawnPos, MobSpawnType.EVENT);
            ZombieHorse steed = EntityType.ZOMBIE_HORSE.spawn(level, spawnPos, MobSpawnType.EVENT);
            if (rider != null && steed != null) {
                ItemStack helmet = new ItemStack(Items.IRON_HELMET);
                ItemStack weapon = new ItemStack(Items.IRON_SWORD);
                helmet.enchant(Enchantments.THORNS, 3);
                EnchantmentHelper.enchantItem(level.random, weapon, 5 + random.nextInt(25), false);
                rider.setItemSlot(EquipmentSlot.HEAD, helmet);
                rider.setItemSlot(EquipmentSlot.MAINHAND, weapon);
                steed.setTamed(true);
                rider.startRiding(steed);
                level.addFreshEntityWithPassengers(steed);
            }
        }
    }

    // Soul lava fog
    @SubscribeEvent
    public static void setFogPlane(ViewportEvent.RenderFog event) {
        if (isInSoulLava(event.getCamera())) {
            event.setCanceled(true);
        }
        if (event.isCanceled()) {
            Entity entity = event.getCamera().getEntity();

            if (entity.isSpectator()) {
                event.setNearPlaneDistance(-8.0F);
                event.setFarPlaneDistance(event.getRenderer().getRenderDistance() * 0.5F);
            } else if (entity instanceof LivingEntity && ((LivingEntity) entity).hasEffect(MobEffects.FIRE_RESISTANCE)) {
                event.setNearPlaneDistance(0.0F);
                event.setFarPlaneDistance(3.0F);
            } else {
                event.setNearPlaneDistance(0.25f);
                event.setFarPlaneDistance(1.0f);
            }
        }
    }

    @SubscribeEvent
    public static void setFogColor(ViewportEvent.ComputeFogColor event) {
        if (isInSoulLava(event.getCamera())) {
            Vector3f soulLavaFogColor = new Vector3f(0f / 255f, 210f / 255f, 225f / 255f);
            event.setRed(soulLavaFogColor.x);
            event.setGreen(soulLavaFogColor.y);
            event.setBlue(soulLavaFogColor.z);
        }
    }

    private static boolean isInSoulLava(Camera camera) {
        Camera.NearPlane nearPlane = camera.getNearPlane();
        BlockGetter blockGetter = Minecraft.getInstance().level;
        for(Vec3 vec3 : Arrays.asList(new Vec3(camera.getLookVector()).scale(0.05F), nearPlane.getTopLeft(),
                nearPlane.getTopRight(), nearPlane.getBottomLeft(), nearPlane.getBottomRight())) {
            Vec3 vec31 = camera.getPosition().add(vec3);
            BlockPos blockpos = BlockPos.containing(vec31);
            FluidState fluidstate1 = blockGetter.getFluidState(blockpos);
            if (fluidstate1.getFluidType() == ModFluidTypes.SOUL_LAVA_TYPE) {
                if (vec31.y <= (double)(fluidstate1.getHeight(blockGetter, blockpos) + (float)blockpos.getY())) {
                    return true;
                }
            }
        }

        return false;
    }

}
