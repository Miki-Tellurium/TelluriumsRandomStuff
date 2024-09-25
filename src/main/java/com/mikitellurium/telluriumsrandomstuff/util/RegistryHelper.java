package com.mikitellurium.telluriumsrandomstuff.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Optional;

public class RegistryHelper {

    public static <T> Optional<T> getRegistryOptional(IForgeRegistry<T> registry, ResourceLocation id) {
        return registry.containsKey(id) ? Optional.ofNullable(registry.getValue(id)) : Optional.empty();
    }

    public static <T> Optional<T> getRegistryOptional(IForgeRegistry<T> registry, String id) {
        return getRegistryOptional(registry, ResourceLocation.tryParse(id));
    }

}
