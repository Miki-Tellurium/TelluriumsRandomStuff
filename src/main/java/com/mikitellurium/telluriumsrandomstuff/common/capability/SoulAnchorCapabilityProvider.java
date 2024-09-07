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

public class SoulAnchorCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<SoulAnchorCapability> INSTANCE = CapabilityManager.get(new CapabilityToken<>() {});

    private SoulAnchorCapability cap = null;
    private final LazyOptional<SoulAnchorCapability> holder = LazyOptional.of(this::create);

    private SoulAnchorCapability create() {
        if (cap == null) {
            cap = new SoulAnchorCapability();
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
        create().saveNBTDAta(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        create().loadNBTData(nbt);
    }

}
