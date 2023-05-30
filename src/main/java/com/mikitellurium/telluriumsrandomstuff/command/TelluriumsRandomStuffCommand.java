package com.mikitellurium.telluriumsrandomstuff.command;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.capability.SoulAnchorCapability;
import com.mikitellurium.telluriumsrandomstuff.capability.SoulAnchorCapabilityProvider;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.Collection;

public class TelluriumsRandomStuffCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal(TelluriumsRandomStuffMod.MOD_ID)
                .requires((commandSourceStack) -> commandSourceStack.hasPermission(3))
                .then(Commands.literal("soulAnchor")
                        .then(Commands.literal("clearSavedInventory")
                                .then(Commands.argument("targets", EntityArgument.players())
                                        .executes((context) -> clearSavedInventory(context.getSource(),
                                                EntityArgument.getOptionalPlayers(context, "targets"))))
                        ).then(Commands.literal("canRecoverInventory")
                                .then(Commands.argument("targets", EntityArgument.players())
                                        .then(Commands.literal("true")
                                                .executes((context -> canRecoverInventory(context.getSource(),
                                                        EntityArgument.getOptionalPlayers(context, "targets"),
                                                        true))))
                                        .then(Commands.literal("false")
                                                .executes((context -> canRecoverInventory(context.getSource(),
                                                        EntityArgument.getOptionalPlayers(context, "targets"),
                                                        false)))))
                        ).then(Commands.literal("hasChargedAnchor")
                                .then(Commands.argument("targets", EntityArgument.players())
                                        .then(Commands.literal("true")
                                                .executes((context -> hasChargedAnchor(context.getSource(),
                                                        EntityArgument.getOptionalPlayers(context, "targets"),
                                                        true))))
                                        .then(Commands.literal("false")
                                                .executes((context -> hasChargedAnchor(context.getSource(),
                                                        EntityArgument.getOptionalPlayers(context, "targets"),
                                                        false))))))
                ));
    }

    private static int clearSavedInventory(CommandSourceStack source, Collection<ServerPlayer> players) {
        if (players.isEmpty()) {
            source.sendFailure(Component.literal("Target not found"));
            return 0;
        }

        for (ServerPlayer player : players) {
            player.getCapability(SoulAnchorCapabilityProvider.SOUL_ANCHOR_CAPABILITY)
                    .ifPresent((SoulAnchorCapability::clearInventory));
        }
        source.sendSuccess(Component.literal("Cleared saved inventory for " + players.iterator().next().getScoreboardName()),
                true);
        return 1;
    }

    private static int hasChargedAnchor(CommandSourceStack source, Collection<ServerPlayer> players, boolean b) {
        if (players.isEmpty()) {
            source.sendFailure(Component.literal("Target not found"));
            return 0;
        }

        for (ServerPlayer player : players) {
            player.getCapability(SoulAnchorCapabilityProvider.SOUL_ANCHOR_CAPABILITY)
                    .ifPresent((soulAnchorCapability -> soulAnchorCapability.setChargedAnchor(b)));
        }
        source.sendSuccess(Component.literal("Set hasChargedAnchor to " + b + " for " +
                players.iterator().next().getScoreboardName()), true);
        return 1;
    }

    private static int canRecoverInventory(CommandSourceStack source, Collection<ServerPlayer> players, boolean b) {
        if (players.isEmpty()) {
            source.sendFailure(Component.literal("Target not found"));
            return 0;
        }

        for (ServerPlayer player : players) {
            player.getCapability(SoulAnchorCapabilityProvider.SOUL_ANCHOR_CAPABILITY)
                    .ifPresent((soulAnchorCapability -> soulAnchorCapability.setCanRecoverInventory(b)));
        }
        source.sendSuccess(Component.literal("Set canRecoverInventory to " + b + " for " +
                players.iterator().next().getScoreboardName()), true);
        return 1;
    }

}
