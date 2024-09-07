package com.mikitellurium.telluriumsrandomstuff.common.capability;

import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GrapplingHookCapabilityProvider implements ICapabilityProvider {

    public static Capability<GrapplingHookCapability> INSTANCE = CapabilityManager.get(new CapabilityToken<>() {});

    private GrapplingHookCapability cap = null;
    private final LazyOptional<GrapplingHookCapability> holder = LazyOptional.of(this::create);

    private GrapplingHookCapability create() {
        if (cap == null) {
            cap = new GrapplingHookCapability();
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

}
