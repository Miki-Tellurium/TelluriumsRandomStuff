package com.mikitellurium.telluriumsrandomstuff.common.item;

import com.mikitellurium.telluriumsrandomstuff.common.entity.GrapplingHookEntity;
import com.mikitellurium.telluriumsrandomstuff.common.networking.GrapplingHookManager;
import com.mikitellurium.telluriumsrandomstuff.util.LogUtils;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityLeaveLevelEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class GrapplingHookItem extends Item implements Vanishable {

    public GrapplingHookItem() {
        super(new Item.Properties()
                .stacksTo(1)
                .defaultDurability(256));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            GrapplingHookManager manager = GrapplingHookManager.get(level);
            if (manager.isHookPresent(player)) {
                int damage = manager.getHook(player).retrieve(player.isCrouching());
                itemstack.hurtAndBreak(damage, player, (p) -> p.broadcastBreakEvent(hand));
            } else {
                player.startUsingItem(hand);
                return InteractionResultHolder.consume(itemstack);
            }
        }

        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }

    @Override
    public void releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int timeCharged) {
        if (!level.isClientSide && livingEntity instanceof Player player) {
            int timeUsed = this.getUseDuration(itemStack) - timeCharged;
            float launchStrength = BowItem.getPowerForTime(timeUsed);
            if (launchStrength < 0.1D) return;
            GrapplingHookManager manager = GrapplingHookManager.get(level);
            GrapplingHookEntity grapplingHook = new GrapplingHookEntity(player, level, launchStrength);
            manager.insertHook(player, grapplingHook);
            level.addFreshEntity(grapplingHook);
            level.playSound(null, grapplingHook, SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 1.0F, 1.0F);
        }
    }

    @Override
    public int getUseDuration(ItemStack itemStack) {
        return 200;
    }

    @Override
    public boolean useOnRelease(ItemStack itemStack) {
        return itemStack.is(this);
    }

    @Override
    public boolean onDroppedByPlayer(ItemStack item, Player player) {
        if (!player.level().isClientSide) {
            GrapplingHookManager.get(player.level()).ifHookPresent(player, GrapplingHookEntity::discard);
        }

        return true;
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack repairStack) {
        return repairStack.is(Items.IRON_INGOT); // todo change
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment.equals(Enchantments.VANISHING_CURSE) ||
                enchantment.equals(Enchantments.UNBREAKING) ||
                enchantment.equals(Enchantments.MENDING);
    }

    /* Events */
    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        Level level = event.getEntity().level();
        if (!level.isClientSide) {
            Entity entity = event.getEntity();
            if (entity instanceof Player player && !event.isCanceled()) {
                GrapplingHookManager.get(player.level()).ifHookPresent(player, GrapplingHookEntity::discard);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        Level level = event.getEntity().level();
        if (!level.isClientSide) {
            Player player = event.getEntity();
            GrapplingHookManager.get(player.level()).ifHookPresent(player, GrapplingHookEntity::discard);
        }
    }

    @SubscribeEvent
    public static void onPlayerLogOut(EntityLeaveLevelEvent event) {
        Entity entity = event.getEntity();
        Level level = entity.level();
        if (!level.isClientSide) {
            if (entity instanceof Player player) {
                GrapplingHookManager.get(player.level()).ifHookPresent(player, GrapplingHookEntity::discard);
            }
        }
    }

}
