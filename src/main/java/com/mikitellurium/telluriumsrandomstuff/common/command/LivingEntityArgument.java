package com.mikitellurium.telluriumsrandomstuff.common.command;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class LivingEntityArgument implements ArgumentType<String> {
    private static final Collection<String> EXAMPLES = Arrays.asList("minecraft:creeper", "minecraft:pig");
    private static final DynamicCommandExceptionType ERROR_INVALID = new DynamicCommandExceptionType((obj) ->
            Component.translatable("argument.entity.options.type.invalid", obj));
    private static final List<String> SUGGESTIONS = ForgeRegistries.ENTITY_TYPES.getValues().stream()
            .filter(DefaultAttributes::hasSupplier)
            .filter((entityType) -> !(entityType.equals(EntityType.PLAYER) || entityType.equals(EntityType.ARMOR_STAND)))
            .map((entityType) -> EntityType.getKey(entityType).toString())
            .map(StringArgumentType::escapeIfRequired)
            .collect(Collectors.toList());

    public static LivingEntityArgument livingEntity() {
        return new LivingEntityArgument();
    }

    private LivingEntityArgument() {}

    @Override
    public String parse(StringReader reader) throws CommandSyntaxException {
        String s = reader.readQuotedString();
        ResourceLocation id = ResourceLocation.tryParse(s);
        if (ForgeRegistries.ENTITY_TYPES.containsKey(id) && SUGGESTIONS.contains(StringArgumentType.escapeIfRequired(id.toString()))) {
            return s;
        } else {
            throw ERROR_INVALID.createWithContext(reader, s);
        }
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        return context.getSource() instanceof SharedSuggestionProvider ?
                SharedSuggestionProvider.suggest(SUGGESTIONS, builder) : Suggestions.empty();
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    public static String getEntityId(CommandContext<CommandSourceStack> context, String argument) {
        return context.getArgument(argument, String.class);
    }

}
