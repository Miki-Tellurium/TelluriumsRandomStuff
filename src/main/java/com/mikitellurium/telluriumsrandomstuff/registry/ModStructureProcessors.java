package com.mikitellurium.telluriumsrandomstuff.registry;

import com.mikitellurium.telluriumsrandomstuff.common.worldgen.processor.WriteSignProcessor;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModStructureProcessors {

    public static DeferredRegister<StructureProcessorType<?>> PROCESSORS =
            DeferredRegister.create(Registries.STRUCTURE_PROCESSOR, FastLoc.modId());

    public static RegistryObject<StructureProcessorType<WriteSignProcessor>> WRITE_SIGN = PROCESSORS.register("write_sign",
            () -> WriteSignProcessor::codec);
    
    public static void register(IEventBus eventBus) {
        PROCESSORS.register(eventBus);
    }

}
