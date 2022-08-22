package com.thegates.reloadablesapi;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class Transferrer<T, D> {

    private final BiConsumer<T, D> setter;
    private final Consumer<T> remover;
    private final BiConsumer<D, T> action;
    private final Function<T, D> getter;

    public Transferrer(Function<T, D> getter, BiConsumer<T, D> setter, Consumer<T> remover) {
        this(getter, setter, remover, null);
    }

    public Transferrer(Function<T, D> getter, BiConsumer<T, D> setter, Consumer<T> remover, BiConsumer<D, T> action) {
        this.setter = setter;
        this.remover = remover;
        this.action = action;
        this.getter = getter;
    }

    public void apply(T onto, D data) {
        set(onto, data);
        if (action != null)
            action.accept(data, onto);
    }

    public void remove(T from) {
        remover.accept(from);
    }

    public void set(T t, D data) {
        setter.accept(t, data);
    }

    public void transfer(T from, T to) {
        final D data = getter.apply(from);
        if (data == null) return;
        apply(to, data);
    }

    public boolean has(T t) {
        return getter.apply(t) != null;
    }
}