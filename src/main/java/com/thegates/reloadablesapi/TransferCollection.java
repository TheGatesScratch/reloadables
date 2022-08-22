package com.thegates.reloadablesapi;

import java.util.ArrayList;
import java.util.Collection;

public class TransferCollection<T> {
    private final Collection<Transferrer<T, ?>> transferrers = new ArrayList<>();

    public <D> Transferrer<T, D> add(Transferrer<T, D> transferrer) {
        transferrers.add(transferrer);
        return transferrer;
    }

    public void transfer(T from, T to) {
        transferrers.forEach(t -> t.transfer(from, to));
    }
}
