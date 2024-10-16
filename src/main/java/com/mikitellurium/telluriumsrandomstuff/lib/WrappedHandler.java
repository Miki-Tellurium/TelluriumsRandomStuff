package com.mikitellurium.telluriumsrandomstuff.lib;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/*
 * WrappedHandler by noeppi_noeppi
 * under https://github.com/ModdingX/LibX/blob/1.19/LICENSE
 *
 */
public class WrappedHandler implements IItemHandlerModifiable {
    private final IItemHandlerModifiable handler;
    private final BiPredicate<Integer, ItemStack> canInsert;
    private final Predicate<Integer> canExtract;

    public WrappedHandler(IItemHandlerModifiable handler, BiPredicate<Integer, ItemStack> canInsert, Predicate<Integer> canExtract) {
        this.handler = handler;
        this.canInsert = canInsert;
        this.canExtract = canExtract;
    }

    @Override
    public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
        this.handler.setStackInSlot(slot, stack);
    }

    @Override
    public int getSlots() {
        return this.handler.getSlots();
    }

    @Nonnull
    @Override
    public ItemStack getStackInSlot(int slot) {
        return this.handler.getStackInSlot(slot);
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        return this.canInsert.test(slot, stack) ? this.handler.insertItem(slot, stack, simulate) : stack;
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return this.canExtract.test(slot) ? this.handler.extractItem(slot, amount, simulate) : ItemStack.EMPTY;
    }

    @Override
    public int getSlotLimit(int slot) {
        return this.handler.getSlotLimit(slot);
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        return this.canInsert.test(slot, stack) && this.handler.isItemValid(slot, stack);
    }

}
