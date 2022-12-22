package com.thegates.reloadablesapi;

public interface Transferrer<T, D> {
    T transfer(T from, T to);

    T remove(T from);
}
