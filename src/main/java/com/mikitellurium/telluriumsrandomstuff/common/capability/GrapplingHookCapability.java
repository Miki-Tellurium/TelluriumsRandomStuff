package com.mikitellurium.telluriumsrandomstuff.common.capability;

import com.mikitellurium.telluriumsrandomstuff.common.entity.GrapplingHookEntity;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Consumer;

@AutoRegisterCapability
public class GrapplingHookCapability {

    private GrapplingHookEntity hook;
    private boolean isUsing;
    private ItemStack stack;

    public boolean isUsing() {
        return isUsing;
    }

    public void setUsing(boolean b) {
        this.isUsing = b;
    }

    public ItemStack getStack() {
        return stack;
    }

    public void setStack(ItemStack stack) {
        this.stack = stack;
    }

    public boolean isHookPresent() {
        return this.hook != null;
    }

    public void setGrappling(GrapplingHookEntity hook, ItemStack itemStack) {
        this.hook = hook;
        this.setUsing(true);
        this.setStack(itemStack);
    }

    public void remove() {
        this.hook = null;
        this.setUsing(false);
        this.stack = null;
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
