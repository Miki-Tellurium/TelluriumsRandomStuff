package com.mikitellurium.telluriumsrandomstuff.common.item;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TelluriumsRandomStuffMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SoulStorageItem extends Item {

    private final int soulsForUnit;

    public SoulStorageItem(int soulForUnit, Item.Properties properties) {
        super(properties);
        this.soulsForUnit = soulForUnit;
    }

    public int getSoulsForUnit() {
        return soulsForUnit;
    }

    public static boolean isSoulStorageItem(ItemStack itemStack) {
        return itemStack.getItem() instanceof SoulStorageItem;
    }

}
