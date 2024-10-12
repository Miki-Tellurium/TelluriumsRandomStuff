package com.mikitellurium.telluriumsrandomstuff.common.item;

import com.mikitellurium.telluriumsrandomstuff.common.entity.SpiritedAllay;
import com.mikitellurium.telluriumsrandomstuff.registry.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SpiritedAllayItem extends Item {

    private static final String COLOR_TAG = "Color";

    public SpiritedAllayItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (!level.isClientSide) {
            ItemStack itemStack = context.getItemInHand();
            CompoundTag tag = getEntityTag(itemStack);
            if (tag != null) {
                if (this.spawnFromTag(tag, (ServerLevel) level, context.getClickedPos())) {
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

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag isAdvanced) {
        Component customName = getCustomName(itemStack);
        if (customName != null) {
            components.add(customName);
        }
        DyeColor dyeColor = getColor(itemStack);
        MutableComponent colorString = Component.literal(dyeColor.getName()).withStyle((style) -> style.withColor(dyeColor.getTextColor()));
        components.add(Component.translatable("item.telluriumsrandomstuff.tooltip.color").append(": ").append(colorString));
    }

    public static DyeColor getColor(ItemStack itemStack) {
        CompoundTag tag = getEntityTag(itemStack);
        if (tag != null && tag.contains(COLOR_TAG, Tag.TAG_BYTE)) {
            return DyeColor.byId(tag.getByte(COLOR_TAG));
        }
        return DyeColor.LIGHT_BLUE;
    }

    public static Component getCustomName(ItemStack itemStack) {
        CompoundTag tag = getEntityTag(itemStack);
        if (tag != null && tag.contains("CustomName", Tag.TAG_STRING)) {
            return Component.Serializer.fromJson(tag.getString("CustomName"));
        }
        return null;
    }

    public static CompoundTag getEntityTag(ItemStack itemStack) {
        CompoundTag tag = itemStack.getOrCreateTag();
        if (tag.contains("SavedEntity", Tag.TAG_COMPOUND)) {
            return tag.getCompound("SavedEntity");
        }
        return null;
    }

}
