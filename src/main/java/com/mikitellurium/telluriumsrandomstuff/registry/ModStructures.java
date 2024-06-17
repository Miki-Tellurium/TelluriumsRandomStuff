package com.mikitellurium.telluriumsrandomstuff.registry;

import com.mikitellurium.telluriumsrandomstuff.common.worldgen.structure.AncientBridge;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModStructures {

    public static final DeferredRegister<StructureType<?>> STRUCTURES =
            DeferredRegister.create(Registries.STRUCTURE_TYPE, FastLoc.modId());

    public static final RegistryObject<StructureType<AncientBridge>> ANCIENT_BRIDGE = STRUCTURES.register("ancient_bridge",
            () -> AncientBridge::codec);

    public static void register(IEventBus eventBus) {
        STRUCTURES.register(eventBus);
    }

}
