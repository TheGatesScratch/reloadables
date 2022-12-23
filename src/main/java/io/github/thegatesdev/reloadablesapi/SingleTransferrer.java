package io.github.thegatesdev.reloadablesapi;

import javax.annotation.Nullable;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class SingleTransferrer<T, D> implements Transferrer<T> {

    private final BiConsumer<T, D> setter;
    private final Consumer<T> remover;
    private final BiConsumer<T, D> action;
    private final Function<T, D> getter;

    public SingleTransferrer(Function<T, D> getter, BiConsumer<T, D> setter, Consumer<T> remover) {
        this(getter, setter, remover, null);
    }

    public SingleTransferrer(Function<T, D> getter, BiConsumer<T, D> setter, Consumer<T> remover, BiConsumer<T, D> action) {
        this.setter = setter;
        this.remover = remover;
        this.action = action;
        this.getter = getter;
    }

    @Nullable
    public D get(T t) {
        if (t == null) return null;
        return getter.apply(t);
    }

    public T apply(T onto, D data) {
        set(onto, data);
        if (action != null)
            action.accept(onto, data);
        return onto;
    }

    void set(T t, D data) {
        setter.accept(t, data);
    }

    public T transfer(T from, T to) {
        final D data = getter.apply(from);
        if (data != null) apply(to, data);
        return to;
    }

    public T remove(T from) {
        remover.accept(from);
        return from;
    }
}