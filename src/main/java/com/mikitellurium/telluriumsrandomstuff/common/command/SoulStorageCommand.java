package com.mikitellurium.telluriumsrandomstuff.common.command;

import com.mikitellurium.telluriumsrandomstuff.common.capability.SoulStorage;
import com.mikitellurium.telluriumsrandomstuff.common.item.SoulStorageItem;
import com.mikitellurium.telluriumsrandomstuff.util.LogUtils;
import com.mikitellurium.telluriumsrandomstuff.util.RegistryHelper;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class SoulStorageCommand {

    public static LiteralArgumentBuilder<CommandSourceStack> build(LiteralArgumentBuilder<CommandSourceStack> builder) {
        return builder.then(Commands.literal("soulStorage")
                .then(Commands.literal("set")
                        .then(Commands.argument("entity", LivingEntityArgument.livingEntity())
                                .then(Commands.argument("count", IntegerArgumentType.integer(1, Short.MAX_VALUE))
                                        .executes((context) -> set(context.getSource(), LivingEntityArgument.getEntityId(context, "entity"), IntegerArgumentType.getInteger(context, "count"))))))
                .then(Commands.literal("add")
                        .then(Commands.argument("entity", LivingEntityArgument.livingEntity())
                                .executes((context) -> add(context.getSource(), LivingEntityArgument.getEntityId(context, "entity"), 1))
                                .then(Commands.argument("count", IntegerArgumentType.integer(1, Short.MAX_VALUE))
                                        .executes((context) -> add(context.getSource(), LivingEntityArgument.getEntityId(context, "entity"), IntegerArgumentType.getInteger(context, "count"))))))
                .then(Commands.literal("remove")
                        .then(Commands.argument("entity", LivingEntityArgument.livingEntity())
                                .executes((context) -> remove(context.getSource(), LivingEntityArgument.getEntityId(context, "entity"), 1))
                                .then(Commands.argument("count", IntegerArgumentType.integer(1, Short.MAX_VALUE))
                                        .executes((context) -> remove(context.getSource(), LivingEntityArgument.getEntityId(context, "entity"), IntegerArgumentType.getInteger(context, "count"))))))
                .then(Commands.literal("clear")
                        .executes((context) -> clear(context.getSource())))
        );
    }

    private static int set(CommandSourceStack source, String entityId, int count) {
        AtomicReference<String> s = new AtomicReference<>();
        return execute(source, (soulStorage) -> {
            Optional<EntityType<?>> optional = RegistryHelper.getRegistryOptional(ForgeRegistries.ENTITY_TYPES, entityId);
            if (optional.isPresent()) {
                soulStorage.set(optional.get(), count);
                s.set(Math.min(count, soulStorage.getCapacity()) + " " + entityId);
            } else {
                source.sendFailure(Component.translatable("argument.entity.notfound.entity"));
            }
        }, Component.translatable("command.telluriumsrandomstuff.item.soulstorage.set", s));
    }

    private static int add(CommandSourceStack source, String entityId, int count) {
        AtomicReference<String> s = new AtomicReference<>();
        return execute(source, (soulStorage) -> {
            Optional<EntityType<?>> optional = RegistryHelper.getRegistryOptional(ForgeRegistries.ENTITY_TYPES, entityId);
            if (optional.isPresent()) {
                int i = soulStorage.grow(optional.get(), count, false);
                s.set(i + " " + entityId);
            } else {
                source.sendFailure(Component.translatable("argument.entity.notfound.entity"));
            }
        }, Component.translatable("command.telluriumsrandomstuff.item.soulstorage.add", s));
    }

    private static int remove(CommandSourceStack source, String entityId, int count) {
        AtomicReference<String> s = new AtomicReference<>();
        return execute(source, (soulStorage) -> {
            Optional<EntityType<?>> optional = RegistryHelper.getRegistryOptional(ForgeRegistries.ENTITY_TYPES, entityId);
            if (optional.isPresent()) {
                int i = soulStorage.shrink(optional.get(), count, false);
                s.set(i + " " + entityId);
            } else {
                source.sendFailure(Component.translatable("argument.entity.notfound.entity"));
            }
        }, Component.translatable("command.telluriumsrandomstuff.item.soulstorage.remove", s));
    }

    private static int clear(CommandSourceStack source) {
        return execute(source, SoulStorage::clear, Component.translatable("command.telluriumsrandomstuff.item.soulstorage.clear"));
    }

    private static int execute(CommandSourceStack source, Consumer<SoulStorage> consumer, Component successMessage) {
        if (!(source.source instanceof Player)) {
            source.sendFailure(Component.translatable("argument.entity.notfound.player"));
        }
        List<Player> players = Arrays.asList(source.getPlayer(), Minecraft.getInstance().player); // Send to client to avoid de-sync issues
        for (Player player : players) {
            if (player != null) {
                ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
                if (SoulStorageItem.isSoulStorageItem(itemStack)) {
                    SoulStorage.performAction(itemStack, consumer);
                    if (player instanceof LocalPlayer) source.sendSuccess(() -> successMessage, true);
                } else {
                    source.sendFailure(Component.translatable("command.telluriumsrandomstuff.item.soulstorage.notfound", itemStack.getItem().getName(itemStack)));
                }
            } else {
                source.sendFailure(Component.translatable("argument.entity.notfound.player"));
            }
        }
        return 1;
    }

}
