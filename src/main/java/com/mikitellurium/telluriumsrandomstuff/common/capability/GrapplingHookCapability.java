package com.mikitellurium.telluriumsrandomstuff.common.capability;

import com.mikitellurium.telluriumsrandomstuff.common.entity.GrapplingHookEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

import java.util.function.Consumer;

@AutoRegisterCapability
public class GrapplingHookCapability {

    private GrapplingHookEntity hook;
    private boolean isUsing;

    public boolean isUsing() {
        return isUsing;
    }

    public void setUsing(boolean b) {
        this.isUsing = b;
    }

    public boolean isHookPresent() {
        return this.hook != null;
    }

    public void setGrappling(GrapplingHookEntity hook) {
        this.hook = hook;
        this.setUsing(true);
    }

    public void remove() {
        this.hook = null;
        this.setUsing(false);
    }

    public GrapplingHookEntity getHook() {
        return this.hook;
    }

    public void ifPresent(Consumer<GrapplingHookEntity> consumer) {
        if (this.isHookPresent()) {
            consumer.accept(this.hook);
        }
    }

    public void saveNBTDAta(CompoundTag nbt) {
    }

    public void loadNBTData(CompoundTag nbt) {
    }

}
