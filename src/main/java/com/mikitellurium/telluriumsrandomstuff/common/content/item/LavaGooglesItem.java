package com.mikitellurium.telluriumsrandomstuff.common.content.item;

import com.mikitellurium.telluriumsrandomstuff.registry.ModArmorMaterials;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class LavaGooglesItem extends ArmorItem {

    public static final String tag_color = "color";

    public LavaGooglesItem() {
        super(ModArmorMaterials.LAVA_GOOGLES, Type.HELMET, new Item.Properties()
                .defaultDurability(64)
                .fireResistant());
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment.equals(Enchantments.VANISHING_CURSE) ||
                enchantment.equals(Enchantments.BINDING_CURSE) ||
                enchantment.equals(Enchantments.UNBREAKING) ||
                enchantment.equals(Enchantments.MENDING) ||
                enchantment.equals(Enchantments.FIRE_PROTECTION);
    }

    @Override
    public void onArmorTick(ItemStack itemStack, Level level, Player player) {
        DyeColor dyeColor = getColor(itemStack);
        if (dyeColor == null) {
            System.out.println("No color tag");
        } else {
            System.out.println("Googles are " + dyeColor.getSerializedName());
        }
    }

    public static void setColor(ItemStack itemStack, DyeColor dyeColor) {
        itemStack.getOrCreateTag().putInt(tag_color, dyeColor.getId());
    }

    public static DyeColor getColor(ItemStack itemStack) {
        CompoundTag tag = itemStack.getTag();
        if (tag != null && tag.contains(tag_color)) {
            return DyeColor.byId(tag.getInt(tag_color));
        } else {
            return null;
        }
    }

}
