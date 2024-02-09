package com.mikitellurium.telluriumsrandomstuff.common.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class GrapplingHookCapability {

    private boolean isGrappling;

    public boolean isGrappling() {
        return isGrappling;
    }

    public void setGrappling(boolean grappling) {
        isGrappling = grappling;
    }

    public void copyFrom(GrapplingHookCapability source) {
        this.isGrappling = source.isGrappling;
    }

    public void saveNBTDAta(CompoundTag nbt) {
        nbt.putBoolean("isGrappling", isGrappling);
    }

    public void loadNBTData(CompoundTag nbt) {
        this.isGrappling = nbt.getBoolean("isGrappling");
    }

}
