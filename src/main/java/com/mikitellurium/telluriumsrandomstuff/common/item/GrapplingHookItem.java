package com.mikitellurium.telluriumsrandomstuff.common.item;

import com.mikitellurium.telluriumsrandomstuff.common.entity.GrapplingHookEntity;
import com.mikitellurium.telluriumsrandomstuff.common.networking.GrapplingHookManager;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityLeaveLevelEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class GrapplingHookItem extends Item implements Vanishable {

    public GrapplingHookItem() {
        super(new Item.Properties()
                .stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            GrapplingHookManager manager = GrapplingHookManager.get(level);
            if (manager.isHookPresent(player)) {
                manager.getHook(player).retrieve();
            } else {
                GrapplingHookEntity grapplingHook = new GrapplingHookEntity(player, level);
                manager.insertHook(player, grapplingHook);
                level.addFreshEntity(grapplingHook);
            }
        }

        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }

    @Override
    public boolean onDroppedByPlayer(ItemStack item, Player player) {
        if (!player.level().isClientSide) {
            GrapplingHookManager.get(player.level()).ifHookPresent(player, GrapplingHookEntity::discard);
        }

        return true;
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
        if (!level.isClientSide && !level.getServer().isSingleplayer()) {
            if (entity instanceof Player player) {
                GrapplingHookManager.get(player.level()).ifHookPresent(player, GrapplingHookEntity::discard);
            }
        }
    }

}
