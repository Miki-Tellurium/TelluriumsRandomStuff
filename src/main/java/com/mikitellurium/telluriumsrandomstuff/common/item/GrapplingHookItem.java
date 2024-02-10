package com.mikitellurium.telluriumsrandomstuff.common.item;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.common.capability.GrapplingHookCapabilityProvider;
import com.mikitellurium.telluriumsrandomstuff.common.entity.GrapplingHookEntity;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.EntityLeaveLevelEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.concurrent.atomic.AtomicReference;

public class GrapplingHookItem extends Item implements Vanishable {

    private static final String TAG_IS_USING = "grappling_hook.is_using";

    public GrapplingHookItem() {
        super(new Item.Properties()
                .stacksTo(1)
                .defaultDurability(256));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        AtomicReference<InteractionResultHolder<ItemStack>> result = new AtomicReference<>(InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide()));
        if (!level.isClientSide) {
            player.getCapability(GrapplingHookCapabilityProvider.INSTANCE).ifPresent((hook) -> {
                if (hook.isPresent()) {
                    int damage = hook.getHook().retrieve(player.isShiftKeyDown());
                    itemstack.hurtAndBreak(damage, player, (p) -> p.broadcastBreakEvent(hand));
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
            float launchStrength = BowItem.getPowerForTime(timeUsed);
            if (launchStrength < 0.15D) return;
            player.getCapability(GrapplingHookCapabilityProvider.INSTANCE).ifPresent((hook) -> {
                GrapplingHookEntity grapplingHook = new GrapplingHookEntity(player, level, launchStrength);
                hook.setGrappling(grapplingHook, itemStack);
                level.addFreshEntity(grapplingHook);
                level.playSound(null, grapplingHook, SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 1.0F, 1.0F);
            });
        }
    }

    public void setPlayerIsUsing(ItemStack itemStack, boolean isUsing) {
        if (itemStack.is(this)) {
            CompoundTag tag = itemStack.getOrCreateTag();
            tag.putBoolean(TAG_IS_USING, isUsing);
        }
    }

    public boolean getPlayerIsUsing(ItemStack itemStack) {
        if (itemStack.hasTag()) {
            CompoundTag tag = itemStack.getTag();
            return tag.contains(TAG_IS_USING) && tag.getBoolean(TAG_IS_USING);
        }
        return false;
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
        return enchantment.equals(Enchantments.VANISHING_CURSE) ||
                enchantment.equals(Enchantments.UNBREAKING) ||
                enchantment.equals(Enchantments.MENDING);
    }

    /* Events */
    @SubscribeEvent
    public static void onAttachPlayerCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player player) {
            if (!player.getCapability(GrapplingHookCapabilityProvider.INSTANCE).isPresent()) {
                event.addCapability(new ResourceLocation(TelluriumsRandomStuffMod.MOD_ID, "grappling_hook"),
                        new GrapplingHookCapabilityProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        Level level = event.getEntity().level();
        if (!level.isClientSide) {
            Entity entity = event.getEntity();
            if (entity instanceof Player player && !event.isCanceled()) {
                player.getCapability(GrapplingHookCapabilityProvider.INSTANCE).ifPresent((hook) ->
                        hook.ifPresent(GrapplingHookEntity::discard));
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        Level level = event.getEntity().level();
        if (!level.isClientSide) {
            Player player = event.getEntity();
            player.getCapability(GrapplingHookCapabilityProvider.INSTANCE).ifPresent((hook) ->
                    hook.ifPresent(GrapplingHookEntity::discard));
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
