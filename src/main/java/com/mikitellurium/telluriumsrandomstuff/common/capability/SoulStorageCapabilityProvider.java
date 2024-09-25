package com.mikitellurium.telluriumsrandomstuff.common.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SoulStorageCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<SoulStorage> INSTANCE = CapabilityManager.get(new CapabilityToken<>() {});

    private SoulStorage cap = null;
    private final LazyOptional<SoulStorage> holder = LazyOptional.of(this::create);
    private final int typesCapacity;
    private final int capacity;

    public SoulStorageCapabilityProvider(int typesCapacity, int capacity) {
        this.typesCapacity = typesCapacity;
        this.capacity = capacity;
    }

    private SoulStorage create() {
        if (cap == null) {
            cap = new SoulStorage(this.typesCapacity, this.capacity);
        }

        return cap;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == INSTANCE) {
            return holder.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        create().storeNbt(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        create().readNBT(nbt);
    }

}
