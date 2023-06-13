package com.mikitellurium.telluriumsrandomstuff.event;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.block.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.block.custom.CustomBubbleColumnBlock;
import com.mikitellurium.telluriumsrandomstuff.capability.SoulAnchorCapabilityProvider;
import com.mikitellurium.telluriumsrandomstuff.capability.SoulAnchorLevelData;
import com.mikitellurium.telluriumsrandomstuff.config.ModCommonConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.horse.ZombieHorse;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
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

@Mod.EventBusSubscriber(modid = TelluriumsRandomStuffMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {
    private static boolean wasInBubbleColumn;
    private static boolean firstTick = true;

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
    public static void insideBubbleColumnBreathing(LivingEvent.LivingTickEvent event) {
        // Increase entity air supply when inside bubble column
        LivingEntity entity = event.getEntity();
        if (entity.level().getBlockState(new BlockPos((int)entity.getX(), (int)entity.getEyeY(), (int)entity.getZ())).is(ModBlocks.CUSTOM_BUBBLE_COLUMN.get())) {
            if (entity.getAirSupply() < entity.getMaxAirSupply()) {
                entity.setAirSupply(Math.min(entity.getAirSupply() + 5, entity.getMaxAirSupply()));
            }
        }
    }

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

}
