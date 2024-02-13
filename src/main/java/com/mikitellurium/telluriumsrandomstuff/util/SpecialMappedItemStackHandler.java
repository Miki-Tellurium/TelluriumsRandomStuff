package com.mikitellurium.telluriumsrandomstuff.util;

import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SpecialMappedItemStackHandler extends ItemStackHandler {

    private final Map<Integer, SlotType> types = new HashMap<>();
    // todo fix this cringe
    public SpecialMappedItemStackHandler(int slots, int bucketSlot, int[] inputSlots, int[] outputSlots) {
        super(slots);
        types.put(bucketSlot, SlotType.BUCKET);
        Arrays.stream(inputSlots).forEach((i) -> types.put(i, SlotType.INPUT));
        Arrays.stream(outputSlots).forEach((i) -> types.put(i, SlotType.OUTPUT));
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return isInput(slot) || (isBucket(slot) && stack.is(ModItems.SOUL_LAVA_BUCKET.get()));
    }

    public SlotType getType(int slot) {
        return types.get(slot);
    }

    public boolean isBucket(int slot) {
        return getType(slot) == SlotType.BUCKET;
    }

    public boolean isInput(int slot) {
        return getType(slot) == SlotType.INPUT;
    }

    public boolean isOutput(int slot) {
        return getType(slot) == SlotType.OUTPUT;
    }

    public enum SlotType {
        BUCKET,
        INPUT,
        OUTPUT
    }

}
