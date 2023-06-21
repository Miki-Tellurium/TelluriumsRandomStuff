package com.mikitellurium.telluriumsrandomstuff.common.content.item;

import com.mikitellurium.telluriumsrandomstuff.registry.ModArmorMaterials;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;

public class LavaGooglesItem extends ArmorItem {

    public LavaGooglesItem() {
        super(ModArmorMaterials.LAVA_GOOGLES, Type.HELMET, new Item.Properties()
                .defaultDurability(64)
                .fireResistant());
    }

}
