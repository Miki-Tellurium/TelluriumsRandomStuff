package com.mikitellurium.telluriumsrandomstuff.api.potionmixing;

import com.google.common.collect.Maps;
import com.google.gson.*;
import com.mikitellurium.telluriumsrandomstuff.api.CustomRegistries;
import com.mikitellurium.telluriumsrandomstuff.registry.ModPotionMixingFunctions;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PotionMixingManager extends SimpleJsonResourceReloadListener {

    private static final String directory = "potionmixing";
    private static final Function<ResourceLocation, String> ERROR_STRING =
            (loc) -> "Error in '" + loc.getNamespace() + "/" + directory + "/" + loc.getPath() + "': ";
    private static final PotionMixingManager INSTANCE = new PotionMixingManager();

    private final Map<MobEffect, PotionMixingFunction> mixingFunctions = Maps.newHashMap();

    private PotionMixingManager() {
        super(new GsonBuilder().create(), directory);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> elements, ResourceManager manager, ProfilerFiller profilerFiller) {
        if (elements.isEmpty()) return;
        this.mixingFunctions.clear();
        Map<ResourceLocation, JsonElement> validEntries = elements.entrySet().stream()
                .filter(entry -> entry.getKey().getPath().equals("mixing_functions"))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        // Ensure the default data pack is loaded first
        Map.Entry<ResourceLocation, JsonElement> defaultElement = validEntries.entrySet().stream()
                .filter((entry) -> entry.getKey().getNamespace().equals(FastLoc.modId()))
                .findFirst().orElseThrow(() -> new NoSuchElementException("Default data pack missing")); // This should never throw
        this.populateFunctions(defaultElement.getKey(), defaultElement.getValue());
        validEntries.remove(defaultElement.getKey());

        for (Map.Entry<ResourceLocation, JsonElement> entry : validEntries.entrySet()) {
            ResourceLocation location = entry.getKey();
            JsonElement element = entry.getValue();
            this.populateFunctions(location, element);
        }
    }

    private void populateFunctions(ResourceLocation location, JsonElement element) {
        if (element.isJsonObject()) {
            JsonObject object = element.getAsJsonObject();
            boolean replace = GsonHelper.getAsBoolean(object, "replace");
            List<JsonObject> objects = this.parseArray(GsonHelper.getAsJsonArray(object, "values"));
            for (JsonObject obj : objects) {
                if (obj.has("mob_effect") && obj.has("function")) {
                    String effectString = GsonHelper.getAsString(obj, "mob_effect");
                    String functionString = GsonHelper.getAsString(obj, "function");
                    ResourceLocation effectLoc = ResourceLocation.tryParse(effectString);
                    ResourceLocation functionLoc = ResourceLocation.tryParse(functionString);
                    Optional<MobEffect> effect = Optional.ofNullable(ForgeRegistries.MOB_EFFECTS.getValue(effectLoc));
                    Optional<PotionMixingFunction> function = Optional.ofNullable(CustomRegistries.POTION_MIXING_FUNCTIONS.get().getValue(functionLoc));
                    if (effect.isPresent() && function.isPresent()) {
                        this.add(effect.get(), function.get(), replace);
                    } else {
                        if (effect.isEmpty()) {
                            throw new JsonSyntaxException(ERROR_STRING.apply(location) + "Unknown effect '" + effectString + "'");
                        } else {
                            throw new JsonSyntaxException(ERROR_STRING.apply(location) + "Unknown function '" + functionString + "'");
                        }
                    }
                } else {
                    if (!obj.has("mob_effect")) {
                        throw new JsonSyntaxException(ERROR_STRING.apply(location) + "Incorrect or missing 'mob_effect'");
                    } else {
                        throw new JsonSyntaxException(ERROR_STRING.apply(location) + "Incorrect or missing 'function'");
                    }
                }
            }
        }
    }

    private List<JsonObject> parseArray(JsonArray array) {
        List<JsonObject> objects = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            JsonElement element = array.get(i);
            if (element.isJsonObject()) {
                objects.add(element.getAsJsonObject());
            }
        }
        return objects;
    }

    private void add(MobEffect effect, PotionMixingFunction function, boolean replace) {
        if (replace) {
            this.mixingFunctions.put(effect, function);
        } else if (!this.mixingFunctions.containsKey(effect)){
            this.mixingFunctions.put(effect, function);
        }
    }

    private PotionMixingFunction get(MobEffect effect) {
        return this.mixingFunctions.get(effect);
    }

    public static PotionMixingFunction getFunction(MobEffect mobEffect) {
        PotionMixingFunction category = INSTANCE.get(mobEffect);
        return Objects.requireNonNullElse(category, ModPotionMixingFunctions.INCREASE_AMPLIFIER.get());
    }

    public static void registerListener(AddReloadListenerEvent event) {
        event.addListener(INSTANCE);
    }

}
