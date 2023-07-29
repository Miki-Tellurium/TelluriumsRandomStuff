package com.mikitellurium.telluriumsrandomstuff.common.command;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.common.content.item.LavaGooglesItem;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

public class LavaGooglesCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal(TelluriumsRandomStuffMod.MOD_ID)
                .requires((commandSourceStack) -> commandSourceStack.hasPermission(3))
                .then(Commands.literal("getLavaGoogles")
                        .executes((context) -> {
                            addStackToPlayerInventory(
                                    context.getSource().getPlayerOrException(), new ItemStack(ModItems.LAVA_GOOGLES.get()));
                                return 0;
                        })
                        .then(Commands.argument("color", StringArgumentType.word())
                        .executes((context -> giveLavaGoogles(context.getSource(),
                                StringArgumentType.getString(context, "color"))
                        )))));
    }

    private static int giveLavaGoogles(CommandSourceStack source, String color) {
        ServerPlayer player = source.getPlayer();
        ItemStack googles = new ItemStack(ModItems.LAVA_GOOGLES.get());

        try {
            int colorId = Integer.parseInt(color);
            if (colorId >= 0 && colorId <= 15) {
                LavaGooglesItem.setColor(googles, DyeColor.byId(colorId));
            }
            addStackToPlayerInventory(player, googles);
            return 1;
        } catch (NumberFormatException e) {
            DyeColor dyeColor = DyeColor.byName(color, null);
            if (dyeColor != null) {
                LavaGooglesItem.setColor(googles, dyeColor);
            }
            addStackToPlayerInventory(player, googles);
            return 1;
        }
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
        }
    }

}
