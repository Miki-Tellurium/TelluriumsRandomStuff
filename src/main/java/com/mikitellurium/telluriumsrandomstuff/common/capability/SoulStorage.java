package com.mikitellurium.telluriumsrandomstuff.common.capability;

import com.google.common.collect.Maps;
import com.mikitellurium.telluriumsrandomstuff.util.RegistryHelper;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@AutoRegisterCapability
public class SoulStorage {

    private final Map<EntityType<?>, Integer> souls = Maps.newLinkedHashMap();
    private final int typesCapacity;
    private final int capacity;

    public SoulStorage() {
        this(1, 1);
    }

    public SoulStorage(int typesCapacity, int capacity) {
        this.typesCapacity = typesCapacity;
        this.capacity = capacity;
    }

    @SuppressWarnings("ConstantConditions")
    public void storeNbt(CompoundTag tag) {
        CompoundTag storedSouls = new CompoundTag();
        this.souls.forEach((entityType, i) -> {
            String s = ForgeRegistries.ENTITY_TYPES.getKey(entityType).toString();
            storedSouls.putInt(s, i);
        });
        tag.put("StoredSouls", storedSouls);
    }

    public void readNBT(CompoundTag tag) {
        CompoundTag storedSouls = this.getStoredSoulsTag(tag);
        storedSouls.getAllKeys().forEach((s) -> {
            RegistryHelper.getRegistryOptional(ForgeRegistries.ENTITY_TYPES, s)
                    .ifPresent((entityType) -> this.souls.put(entityType, storedSouls.getInt(s)));
        });
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

    public static boolean evaluate(ItemStack itemStack, Predicate<SoulStorage> predicate) {
        AtomicBoolean b = new AtomicBoolean();
        performAction(itemStack, (storage) -> b.set(predicate.test(storage)));
        return b.get();
    }

    public static void moveRandomSoul(ItemStack senderStack, ItemStack receiverStack, RandomSource random) {
        performAction(senderStack, receiverStack, (sender, receiver) -> {
            if (!sender.isEmpty()) {
                EntityType<?> entity = sender.getRandom(random);
                sender.shrink(entity, 1, false);
                receiver.grow(entity, 1, false);
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

    public static SoulStorage copyFrom(ItemStack itemStack) {
        AtomicReference<SoulStorage> newStorage = new AtomicReference<>();
        SoulStorage.performAction(itemStack, (soulStorage) -> {
            newStorage.set(soulStorage.copy());
        });
        return newStorage.get();
    }

    public EntityType<?> getRandom(RandomSource random) {
        return this.getRandom(random, (s, i) -> true);
    }

    public EntityType<?> getRandom(RandomSource random, BiPredicate<EntityType<?>, Integer> filter) {
        if (!this.isEmpty()) {
            List<EntityType<?>> entities = this.getEntities(filter);
            return entities.isEmpty() ? null : Util.getRandom(entities, random);
        }
        return null;
    }

    public List<EntityType<?>> getEntities() {
        return this.getEntities((e, i) -> true);
    }

    public List<EntityType<?>> getEntities(BiPredicate<EntityType<?>, Integer> filter) {
        return this.souls.entrySet().stream()
                .filter((entry) -> filter.test(entry.getKey(), entry.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public void remove(EntityType<?> entity) {
        this.souls.remove(entity);
    }

    public boolean contains(EntityType<?> entity) {
        return this.souls.containsKey(entity);
    }

    public boolean isEmpty() {
        return this.souls.isEmpty();
    }

    public int getTypesCapacity() {
        return this.typesCapacity;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public int getTypes() {
        return this.souls.size();
    }

    public int getAmount(EntityType<?> entity) {
        Integer count = this.souls.get(entity);
        return count == null ? 0 : count;
    }

    public boolean canInsert(EntityType<?> entity) {
        if (!this.contains(entity) && this.souls.size() >= this.typesCapacity) return false;
        return this.getAmount(entity) < this.capacity;
    }

    public void set(EntityType<?> entity, int amount) {
        this.validate(entity);
        if (amount <= 0) {
            this.remove(entity);
        } else if (amount < this.getAmount(entity)) {
            this.souls.put(entity, amount);
        } else if (this.canInsert(entity)) {
            int i = Math.min(amount, this.capacity);
            this.souls.put(entity, i);
        }
    }
    
    // Returns the inserted amount
    public int grow(EntityType<?> entity, int amount, boolean simulate) {
        if (amount == 0) return 0;
        if (!this.canInsert(entity)) return 0;
        int sum = this.getAmount(entity) + amount;
        if (sum <= this.capacity) {
            if(!simulate) this.set(entity, sum);
        } else {
            int remainder = sum - this.capacity;
            amount -= remainder;
            if(!simulate) this.set(entity, this.getAmount(entity) + amount);
        }
        return amount;
    }
    
    // Returns the removed amount
    public int shrink(EntityType<?> entity, int amount, boolean simulate) {
        if (amount == 0) return 0;
        int remainder = this.getAmount(entity) - amount;
        if (remainder >= 0) {
            if(!simulate) this.set(entity, remainder);
        } else {
            amount = this.getAmount(entity);
            if(!simulate) this.set(entity, 0);
        }
        return amount;
    }

    public void addAll(SoulStorage soulStorage) {
        soulStorage.souls.forEach((entity, amount) -> this.grow(entity, amount, false));
    }

    public void clear() {
        this.souls.clear();
    }

    public void forEach(BiConsumer<EntityType<?>, Integer> consumer) {
        this.souls.forEach(consumer);
    }

    public SoulStorage copy() {
        SoulStorage newStorage = new SoulStorage(this.typesCapacity, this.capacity);
        this.forEach(newStorage::set);
        return newStorage;
    }

    private void validate(EntityType<?> entity) {
        if (entity == null) throw new IllegalArgumentException("Entity can't be null");
    }
    
}
