package com.mikitellurium.telluriumsrandomstuff.common.networking;

import com.mikitellurium.telluriumsrandomstuff.common.entity.GrapplingHookEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class GrapplingHookManager extends SavedData {

    private final Map<Player, GrapplingHookEntity> savedGrapplingHooks = new HashMap<>();

    public GrapplingHookManager() {
    }

    public GrapplingHookManager(CompoundTag tag, ServerLevel level) {
        ListTag list = tag.getList("saved_data", Tag.TAG_COMPOUND);
        for (Tag t : list) {
            CompoundTag entry = (CompoundTag) t;
            Player player = level.getPlayerByUUID(entry.getUUID("player"));
            Entity entity = level.getEntity(entry.getUUID("grappling_hook"));
            if (player != null && entity instanceof GrapplingHookEntity hook) {
                insertHook(player, hook);
            }
        }
    }

    public void insertHook(Player player, GrapplingHookEntity hook) {
        savedGrapplingHooks.put(player, hook);
        setDirty();
    }

    public void removeHook(Player player) {
        savedGrapplingHooks.remove(player);
        setDirty();
    }

    public boolean isHookPresent(Player player) {
        return getHook(player) != null;
    }

    public void ifHookPresent(Player player, Consumer<GrapplingHookEntity> consumer) {
        if (isHookPresent(player)) {
            consumer.accept(getHook(player));
        }
    }

    public GrapplingHookEntity getHook(Player player) {
        return savedGrapplingHooks.get(player);
    }

    @Nonnull
    public static GrapplingHookManager get(Level level) {
        if (level.isClientSide) {
            throw new RuntimeException("World data can't be client-side");
        }
        DimensionDataStorage storage = level.getServer().overworld().getDataStorage();
        return storage.computeIfAbsent((tag) -> new GrapplingHookManager(tag, (ServerLevel) level),
                GrapplingHookManager::new, "grappling_hook_manager");
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        ListTag list = new ListTag();
        for (Map.Entry<Player, GrapplingHookEntity> entry : savedGrapplingHooks.entrySet()) {
            CompoundTag entryTag = new CompoundTag();
            entryTag.putUUID("player", entry.getKey().getUUID());
            entryTag.putUUID("grappling_hook", entry.getValue().getUUID());
            list.add(entryTag);
        }
        tag.put("saved_data", list);
        return tag;
    }

}
