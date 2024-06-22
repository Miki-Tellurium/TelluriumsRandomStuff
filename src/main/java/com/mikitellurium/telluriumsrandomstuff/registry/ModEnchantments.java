package com.mikitellurium.telluriumsrandomstuff.registry;

import com.mikitellurium.telluriumsrandomstuff.common.enchantment.AerodynamicsEnchantment;
import com.mikitellurium.telluriumsrandomstuff.common.enchantment.SoulHarvestingEnchantment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModEnchantments {

    public static RegistryObject<Enchantment> SOUL_HARVESTING = registerEnchantment("soul_harvesting",
            () -> new SoulHarvestingEnchantment(EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND));

    public static RegistryObject<Enchantment> AERODYNAMICS = registerEnchantment("aerodynamics",
            () -> new AerodynamicsEnchantment(EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND));

    private static <T extends Enchantment> RegistryObject<T> registerEnchantment(String name, Supplier<T> enchantment) {
        return ModRegistries.ENCHANTMENTS.register(name, enchantment);
    }

    protected static void register(IEventBus eventBus) {
        ModRegistries.ENCHANTMENTS.register(eventBus);
    }

}
