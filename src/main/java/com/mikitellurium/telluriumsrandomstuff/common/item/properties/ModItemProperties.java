package com.mikitellurium.telluriumsrandomstuff.common.item.properties;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.entity.player.Player;

public class ModItemProperties {

    public static void register() {
        ItemProperties.register(ModItems.GRAPPLING_HOOK.get(), TelluriumsRandomStuffMod.modLoc("launched"),
                (itemStack, level, livingEntity, seed) -> {
                    if (livingEntity == null) return 0;
                    if (livingEntity instanceof Player player) {

                    }
                    return 0;
                });
    }

}
