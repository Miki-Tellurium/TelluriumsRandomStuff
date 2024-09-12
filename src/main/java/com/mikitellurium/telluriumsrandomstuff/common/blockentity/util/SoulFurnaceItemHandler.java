package com.mikitellurium.telluriumsrandomstuff.common.blockentity.util;

import com.mikitellurium.telluriumsrandomstuff.lib.MappedItemStackHandler;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SoulFurnaceItemHandler extends MappedItemStackHandler {

    private static final int bucketSlot = 0;
    private static final List<Integer> inputSlots = List.of(1, 2, 3, 4);
    private static final List<Integer> outputSlots = List.of(5, 6, 7, 8);

    private final Map<Integer, Integer> counters = Util.make(new HashMap<>(), (map) -> {
        inputSlots.forEach((slot) -> map.put(slot, 0));
    });

    public SoulFurnaceItemHandler() {
        super(9, inputSlots::contains, outputSlots::contains, (i) -> i == bucketSlot);
    }

    public int getProgress(int slot) {
        return this.counters.get(slot);
    }

    public void setProgress(int slot, int progress) {
        this.counters.put(slot, progress);
    }

    public void tickProgress(int slot) {
        int progress = this.counters.get(slot);
        this.counters.put(slot, ++progress);
    }

    public void resetProgress(int slot) {
        this.counters.put(slot, 0);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = super.serializeNBT();
        ListTag listTag = new ListTag();
        this.counters.forEach((slot, progress) -> {
            CompoundTag progressTag = new CompoundTag();
            progressTag.putInt("slot", slot);
            progressTag.putInt("progress", progress);
            listTag.add(progressTag);
        });
        tag.put("Counters", listTag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        if (tag.contains("Counters")) {
            ListTag listTag = tag.getList("Counters", ListTag.TAG_COMPOUND);
            for (int i = 0; i < listTag.size(); i++) {
                CompoundTag progressTag = listTag.getCompound(i);
                this.counters.put(progressTag.getInt("slot"), progressTag.getInt("progress"));
            }
        }
        super.deserializeNBT(tag);
    }

}
