package com.thegates.reloadablesapi;

import java.util.Collection;
import java.util.LinkedList;

public class TransferCollection<T, D> implements Transferrer<T, D> {
    private final Collection<Transferrer<T, D>> singleTransferrers = new LinkedList<>();

    public TransferCollection() {
    }

    public TransferCollection(TransferCollection<T, D> other) {
        this();
        singleTransferrers.addAll(other.singleTransferrers);
    }

    public Transferrer<T, D> add(Transferrer<T, D> transferrer) {
        singleTransferrers.add(transferrer);
        return transferrer;
    }

    @Override
    public T transfer(T from, T to) {
        singleTransferrers.forEach(t -> t.transfer(from, to));
        return to;
    }

    public T remove(T from) {
        singleTransferrers.forEach(t -> t.remove(from));
        return from;
    }
}
