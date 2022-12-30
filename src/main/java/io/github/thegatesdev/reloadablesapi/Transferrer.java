package io.github.thegatesdev.reloadablesapi;

public interface Transferrer<T> {
    <E extends T> E transfer(T from, E to);

    default <E extends T> E transfer(Iterable<T> from, E to) {
        for (final T item : from) transfer(item, to);
        return to;
    }

    <E extends T> E remove(E from);
}
