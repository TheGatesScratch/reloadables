package com.thegates.reloadablesapi;

import java.util.Collection;
import java.util.LinkedList;

public class TransferCollection<T> implements Transferrer<T> {
    private final Collection<Transferrer<T>> singleTransferrers = new LinkedList<>();

    public TransferCollection() {
    }

    public TransferCollection(TransferCollection<T> other) {
        singleTransferrers.addAll(other.singleTransferrers);
    }

    public <D> Transferrer<T> add(Transferrer<T> transferrer) {
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
