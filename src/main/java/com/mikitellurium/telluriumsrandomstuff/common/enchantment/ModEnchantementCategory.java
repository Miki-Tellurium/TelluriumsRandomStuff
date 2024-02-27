package com.mikitellurium.telluriumsrandomstuff.common.enchantment;

import com.mikitellurium.telluriumsrandomstuff.common.item.GrapplingHookItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class ModEnchantementCategory {

    public static final EnchantmentCategory THROWABLE = EnchantmentCategory.create("THROWABLE",
            (item) -> item instanceof GrapplingHookItem);

}
