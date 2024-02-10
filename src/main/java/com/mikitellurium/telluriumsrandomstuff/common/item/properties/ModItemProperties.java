package com.mikitellurium.telluriumsrandomstuff.common.item.properties;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.common.capability.GrapplingHookCapability;
import com.mikitellurium.telluriumsrandomstuff.common.capability.GrapplingHookCapabilityProvider;
import com.mikitellurium.telluriumsrandomstuff.common.item.GrapplingHookItem;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import com.mikitellurium.telluriumsrandomstuff.util.LogUtils;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.entity.player.Player;

import java.util.concurrent.atomic.AtomicReference;

public class ModItemProperties {

    public static void register() {
        ItemProperties.register(ModItems.GRAPPLING_HOOK.get(), TelluriumsRandomStuffMod.modLoc("thrown"),
                (itemStack, level, livingEntity, seed) -> {
                    if (livingEntity == null) return 0;
                    AtomicReference<Float> value = new AtomicReference<>(0.0F);
                    if (livingEntity instanceof Player player) {
                        player.getCapability(GrapplingHookCapabilityProvider.INSTANCE).ifPresent((hook) ->{
                            value.set(hook.isUsing() ? 1.0F : 0.0F);
                        });
                    }
                    return value.get();
                });
    }

}
