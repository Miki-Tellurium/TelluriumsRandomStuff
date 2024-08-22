package com.mikitellurium.telluriumsrandomstuff.lib;

import net.minecraft.core.Direction;
import net.minecraftforge.common.util.LazyOptional;

public interface SidedCapabilityProvider<T> {

    T capabilityBySide(Direction side);

    default LazyOptional<T> sidedLazyOptional(Direction side) {
        return LazyOptional.of(() -> this.capabilityBySide(side));
    }

}
