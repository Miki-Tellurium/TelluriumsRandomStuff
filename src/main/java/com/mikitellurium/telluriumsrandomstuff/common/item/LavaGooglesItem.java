package com.mikitellurium.telluriumsrandomstuff.common.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LavaGooglesItem extends Item implements Equipable {

    public static final String tag_color = "color";

    public LavaGooglesItem() {
        super(new Item.Properties()
                .defaultDurability(64)
                .fireResistant());
    }

    @Override
    public EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.HEAD;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return this.swapWithEquipmentSlot(this, level, player, hand);
    }

    public static void hurtGoogles(ItemStack googles, Player player, DamageSource source, float damage) {
        if (!source.is(DamageTypeTags.IS_FIRE) && googles.getItem() instanceof LavaGooglesItem) {
            googles.hurtAndBreak((int)damage, player, (player1) -> {
                player1.broadcastBreakEvent(EquipmentSlot.HEAD);
            });
        }
    }

    @Override
    public int getEnchantmentValue() {
        return 12;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment.equals(Enchantments.VANISHING_CURSE) ||
                enchantment.equals(Enchantments.BINDING_CURSE) ||
                enchantment.equals(Enchantments.FIRE_PROTECTION) ||
                enchantment.equals(Enchantments.UNBREAKING) ||
                enchantment.equals(Enchantments.MENDING);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components,
                                TooltipFlag isAdvanced) {
        DyeColor dyeColor = getColor(itemStack);
        if (dyeColor != null) {
            MutableComponent colorString = Component.literal(dyeColor.getName())
                    .withStyle((style -> style.withColor(dyeColor.getTextColor())));
            components.add(Component.literal("Color: ").append(colorString));
        }
    }

    public static ItemStack setColor(ItemStack itemStack, DyeColor dyeColor) {
        itemStack.getOrCreateTag().putString(tag_color, dyeColor.getSerializedName());
        return itemStack;
    }

    public static ItemStack setRandomColor(ItemStack itemStack, RandomSource random) {
        return setColor(itemStack, DyeColor.byId(random.nextInt(16)));
    }

    public static DyeColor getColor(ItemStack itemStack) {
        CompoundTag tag = itemStack.getTag();
        if (tag != null && tag.contains(tag_color)) {
            String colorName = tag.getString(tag_color);
            return DyeColor.byName(colorName, DyeColor.byId(tag.getInt(tag_color)));
        } else {
            return null;
        }
    }

}
