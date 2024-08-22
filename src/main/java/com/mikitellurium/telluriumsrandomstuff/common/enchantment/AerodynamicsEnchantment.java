package com.mikitellurium.telluriumsrandomstuff.common.enchantment;

import com.mikitellurium.telluriumsrandomstuff.common.item.GrapplingHookItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.Set;

public class AerodynamicsEnchantment extends Enchantment {

    public AerodynamicsEnchantment(EquipmentSlot... equipmentSlots) {
        super(Rarity.UNCOMMON, ModEnchantementCategory.THROWABLE, equipmentSlots);
    }

    @Override
    public boolean allowedInCreativeTab(Item book, Set<EnchantmentCategory> allowedCategories) {
        return true;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean canEnchant(ItemStack itemStack) {
        return itemStack.getItem() instanceof GrapplingHookItem;
    }

}
