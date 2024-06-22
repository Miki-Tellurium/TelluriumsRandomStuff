package com.mikitellurium.telluriumsrandomstuff.registry;

import com.mikitellurium.telluriumsrandomstuff.common.worldgen.structure.AncientBridge;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModStructures {

    public static final RegistryObject<StructureType<AncientBridge>> ANCIENT_BRIDGE = registerStructure("ancient_bridge",
            () -> AncientBridge::codec);

    private static <S extends Structure, T extends StructureType<S>> RegistryObject<T> registerStructure(String name, Supplier<T> structure) {
        return ModRegistries.STRUCTURES.register(name, structure);
    }

    protected static void register(IEventBus eventBus) {
        ModRegistries.STRUCTURES.register(eventBus);
    }

}
