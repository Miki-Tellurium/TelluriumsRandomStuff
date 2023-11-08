package com.mikitellurium.telluriumsrandomstuff.common.event;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.common.config.ModCommonConfig;
import com.mikitellurium.telluriumsrandomstuff.util.LevelUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.animal.horse.ZombieHorse;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.village.VillageSiegeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TelluriumsRandomStuffMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GameplayCommonEvents {

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

    // Trident behaviour
    @SubscribeEvent
    public static void onStopUsingItem(LivingEntityUseItemEvent.Stop event) {
        Level level = event.getEntity().level();
        ItemStack itemStack = event.getItem();
        if (event.getEntity() instanceof Player player && itemStack.is(Items.TRIDENT)) {
            if (LevelUtils.isInsideWaterCauldron(level, player)) {
                int riptideLevel = EnchantmentHelper.getRiptide(itemStack);
                if (riptideLevel > 0) {
                    launchPlayer(level, player, riptideLevel);
                }
            }
        }
    }

    private static void launchPlayer(Level level, Player player, int riptideLevel) {
        Vec3 playerPos = player.blockPosition().above().getCenter();
        player.setPos(playerPos.subtract(0.0D, 0.25D, 0.0D)); // Avoid the player getting stuck on the cauldron
        float f7 = player.getYRot();
        float f = player.getXRot();
        float f1 = -Mth.sin(f7 * ((float)Math.PI / 180F)) * Mth.cos(f * ((float)Math.PI / 180F));
        float f2 = -Mth.sin(f * ((float)Math.PI / 180F));
        float f3 = Mth.cos(f7 * ((float)Math.PI / 180F)) * Mth.cos(f * ((float)Math.PI / 180F));
        float f4 = Mth.sqrt(f1 * f1 + f2 * f2 + f3 * f3);
        float f5 = 3.0F * ((1.0F + (float)riptideLevel) / 4.0F);
        f1 *= f5 / f4;
        f2 *= f5 / f4;
        f3 *= f5 / f4;
        player.push(f1, f2, f3);
        player.startAutoSpinAttack(20);
        if (player.onGround()) {
            player.move(MoverType.SELF, new Vec3(0.0D, 1.1999999F, 0.0D));
        }

        SoundEvent soundevent;
        if (riptideLevel >= 3) {
            soundevent = SoundEvents.TRIDENT_RIPTIDE_3;
        } else if (riptideLevel == 2) {
            soundevent = SoundEvents.TRIDENT_RIPTIDE_2;
        } else {
            soundevent = SoundEvents.TRIDENT_RIPTIDE_1;
        }

        level.playSound(null, player, soundevent, SoundSource.PLAYERS, 1.0F, 1.0F);
    }

}
