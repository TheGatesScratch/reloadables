package io.github.thegatesdev.reloadablesapi;

public interface SingleTransferrer<T, D> extends Transferrer<T> {
    D get(T t);

    <E extends T> E apply(E onto, D data);

    @Override
    default <E extends T> E transfer(T from, E to) {
        return apply(to, get(from));
    }
}