package com.thegates.reloadablesapi;

public interface Transferrer<T, D> {
    void transfer(T from, T to);
}
