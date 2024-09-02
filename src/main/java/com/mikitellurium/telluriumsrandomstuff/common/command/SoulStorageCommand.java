package com.mikitellurium.telluriumsrandomstuff.common.command;

import com.mikitellurium.telluriumsrandomstuff.common.capability.SoulStorage;
import com.mikitellurium.telluriumsrandomstuff.common.item.SoulStorageItem;
import com.mikitellurium.telluriumsrandomstuff.util.LogUtils;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

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
                                .then(Commands.argument("count", IntegerArgumentType.integer(1, Short.MAX_VALUE))
                                        .executes((context) -> add(context.getSource(), LivingEntityArgument.getEntityId(context, "entity"), IntegerArgumentType.getInteger(context, "count"))))))
                .then(Commands.literal("remove")
                        .then(Commands.argument("entity", LivingEntityArgument.livingEntity())
                                .then(Commands.argument("count", IntegerArgumentType.integer(1, Short.MAX_VALUE))
                                        .executes((context) -> remove(context.getSource(), LivingEntityArgument.getEntityId(context, "entity"), IntegerArgumentType.getInteger(context, "count"))))))
                .then(Commands.literal("clear")
                        .executes((context) -> clear(context.getSource())))
        );
    }

    private static int set(CommandSourceStack source, String entityId, int count) {
        return execute(source, (soulStorage) -> {
            soulStorage.set(entityId, count);
            String s = count + " " + entityId;
            source.sendSuccess(() -> Component.translatable("command.telluriumsrandomstuff.item.soulstorage.set", s), true);
        });
    }

    private static int add(CommandSourceStack source, String entityId, int count) {
        return execute(source, (soulStorage) -> {
            soulStorage.grow(entityId, count);
            String s = count + " " + entityId;
            source.sendSuccess(() -> Component.translatable("command.telluriumsrandomstuff.item.soulstorage.add", s), true);
        });
    }

    private static int remove(CommandSourceStack source, String entityId, int count) {
        return execute(source, (soulStorage) -> {
            soulStorage.shrink(entityId, count);
            String s = count + " " + entityId;
            source.sendSuccess(() -> Component.translatable("command.telluriumsrandomstuff.item.soulstorage.remove", s), true);
        });
    }

    private static int clear(CommandSourceStack source) {
        return execute(source, (soulStorage) -> {
            soulStorage.clear();
            source.sendSuccess(() -> Component.translatable("command.telluriumsrandomstuff.item.soulstorage.clear"), true);
        });
    }

    private static int execute(CommandSourceStack source, Consumer<SoulStorage> consumer) {
        if (!(source.source instanceof Player)) {
            source.sendFailure(Component.translatable("argument.entity.notfound.player"));
        }
        Player player = Minecraft.getInstance().player; // Send to client to avoid de-sync issues
        if (player != null) {
            ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
            if (SoulStorageItem.isSoulStorageItem(itemStack)) {
                SoulStorage.performAction(itemStack, consumer);
            } else {
                source.sendFailure(Component.translatable("command.telluriumsrandomstuff.item.soulstorage.notfound", itemStack.getItem().getName(itemStack)));
            }
        } else {
            source.sendFailure(Component.translatable("argument.entity.notfound.player"));
        }
        return 1;
    }

}
