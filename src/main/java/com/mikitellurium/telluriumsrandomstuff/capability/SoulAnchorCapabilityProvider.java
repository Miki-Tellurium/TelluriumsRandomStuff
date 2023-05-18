package com.mikitellurium.telluriumsrandomstuff.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SoulAnchorCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<SoulAnchorCapability> SOUL_ANCHOR_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});

    private SoulAnchorCapability soulAnchorCapability = null;
    private final LazyOptional<SoulAnchorCapability> optional = LazyOptional.of(this::createSoulAnchorCapability);

    private SoulAnchorCapability createSoulAnchorCapability() {
        if (soulAnchorCapability == null) {
            soulAnchorCapability = new SoulAnchorCapability();
        }

        return soulAnchorCapability;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == SOUL_ANCHOR_CAPABILITY) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createSoulAnchorCapability().saveNBTDAta(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createSoulAnchorCapability().loadNBTData(nbt);
    }

}
