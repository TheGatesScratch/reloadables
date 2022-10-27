package com.thegates.reloadablesapi;

import com.thegates.gatebase.GateBase;

import java.util.Collection;
import java.util.LinkedList;

public class TransferCollection<T> {
    private final Collection<Transferrer<T, ?>> transferrers = new LinkedList<>();

    public TransferCollection() {
    }

    public TransferCollection(TransferCollection<T> other) {
        this();
        transferrers.addAll(other.transferrers);
    }

    public <D> Transferrer<T, D> add(Transferrer<T, D> transferrer) {
        transferrers.add(transferrer);
        return transferrer;
    }

    public void transfer(T from, T to) {
        transferrers.forEach(t -> t.transfer(from, to));
    }

    public void hasAll(T toCheck) {
        GateBase.forEachAND(transferrers, t -> t.has(toCheck));
    }
}
