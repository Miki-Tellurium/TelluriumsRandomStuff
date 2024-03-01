package com.mikitellurium.telluriumsrandomstuff.common.item;

import com.mikitellurium.telluriumsrandomstuff.client.item.GrapplingHookItemExtension;
import com.mikitellurium.telluriumsrandomstuff.common.capability.GrapplingHookCapabilityProvider;
import com.mikitellurium.telluriumsrandomstuff.common.entity.GrapplingHookEntity;
import com.mikitellurium.telluriumsrandomstuff.common.networking.ModMessages;
import com.mikitellurium.telluriumsrandomstuff.common.networking.packets.GrapplingHookSyncS2CPacket;
import com.mikitellurium.telluriumsrandomstuff.registry.ModEnchantments;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import com.mikitellurium.telluriumsrandomstuff.util.LogUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class GrapplingHookItem extends Item implements Vanishable {

    public GrapplingHookItem() {
        super(new Item.Properties()
                .stacksTo(1)
                .defaultDurability(200));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        AtomicReference<InteractionResultHolder<ItemStack>> result = new AtomicReference<>(InteractionResultHolder.fail(itemstack));
        if (!level.isClientSide) {
            player.getCapability(GrapplingHookCapabilityProvider.INSTANCE).ifPresent((hook) -> {
                if (hook.isHookPresent()) {
                    if (ItemStack.matches(itemstack, hook.getStack())) {
                        int damage = hook.getHook().retrieve(player.isShiftKeyDown());
                        itemstack.hurtAndBreak(damage, player, (p) -> p.broadcastBreakEvent(hand));
                        player.awardStat(Stats.ITEM_USED.get(this));
                        result.set(InteractionResultHolder.success(itemstack));
                    }
                } else {
                    player.startUsingItem(hand);
                    result.set(InteractionResultHolder.consume(itemstack));
                }
            });
        }

        return result.get();
    }

    @Override
    public void releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int timeCharged) {
        if (!level.isClientSide && livingEntity instanceof Player player) {
            int timeUsed = this.getUseDuration(itemStack) - timeCharged;
            float launchStrength = this.getPowerForTime(timeUsed, itemStack) + this.getAeroDynamicsBoost(itemStack);
            if (launchStrength < 0.2D) return;
            player.getCapability(GrapplingHookCapabilityProvider.INSTANCE).ifPresent((hook) -> {
                GrapplingHookEntity grapplingHook = new GrapplingHookEntity(player, level, itemStack, launchStrength);
                if (level.addFreshEntity(grapplingHook)) {
                    hook.setGrappling(grapplingHook, itemStack);
                    ModMessages.sendToPlayer(new GrapplingHookSyncS2CPacket(true, itemStack), (ServerPlayer) player);
                    level.playSound(null, grapplingHook, SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 1.0F, 1.0F);
                }
            });
        }
    }

    private float getPowerForTime(int useTime, ItemStack itemStack) {
        float f = (float)useTime / (float) this.getChargeDuration(itemStack);
        if (f > 1.0F) {
            f = 1.0F;
        }
        return f;
    }

    private int getChargeDuration(ItemStack itemStack) {
        int i = itemStack.getEnchantmentLevel(Enchantments.QUICK_CHARGE);
        return i == 0 ? 20 : 20 - 4 * i;
    }

    private float getAeroDynamicsBoost(ItemStack itemStack) {
        int i = itemStack.getEnchantmentLevel(ModEnchantments.AERODYNAMICS.get());
        return 0.1F * i;
    }

    @Override
    public int getUseDuration(ItemStack itemStack) {
        return 72000;
    }

    @Override
    public boolean useOnRelease(ItemStack itemStack) {
        return itemStack.is(this);
    }

    @Override
    public boolean onDroppedByPlayer(ItemStack item, Player player) {
        if (!player.level().isClientSide) {
            player.getCapability(GrapplingHookCapabilityProvider.INSTANCE).ifPresent((hook) ->
                    hook.ifPresent(GrapplingHookEntity::discard));
        }

        return true;
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack repairStack) {
        return repairStack.is(ModItems.SOUL_INFUSED_IRON_INGOT.get());
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment.equals(ModEnchantments.AERODYNAMICS.get()) ||
                enchantment.equals(Enchantments.QUICK_CHARGE) ||
                enchantment.equals(Enchantments.VANISHING_CURSE) ||
                enchantment.equals(Enchantments.UNBREAKING) ||
                enchantment.equals(Enchantments.MENDING);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new GrapplingHookItemExtension());
    }

    /* Events */
    @SubscribeEvent
    public static void onAttachPlayerCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player player) {
            if (!player.getCapability(GrapplingHookCapabilityProvider.INSTANCE).isPresent()) {
                event.addCapability(FastLoc.modLoc("grappling_hook"),
                        new GrapplingHookCapabilityProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerLogOut(PlayerEvent.PlayerLoggedOutEvent event) {
        Player player = event.getEntity();
        Level level = player.level();
        if (!level.isClientSide) {
            player.getCapability(GrapplingHookCapabilityProvider.INSTANCE).ifPresent((hook) ->
                    hook.ifPresent(GrapplingHookEntity::discard));
        }
    }

}
