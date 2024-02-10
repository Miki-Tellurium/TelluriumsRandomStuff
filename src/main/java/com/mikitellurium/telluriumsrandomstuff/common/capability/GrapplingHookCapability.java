package com.mikitellurium.telluriumsrandomstuff.common.capability;

import com.mikitellurium.telluriumsrandomstuff.common.entity.GrapplingHookEntity;
import com.mikitellurium.telluriumsrandomstuff.common.item.GrapplingHookItem;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

import java.util.function.Consumer;

@AutoRegisterCapability
public class GrapplingHookCapability {

    private GrapplingHookEntity hook;
    private ItemStack itemStack;

    public boolean isPresent() {
        return this.hook != null;
    }

    public void setGrappling(GrapplingHookEntity hook, ItemStack itemStack) {
        this.hook = hook;
        if (itemStack.is(ModItems.GRAPPLING_HOOK.get())) {
            this.itemStack = itemStack;
            ((GrapplingHookItem)itemStack.getItem()).setPlayerIsUsing(itemStack, true);
        }
    }

    public void remove() {
        this.hook = null;
        if (this.itemStack != null) {
            ((GrapplingHookItem) this.itemStack.getItem()).setPlayerIsUsing(this.itemStack, false);
            this.itemStack = null;
        }
    }

    public GrapplingHookEntity getHook() {
        return this.hook;
    }

    public void ifPresent(Consumer<GrapplingHookEntity> consumer) {
        if (this.isPresent()) {
            consumer.accept(this.hook);
        }
    }

    public void saveNBTDAta(CompoundTag nbt) {
    }

    public void loadNBTData(CompoundTag nbt) {
    }

}
