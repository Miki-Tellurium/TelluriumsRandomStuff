package com.mikitellurium.telluriumsrandomstuff.common.item;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.test.bin.SoulStorage;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.atomic.AtomicReference;

@Mod.EventBusSubscriber(modid = TelluriumsRandomStuffMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SpiritBottleItem extends SoulStorageItem {

    private final int capacity;

    public SpiritBottleItem(int capacity, Properties properties) {
        super(properties.stacksTo(1));
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack itemStack, Slot slot, ClickAction action, Player player) {
        if (itemStack.getCount() != 1 || !SoulStorageItem.isSoulStorageItem(slot.getItem()) || action != ClickAction.SECONDARY) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public ItemStack getEmptyRemainder() {
        return this.getDefaultInstance();
    }

}
