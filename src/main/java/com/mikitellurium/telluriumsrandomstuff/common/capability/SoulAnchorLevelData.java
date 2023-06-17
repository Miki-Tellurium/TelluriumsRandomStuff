package com.mikitellurium.telluriumsrandomstuff.common.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SoulAnchorLevelData extends SavedData {

    private final List<UUID> players = new ArrayList<>();

    public SoulAnchorLevelData() {
    }

    public SoulAnchorLevelData(CompoundTag tag) {
        ListTag list = tag.getList("players", Tag.TAG_COMPOUND);
        for (Tag t : list) {
            CompoundTag tag1 = (CompoundTag) t;
            UUID uuid = tag1.getUUID("uuid");
            players.add(uuid);
        }
    }

    public void addPlayer(UUID player) {
        players.add(player);
        setDirty();
    }

    public boolean removePlayer(Player player) {
        if (players.removeIf(uuid -> uuid.equals(player.getUUID()))) {
            setDirty();
            return true;
        }

        return false;
    }

    @Nonnull
    public static SoulAnchorLevelData get(Level level) {
        if (level.isClientSide) {
            throw new RuntimeException("World data can't be client-side");
        }
        DimensionDataStorage storage = level.getServer().overworld().getDataStorage();
        return storage.computeIfAbsent(SoulAnchorLevelData::new, SoulAnchorLevelData::new, "soulanchordata");
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        ListTag list = new ListTag();
        for (UUID uuid : players) {
            CompoundTag s = new CompoundTag();
            s.putUUID("uuid", uuid);
            list.add(s);
        }
        tag.put("players", list);
        return tag;
    }

}
