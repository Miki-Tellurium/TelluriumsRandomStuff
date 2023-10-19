package com.mikitellurium.telluriumsrandomstuff.util;

import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

public class CachedBlockEntity<T extends BlockEntity> {

    private T blockEntity;

    public static <T extends BlockEntity> CachedBlockEntity<T> of(T blockEntity) {
        return new CachedBlockEntity<>(blockEntity);
    }

    public static <T extends BlockEntity> CachedBlockEntity<T> empty() {
        return new CachedBlockEntity<>(null);
    }

    private CachedBlockEntity(@Nullable T blockEntity) {
        this.blockEntity = blockEntity;
    }

    public T get() {
        return blockEntity;
    }

    public boolean isEmpty() {
        return blockEntity == null;
    }

    public boolean isPresent() {
        return !this.isEmpty();
    }

    public void clear() {
        this.blockEntity = null;
    }

}
