package com.mikitellurium.telluriumsrandomstuff.common.item;

import com.mikitellurium.telluriumsrandomstuff.common.entity.SpiritedAllay;
import com.mikitellurium.telluriumsrandomstuff.registry.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class SpiritedAllayItem extends Item {

    public SpiritedAllayItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (!level.isClientSide) {
            ItemStack itemStack = context.getItemInHand();
            CompoundTag tag = itemStack.getOrCreateTag();
            if (tag.contains("SavedEntity", Tag.TAG_COMPOUND)) {
                if (this.spawnFromTag(tag.getCompound("SavedEntity"), (ServerLevel) level, context.getClickedPos())) {
                    itemStack.shrink(1);
                    return InteractionResult.SUCCESS;
                }
            } else {
                if (this.spawnWithoutTag((ServerLevel) level, context.getItemInHand(), context.getPlayer(), context.getClickedPos())) {
                    itemStack.shrink(1);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return super.useOn(context);
    }

    private boolean spawnFromTag(CompoundTag tag, ServerLevel level, BlockPos pos) {
        SpiritedAllay entity = ModEntities.SPIRITED_ALLAY.get().spawn(level, tag, null, pos, MobSpawnType.MOB_SUMMONED, true, false);
        return entity != null;
    }

    private boolean spawnWithoutTag(ServerLevel level, ItemStack itemStack, Player player, BlockPos pos) {
        SpiritedAllay entity = ModEntities.SPIRITED_ALLAY.get().spawn(level, itemStack, player, pos, MobSpawnType.MOB_SUMMONED, true, false);
        return entity != null;
    }

}
