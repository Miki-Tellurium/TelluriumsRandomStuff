package com.mikitellurium.telluriumsrandomstuff.common.item;

import com.mikitellurium.telluriumsrandomstuff.common.entity.SpiritedAllay;
import com.mikitellurium.telluriumsrandomstuff.mixin.AllayAccessor;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ResonanceCrystalItem extends Item {

    private final DyeColor color;

    public ResonanceCrystalItem(DyeColor color, Properties properties) {
        super(properties);
        this.color = color;
    }

    public DyeColor getColor() {
        return color;
    }

    /* Events */
    @SubscribeEvent
    public static void entityInteract(PlayerInteractEvent.EntityInteract event) {
        Entity entity = event.getTarget();
        if (entity instanceof Allay allay) {
            Player player = event.getEntity();
            ItemStack itemStack = player.getItemInHand(event.getHand());
            if (itemStack.getItem() instanceof ResonanceCrystalItem) {
                InteractionResult result = interactWithAllay(itemStack, player, allay);
                if (result == InteractionResult.SUCCESS) {
                    event.setCanceled(true);
                    event.setCancellationResult(result);
                } else {
                    event.setCanceled(entity.level().isClientSide);
                }
            }
        }
    }

    private static InteractionResult interactWithAllay(ItemStack itemStack, Player player, Allay allay) {
        Level level = allay.level();
        if (!level.isClientSide && allay.isDancing() && ((AllayAccessor)allay).invokeCanDuplicate()) {
            if (spawnSpiritedAllay(allay, ((ResonanceCrystalItem)itemStack.getItem()).getColor())) {
                level.broadcastEntityEvent(allay, (byte)18);
                level.playSound(player, allay, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.NEUTRAL, 2.0F, 1.0F);
                if (!player.isCreative()) {
                    itemStack.shrink(1);
                }
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.FAIL;
        }
        return InteractionResult.PASS;
    }

    private static boolean spawnSpiritedAllay(Entity entity, DyeColor color) {
        Level level = entity.level();
        SpiritedAllay spiritedAllay = new SpiritedAllay(level, color);
        spiritedAllay.moveTo(entity.position());
        spiritedAllay.setPersistenceRequired();
        ((AllayAccessor)entity).invokeResetDuplicationCooldown();
        return level.addFreshEntity(spiritedAllay);
    }

}
