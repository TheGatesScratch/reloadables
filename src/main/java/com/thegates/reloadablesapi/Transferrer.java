package com.thegates.reloadablesapi;

public interface Transferrer<T, D> {
    T transfer(T from, T to);

    void remove(T from);
}
