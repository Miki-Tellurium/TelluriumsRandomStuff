package com.mikitellurium.telluriumsrandomstuff.util;

import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class CachedPlayer {

    private UUID player;

    public CachedPlayer() {}

    public CachedPlayer(UUID player) {
        this.player = player;
    }

    public UUID get() {
        return player;
    }

    public void set(UUID player) {
        this.player = player;
    }

    public void clear() {
        this.player = null;
    }

}
