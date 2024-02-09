package com.mikitellurium.telluriumsrandomstuff.common.capability;

import com.mikitellurium.telluriumsrandomstuff.common.entity.GrapplingHookEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

import java.util.UUID;
import java.util.function.Consumer;

@AutoRegisterCapability
public class GrapplingHookCapability {

    private UUID hook;

    public boolean isPresent(ServerLevel level) {
        return this.getHook(level) != null;
    }

    public void setGrappling(GrapplingHookEntity hook) {
        this.hook = hook.getUUID();
    }

    public void remove() {
        this.hook = null;
    }

    public GrapplingHookEntity getHook(ServerLevel level) {
        Entity entity = level.getEntity(this.hook);
        return entity instanceof GrapplingHookEntity ? (GrapplingHookEntity)entity : null;
    }

    public void ifPresent(ServerLevel level, Consumer<GrapplingHookEntity> consumer) {
        if (this.isPresent(level)) {
            consumer.accept(this.getHook(level));
        }
    }

    public void copyFrom(GrapplingHookCapability source) {
        this.hook = source.hook;
    }

    public void saveNBTDAta(CompoundTag nbt) {
        if (this.hook != null) {
            nbt.putUUID("hook", this.hook);
        }
    }

    public void loadNBTData(CompoundTag nbt) {
        if (nbt.hasUUID("hook")) {
            this.hook = nbt.getUUID("hook");
        }
    }

}
