package com.thegates.reloadablesapi;

import java.util.Collection;
import java.util.LinkedList;

public class TransferCollection<T, D> implements Transferrer<T, D> {
    private final Collection<SingleTransferrer<T, D>> singleTransferrers = new LinkedList<>();

    public TransferCollection() {
    }

    public TransferCollection(TransferCollection<T, D> other) {
        this();
        singleTransferrers.addAll(other.singleTransferrers);
    }

    public SingleTransferrer<T, D> add(SingleTransferrer<T, D> transferrer) {
        singleTransferrers.add(transferrer);
        return transferrer;
    }

    @Override
    public void transfer(T from, T to) {
        singleTransferrers.forEach(t -> t.transfer(from, to));
    }

    public void removeAll(T from) {
        singleTransferrers.forEach(t -> t.remove(from));
    }
}
