package com.mikitellurium.telluriumsrandomstuff.util;

import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class CachedObject<T> {

    T obj;

    private CachedObject(@Nullable T obj) {
        this.obj = obj;
    }

    public static <T> CachedObject<T> of(T obj) {
        return new CachedObject<>(obj);
    }

    public static <T> CachedObject<T> empty() {
        return new CachedObject<>(null);
    }

    public T get() {
        return obj;
    }

    public void set(T obj) {
        this.obj = obj;
    }

    public void clear() {
        this.obj = null;
    }

    public boolean isPresent() {
        return obj != null;
    }

    public boolean isEmpty() {
        return obj == null;
    }

    public void ifPresent(Consumer<T> consumer) {
        if (this.isPresent()) {
            consumer.accept(obj);
        }
    }

}
