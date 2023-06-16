package com.mikitellurium.telluriumsrandomstuff.item.armortrim;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.item.ModItems;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;

import java.util.Map;

public class ModArmorTrims {

    //todo: armor trims

    public static final ResourceKey<TrimMaterial> OPAL_CRYSTAL = createTrimMaterial("opal_crystal");

    public static void createMaterials(BootstapContext<TrimMaterial> bootstapContext) {
        createMaterial(bootstapContext, OPAL_CRYSTAL, ModItems.OPAL_CRYSTAL.get(), Style.EMPTY.withColor(0xFFFFFF), 0.1F);
    }

    private static void createMaterial(BootstapContext<TrimMaterial> bootstapContext, ResourceKey<TrimMaterial> resourceKey,
                                       Item item, Style style, float f) {
        createMaterial(bootstapContext, resourceKey, item, style, f, Map.of());
    }

    private static void createMaterial(BootstapContext<TrimMaterial> bootstapContext, ResourceKey<TrimMaterial> resourceKey,
                                       Item item, Style style, float f, Map<ArmorMaterials, String> map) {
        TrimMaterial trimMaterial = TrimMaterial.create(resourceKey.location().getPath(), item, f,
                Component.translatable(Util.makeDescriptionId("trim_material", resourceKey.location())).withStyle(style), map);
        bootstapContext.register(resourceKey, trimMaterial);
    }

    private static ResourceKey<TrimMaterial> createTrimMaterial(String string) {
        return ResourceKey.create(Registries.TRIM_MATERIAL, new ResourceLocation(TelluriumsRandomStuffMod.MOD_ID, string));
    }

}
