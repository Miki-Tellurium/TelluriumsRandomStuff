package com.mikitellurium.telluriumsrandomstuff.registry;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.common.capability.GrapplingHookCapability;
import com.mikitellurium.telluriumsrandomstuff.common.capability.GrapplingHookCapabilityProvider;
import com.mikitellurium.telluriumsrandomstuff.common.item.GrapplingHookItem;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import com.mikitellurium.telluriumsrandomstuff.util.LogUtils;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.entity.player.Player;
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
    }

}
