package com.mikitellurium.telluriumsrandomstuff.registry;

import com.mikitellurium.telluriumsrandomstuff.common.capability.GrapplingHookCapabilityProvider;
import com.mikitellurium.telluriumsrandomstuff.test.bin.SoulStorage;
import com.mikitellurium.telluriumsrandomstuff.test.bin.SoulStorageCapabilityProvider;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

import java.util.concurrent.atomic.AtomicReference;

public class ModItemProperties {

    public static void register() {
        ItemProperties.register(ModItems.GRAPPLING_HOOK.get(), FastLoc.modLoc("thrown"),
                (itemStack, level, livingEntity, seed) -> {
                    if (livingEntity == null) return 0;
                    AtomicReference<Float> value = new AtomicReference<>(0.0F);
                    if (livingEntity instanceof Player player) {
                        player.getCapability(GrapplingHookCapabilityProvider.INSTANCE).ifPresent((hook) ->{
                            boolean shouldOverride = hook.isUsing() && ItemStack.matches(hook.getStack(), itemStack);
                            value.set(shouldOverride ? 1.0F : 0.0F);
                        });
                    }
                    return value.get();
                });
        ItemProperties.register(ModItems.GRAPPLING_HOOK.get(), FastLoc.modLoc("charging"),
                (itemStack, level, livingEntity, seed) -> {
                    return livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1.0F : 0.0F;
                });
        ItemProperties.register(ModItems.SPIRIT_BOTTLE.get(), FastLoc.modLoc("storage"),
                (itemStack, level, livingEntity, seed) -> itemStack.getCount() > 0 ? 1 : 0);
        ItemProperties.register(ModItems.SPIRITED_ALLAY_ITEM.get(), FastLoc.modLoc("color"),
                (itemStack, level, livingEntity, seed) -> {
                    CompoundTag tag = itemStack.getOrCreateTag();
                    if (tag.contains("SavedEntity", Tag.TAG_COMPOUND)) {
                        CompoundTag entityTag = tag.getCompound("SavedEntity");
                        if (entityTag.contains("Color", Tag.TAG_BYTE)) {
                            return entityTag.getByte("Color");
                        }
                    }
                    return DyeColor.LIGHT_BLUE.getId();
                });
    }

}
