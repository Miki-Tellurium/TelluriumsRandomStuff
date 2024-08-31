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

public class GrapplingHookCapabilityProvider implements ICapabilityProvider {
    // todo add abstraction to providers
    public static Capability<GrapplingHookCapability> INSTANCE = CapabilityManager.get(new CapabilityToken<>() {});

    private GrapplingHookCapability grapplingHookCapability = null;
    private final LazyOptional<GrapplingHookCapability> optional = LazyOptional.of(this::createGrapplingHookCapability);

    private GrapplingHookCapability createGrapplingHookCapability() {
        if (grapplingHookCapability == null) {
            grapplingHookCapability = new GrapplingHookCapability();
        }

        return grapplingHookCapability;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == INSTANCE) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

}
