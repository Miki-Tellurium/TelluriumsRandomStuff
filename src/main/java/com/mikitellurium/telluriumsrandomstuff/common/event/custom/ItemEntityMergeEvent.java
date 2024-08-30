package com.mikitellurium.telluriumsrandomstuff.common.event.custom;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Cancelable
public class ItemEntityMergeEvent extends Event {

    private final ItemStack destinationStack;
    private final ItemStack originStack;
    private ItemStack returnStack = ItemStack.EMPTY;

    public ItemEntityMergeEvent(ItemStack destinationStack, ItemStack originStack) {
        this.destinationStack = destinationStack;
        this.originStack = originStack;
    }

    public ItemStack getDestinationStack() {
        return destinationStack;
    }

    public ItemStack getOriginStack() {
        return originStack;
    }

    public ItemStack getReturnStack() {
        return returnStack;
    }

    public void setReturnStack(ItemStack returnStack) {
        this.returnStack = returnStack;
    }

}
