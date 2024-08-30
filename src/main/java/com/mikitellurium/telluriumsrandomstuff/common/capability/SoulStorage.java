package com.mikitellurium.telluriumsrandomstuff.common.capability;

import com.mikitellurium.telluriumsrandomstuff.common.item.SoulStorageItem;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@AutoRegisterCapability
public class SoulStorage {

    private final Map<String, Integer> souls;

    public SoulStorage() {
        this.souls = new HashMap<>();
    }

    private SoulStorage(Map<String, Integer> souls) {
        this.souls = souls;
    }

    public void storeNbt(CompoundTag tag) {
        CompoundTag storedSouls = new CompoundTag();
        this.souls.forEach(storedSouls::putInt);
        tag.put("StoredSouls", storedSouls);
    }

    public void readNBT(CompoundTag tag) {
        CompoundTag storedSouls = this.getStoredSoulsTag(tag);
        storedSouls.getAllKeys().forEach((key) -> this.souls.put(key, storedSouls.getInt(key)));
    }

    private CompoundTag getStoredSoulsTag(CompoundTag tag) {
        if (!tag.contains("StoredSouls")) {
            tag.put("StoredSouls", new CompoundTag());
        }
        return tag.getCompound("StoredSouls");
    }

    public static void performAction(ItemStack itemStack, Consumer<SoulStorage> consumer) {
        itemStack.getCapability(SoulStorageCapabilityProvider.INSTANCE).ifPresent(consumer::accept);
    }

    public static void performAction(ItemStack firstStack, ItemStack secondStack, BiConsumer<SoulStorage, SoulStorage> consumer) {
        firstStack.getCapability(SoulStorageCapabilityProvider.INSTANCE).ifPresent((firstStorage) -> {
            secondStack.getCapability(SoulStorageCapabilityProvider.INSTANCE).ifPresent((secondStorage) -> {
                consumer.accept(firstStorage, secondStorage);
            });
        });
    }

    public static void moveRandomSoul(ItemStack senderStack, ItemStack receiverStack, RandomSource random) {
        performAction(senderStack, receiverStack, (sender, receiver) -> {
            if (!sender.isEmpty()) {
                String key = sender.getRandomKey(random);
                sender.shrink(key, 1);
                receiver.grow(key, 1);
            }
        });
    }

    public static void moveRandomSouls(ItemStack senderStack, ItemStack receiverStack, int amount, RandomSource random) {
        for (int i = 0; i < amount; i++) {
            moveRandomSoul(senderStack, receiverStack, random);
        }
    }

    public static void moveAllSouls(ItemStack senderStack, ItemStack receiverStack) {
        performAction(senderStack, receiverStack, (sender, receiver) -> {
            receiver.addAll(sender);
        });
    }

    public void set(String key, int amount) {
        if (key == null) throw new IllegalArgumentException("Key can't be null");
        if (amount < 1) throw new IllegalArgumentException("Amount must be higher than 0");
        this.souls.put(key, amount);
    }

    public void remove(String key) {
        this.souls.remove(key);
    }

    public boolean isEmpty() {
        return this.souls.isEmpty();
    }

    public Map<String, Integer> getSouls() {
        return this.souls;
    }

    public int getCount() {
        AtomicInteger count = new AtomicInteger();
        this.souls.forEach((key, i) -> count.addAndGet(i));
        return count.get();
    }

    public int getCount(String key) {
        return this.souls.get(key);
    }

    public String getRandomKey(RandomSource random) {
        if (!this.isEmpty()) {
            String[] strings = this.souls.keySet().toArray(String[]::new);
            return Util.getRandom(strings, random);
        }
        return null;
    }

    public void grow(String key, int amount) {
        if (key == null) throw new IllegalArgumentException("Key can't be null");
        if (this.souls.containsKey(key)) {
            this.souls.put(key, this.souls.get(key) + amount);
        } else {
            this.souls.put(key, amount);
        }
        if (this.souls.get(key) < 1) {
            this.remove(key);
        }
    }

    public void shrink(String key, int amount) {
        this.grow(key, -amount);
    }

    public void addAll(SoulStorage soulStorage) {
        soulStorage.souls.forEach(this::grow);
    }

    public void clear() {
        this.souls.clear();
    }

}
