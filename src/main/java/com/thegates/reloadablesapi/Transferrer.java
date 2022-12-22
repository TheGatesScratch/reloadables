package com.thegates.reloadablesapi;

public interface Transferrer<T> {
    T transfer(T from, T to);

    T remove(T from);
}
