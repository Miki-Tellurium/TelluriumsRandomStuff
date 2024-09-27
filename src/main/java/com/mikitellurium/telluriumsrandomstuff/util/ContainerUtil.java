package com.mikitellurium.telluriumsrandomstuff.util;

import net.minecraft.world.SimpleContainer;
import net.minecraftforge.items.IItemHandler;

public class ContainerUtil {

    public static SimpleContainer fromItemHandler(IItemHandler itemHandler) {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        return inventory;
    }

}
