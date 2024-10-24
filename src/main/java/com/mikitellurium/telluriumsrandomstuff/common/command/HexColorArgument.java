package com.mikitellurium.telluriumsrandomstuff.common.command;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

import java.util.Arrays;
import java.util.Collection;

public class HexColorArgument implements ArgumentType<Integer> {

    private static final Collection<String> EXAMPLES = Arrays.asList("#FFFFFF", "#FF00FF");
    private static final SimpleCommandExceptionType ERROR_INVALID = new SimpleCommandExceptionType(Component.translatable("command.telluriumsrandomstuff.item.lava_googles.invalid_color"));

    public static HexColorArgument hexColor() {
        return new HexColorArgument();
    }

    private HexColorArgument() {
    }

    @Override
    public Integer parse(StringReader reader) throws CommandSyntaxException {
        reader.expect('#');
        String s = reader.readUnquotedString();
        if (s == null || s.length() != 6) {
            throw ERROR_INVALID.createWithContext(reader);
        } else {
            try {
                return Integer.parseInt(s, 16);
            } catch (NumberFormatException e) {
                throw ERROR_INVALID.createWithContext(reader);
            }
        }
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    public static int getParsedColor(CommandContext<CommandSourceStack> context, String argument) {
        return context.getArgument(argument, Integer.class);
    }

}
