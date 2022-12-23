package io.github.thegatesdev.reloadablesapi;

public interface Transferrer<T> {
    T transfer(T from, T to);

    T remove(T from);
}
