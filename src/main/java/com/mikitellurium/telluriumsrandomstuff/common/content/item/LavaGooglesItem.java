package com.mikitellurium.telluriumsrandomstuff.common.content.item;

import com.mikitellurium.telluriumsrandomstuff.registry.ModArmorMaterials;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

public class LavaGooglesItem extends ArmorItem {

    public LavaGooglesItem() {
        super(ModArmorMaterials.LAVA_GOOGLES, Type.HELMET, new Item.Properties()
                .defaultDurability(64)
                .fireResistant());
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment.equals(Enchantments.VANISHING_CURSE) ||
                enchantment.equals(Enchantments.UNBREAKING) ||
                enchantment.equals(Enchantments.MENDING) ||
                enchantment.equals(Enchantments.FIRE_PROTECTION);
    }

}
