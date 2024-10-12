package com.mikitellurium.telluriumsrandomstuff.common.item;

import com.mikitellurium.telluriumsrandomstuff.common.entity.SpiritedAllay;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class SpiritedEchoWandItem extends Item {

    public SpiritedEchoWandItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    // temporary
    @Override
    public boolean hurtEnemy(ItemStack itemStack, LivingEntity target, LivingEntity attacker) {
        if (!target.level().isClientSide && target instanceof SpiritedAllay) {
            target.kill();
            return true;
        }
        return super.hurtEnemy(itemStack, target, attacker);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity entity, InteractionHand interactionHand) {
        if (entity instanceof SpiritedAllay && !entity.level().isClientSide) {
            ItemStack itemStack1 = new ItemStack(ModItems.SPIRITED_ALLAY_ITEM.get());
            CompoundTag entityTag = new CompoundTag();
            entity.save(entityTag);
            CompoundTag tag = itemStack1.getOrCreateTag();
            tag.put("SavedEntity", entityTag);
            entity.spawnAtLocation(itemStack1, 0.4F);
            entity.discard();
            return InteractionResult.SUCCESS;
        }
        
        return super.interactLivingEntity(itemStack, player, entity, interactionHand);
    }

}
