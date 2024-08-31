package com.mikitellurium.telluriumsrandomstuff.registry;

import com.mikitellurium.telluriumsrandomstuff.common.command.DyeColorArgument;
import com.mikitellurium.telluriumsrandomstuff.common.command.LivingEntityArgument;
import com.mojang.brigadier.arguments.ArgumentType;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModArgumentTypes {

    public static final RegistryObject<ArgumentTypeInfo<DyeColorArgument, SingletonArgumentInfo<DyeColorArgument>.Template>> DYE_COLOR = register("dye_color",
            () -> ArgumentTypeInfos.registerByClass(DyeColorArgument.class, SingletonArgumentInfo.contextFree(DyeColorArgument::dyeColor)));

    public static final RegistryObject<ArgumentTypeInfo<LivingEntityArgument, SingletonArgumentInfo<LivingEntityArgument>.Template>> LIVING_ENTITY = register("living_entity",
            () -> ArgumentTypeInfos.registerByClass(LivingEntityArgument.class, SingletonArgumentInfo.contextFree(LivingEntityArgument::livingEntity)));

    public static <A extends ArgumentType<?>, T extends ArgumentTypeInfo.Template<A>> RegistryObject<ArgumentTypeInfo<A, T>> register(
            String name, Supplier<ArgumentTypeInfo<A, T>> argumentType) {
        return ModRegistries.ARGUMENT_TYPES.register(name, argumentType);
    }

    protected static void register(IEventBus eventBus) {
        ModRegistries.ARGUMENT_TYPES.register(eventBus);
    }

}
