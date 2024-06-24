package com.mikitellurium.telluriumsrandomstuff.lib;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mikitellurium.telluriumsrandomstuff.api.potionmixing.CustomRegistries;
import com.mikitellurium.telluriumsrandomstuff.api.potionmixing.PotionMixingFunction;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.ForgeRegistries;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public abstract class PotionMixingFunctionProvider implements DataProvider {

    private final PackOutput output;
    private final String modid;
    private final Map<MobEffect, PotionMixingFunction> toSerialize = new HashMap<>();
    private boolean replace = false;

    public PotionMixingFunctionProvider(PackOutput output, String modid) {
        this.output = output;
        this.modid = modid;
    }

    protected void replace() {
        this.replace = true;
    }

    public void addFunction(MobEffect mobEffect, PotionMixingFunction function) {
        this.toSerialize.put(mobEffect, function);
    }

    protected abstract void start();

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        this.start();

        Path dataPath = this.output.getOutputFolder(PackOutput.Target.DATA_PACK)
                .resolve(this.modid).resolve("potionmixing").resolve("mixing_functions.json");

        JsonArray array = new JsonArray();
        this.toSerialize.forEach(((effect, function) -> {
            ResourceLocation effectLoc = ForgeRegistries.MOB_EFFECTS.getKey(effect);
            ResourceLocation functionLoc = CustomRegistries.POTION_MIXING_FUNCTIONS.get().getKey(function);

            JsonObject entry = new JsonObject();
            entry.addProperty("mob_effect", effectLoc.toString());
            entry.addProperty("function", functionLoc.toString());

            array.add(entry);
        }));

        JsonObject json = new JsonObject();
        json.addProperty("replace", this.replace);
        json.add("values", array);

        ImmutableList.Builder<CompletableFuture<?>> futuresBuilder = new ImmutableList.Builder<>();
        futuresBuilder.add(DataProvider.saveStable(cache, json, dataPath));

        return CompletableFuture.allOf(futuresBuilder.build().toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Potion Mixing Functions: " + this.modid;
    }

}
