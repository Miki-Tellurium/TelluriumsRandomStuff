package com.mikitellurium.telluriumsrandomstuff.registry;

import com.mikitellurium.telluriumsrandomstuff.client.gui.menu.*;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {

    public static final RegistryObject<MenuType<SoulFurnaceMenu>> SOUL_FURNACE_MENU =
            registerMenuType("soul_furnace_menu", SoulFurnaceMenu::new);

    public static final RegistryObject<MenuType<SoulAnchorMenu>> SOUL_ANCHOR_MENU =
            registerMenuType("soul_anchor_menu", SoulAnchorMenu::new);

    public static final RegistryObject<MenuType<ExtractorMenu>> EXTRACTOR_MENU =
            registerMenuType("extractor_menu", ExtractorMenu::new);

    public static final RegistryObject<MenuType<SoulInfuserMenu>> SOUL_INFUSER_MENU =
            registerMenuType("soul_infuser_menu", SoulInfuserMenu::new);

    public static final RegistryObject<MenuType<AlchemixerMenu>> ALCHEMIXER_MENU =
            registerMenuType("alchemixer_menu", AlchemixerMenu::new);

    public static final RegistryObject<MenuType<SoulCompactorMenu>> SOUL_COMPACTOR_MENU =
            registerMenuType("soul_compactor_menu", SoulCompactorMenu::new);

    private static <M extends AbstractContainerMenu> RegistryObject<MenuType<M>> registerMenuType(String name, IContainerFactory<M> menu) {
        return ModRegistries.MENU_TYPES.register(name, () -> IForgeMenuType.create(menu));
    }

    protected static void register(IEventBus eventBus) {
        ModRegistries.MENU_TYPES.register(eventBus);
    }

}
