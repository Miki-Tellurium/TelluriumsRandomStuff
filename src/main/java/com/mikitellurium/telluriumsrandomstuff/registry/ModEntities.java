package com.mikitellurium.telluriumsrandomstuff.registry;

import com.mikitellurium.telluriumsrandomstuff.common.entity.GrapplingHookEntity;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModEntities {



    public static final RegistryObject<EntityType<GrapplingHookEntity>> GRAPPLING_HOOK = registerEntity("grappling_hook",
            () -> EntityType.Builder.<GrapplingHookEntity>of(GrapplingHookEntity::new, MobCategory.MISC)
                    .noSave()
                    .noSummon()
                    .sized(0.4F, 0.4F)
                    .clientTrackingRange(4)
                    .updateInterval(5)
                    .build("grappling_hook"));

    private static <E extends Entity, T extends EntityType<E>> RegistryObject<T> registerEntity(String name, Supplier<T> entity) {
        return ModRegistries.ENTITY_TYPES.register(name, entity);
    }

    protected static void register(IEventBus eventBus) {
        ModRegistries.ENTITY_TYPES.register(eventBus);
    }

}
