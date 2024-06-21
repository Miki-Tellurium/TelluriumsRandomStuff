package com.mikitellurium.telluriumsrandomstuff.common.command;

import com.mikitellurium.telluriumsrandomstuff.common.item.LavaGooglesItem;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

import java.util.Collection;

public class LavaGooglesCommand {

    public static LiteralArgumentBuilder<CommandSourceStack> build(LiteralArgumentBuilder<CommandSourceStack> builder) {
        return builder.then(Commands.literal("giveLavaGoogles")
                        .then(Commands.argument("targets", EntityArgument.players())
                        .executes((context) ->
                                giveGoogles(context.getSource(),
                                        EntityArgument.getPlayers(context, "targets")))
                        .then(Commands.argument("color", DyeColorArgument.dyeColor())
                        .executes((context) ->
                                giveGoogles(context.getSource(),
                                        EntityArgument.getPlayers(context, "targets"),
                                        DyeColorArgument.getDyeColor(context, "color"))
                        ))));
    }

    private static int giveGoogles(CommandSourceStack source, Collection<ServerPlayer> targets) {
        return giveGoogles(source, targets, null);
    }

    private static int giveGoogles(CommandSourceStack source, Collection<ServerPlayer> targets, DyeColor dyeColor) {
        for (ServerPlayer player : targets) {
            ItemStack googles = new ItemStack(ModItems.LAVA_GOOGLES.get());
            if (dyeColor != null) {
                LavaGooglesItem.setColor(googles, dyeColor);
            }
            addStackToPlayerInventory(player, googles);
            source.sendSuccess(() -> Component.translatable("commands.give.success.single", 1,
                    googles.getDisplayName(), player.getDisplayName()), true);
        }
        return 1;
    }

    private static void addStackToPlayerInventory(ServerPlayer player, ItemStack itemStack) {
        boolean flag = player.getInventory().add(itemStack);
        if (flag && itemStack.isEmpty()) {
            itemStack.setCount(1);
            ItemEntity itemEntity = player.drop(itemStack, false);
            if (itemEntity != null) {
                itemEntity.makeFakeItem();
            }

            player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.2F,
                    ((player.getRandom().nextFloat() - player.getRandom().nextFloat()) * 0.7F + 1.0F) * 2.0F);
            player.containerMenu.broadcastChanges();
        } else {
            ItemEntity itemEntity = player.drop(itemStack, false);
            if (itemEntity != null) {
                itemEntity.setNoPickUpDelay();
                itemEntity.setTarget(player.getUUID());
            }
        }
    }

}
