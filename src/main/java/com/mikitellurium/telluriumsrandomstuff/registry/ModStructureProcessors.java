package com.mikitellurium.telluriumsrandomstuff.registry;

import com.mikitellurium.telluriumsrandomstuff.common.worldgen.structure.processor.WriteSignProcessor;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModStructureProcessors {

    public static RegistryObject<StructureProcessorType<WriteSignProcessor>> WRITE_SIGN = registerProcessor("write_sign",
            () -> WriteSignProcessor::codec);

    private static <P extends StructureProcessor, T extends StructureProcessorType<P>> RegistryObject<T> registerProcessor(String name, Supplier<T> processor) {
        return ModRegistries.STRUCTURE_PROCESSORS.register(name, processor);
    }

    protected static void register(IEventBus eventBus) {
        ModRegistries.STRUCTURE_PROCESSORS.register(eventBus);
    }

}
