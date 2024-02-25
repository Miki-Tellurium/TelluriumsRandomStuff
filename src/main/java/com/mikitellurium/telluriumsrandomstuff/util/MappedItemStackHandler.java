package com.mikitellurium.telluriumsrandomstuff.util;

import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public class MappedItemStackHandler extends ItemStackHandler {

    Predicate<Integer> isInputSlot;
    Predicate<Integer> isOutputSlot;
    Predicate<Integer> isBucketSlot;

    public MappedItemStackHandler(int slots, Predicate<Integer> isInputSlot, Predicate<Integer> isOutputSlot,
                                  Predicate<Integer> isBucketSlot) {
        super(slots);
        this.isInputSlot = isInputSlot;
        this.isOutputSlot = isOutputSlot;
        this.isBucketSlot = isBucketSlot;
    }

    public boolean isBucket(int slot) {
        return this.isBucketSlot.test(slot);
    }

    public boolean isInput(int slot) {
        return this.isInputSlot.test(slot);
    }

    public boolean isOutput(int slot) {
        return this.isOutputSlot.test(slot);
    }

}
