package com.mikitellurium.telluriumsrandomstuff.registry;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.client.gui.menu.ExtractorMenu;
import com.mikitellurium.telluriumsrandomstuff.client.gui.menu.SoulAnchorMenu;
import com.mikitellurium.telluriumsrandomstuff.client.gui.menu.SoulFurnaceMenu;
import com.mikitellurium.telluriumsrandomstuff.client.gui.menu.SoulInfuserMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {

    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, TelluriumsRandomStuffMod.MOD_ID);

    public static final RegistryObject<MenuType<SoulFurnaceMenu>> SOUL_FURNACE_MENU =
            registerMenuType(SoulFurnaceMenu::new, "soul_furnace_menu");

    public static final RegistryObject<MenuType<SoulAnchorMenu>> SOUL_ANCHOR_MENU =
            registerMenuType(SoulAnchorMenu::new, "soul_anchor_menu");

    public static final RegistryObject<MenuType<ExtractorMenu>> EXTRACTOR_MENU =
            registerMenuType(ExtractorMenu::new, "extractor_menu");

    public static final RegistryObject<MenuType<SoulInfuserMenu>> SOUL_INFUSER_MENU =
            registerMenuType(SoulInfuserMenu::new, "soul_infuser_menu");

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory,
                                                                                                  String name) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }

}
