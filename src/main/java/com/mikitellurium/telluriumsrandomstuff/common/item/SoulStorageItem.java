package com.mikitellurium.telluriumsrandomstuff.common.item;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TelluriumsRandomStuffMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SoulStorageItem extends Item {

    public SoulStorageItem(Item.Properties properties) {
        super(properties);
    }

    public ItemStack getEmptyRemainder() {
        return ItemStack.EMPTY;
    }

    public static boolean isSoulStorageItem(ItemStack itemStack) {
        return itemStack.getItem() instanceof SoulStorageItem;
    }

}
