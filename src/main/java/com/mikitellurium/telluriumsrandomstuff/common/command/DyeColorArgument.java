package com.mikitellurium.telluriumsrandomstuff.common.command;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.DyeColor;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class DyeColorArgument implements ArgumentType<DyeColor> {
    private static final Collection<String> EXAMPLES = Arrays.asList("red", "green");
    private static final DynamicCommandExceptionType ERROR_INVALID = new DynamicCommandExceptionType((obj) ->
            Component.translatable("argument.color.invalid", obj));

    public static DyeColorArgument dyeColor() {
        return new DyeColorArgument();
    }

    private DyeColorArgument() {}

    @Override
    public DyeColor parse(StringReader reader) throws CommandSyntaxException {
        String s = reader.readUnquotedString();
        DyeColor dyeColor = DyeColor.byName(s, null);
        if (dyeColor == null) {
            throw ERROR_INVALID.createWithContext(reader, s);
        } else {
            return dyeColor;
        }
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        return context.getSource() instanceof SharedSuggestionProvider ?
                SharedSuggestionProvider.suggest(Arrays.stream(DyeColor.values()).map(DyeColor::getName), builder) : Suggestions.empty();
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    public static DyeColor getDyeColor(CommandContext<CommandSourceStack> context, String argument) {
        return context.getArgument(argument, DyeColor.class);
    }

}
