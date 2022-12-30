package io.github.thegatesdev.reloadablesapi;

import java.util.ArrayList;
import java.util.Collection;

public class TransferCollection<T> implements Transferrer<T> {
    private final Collection<Transferrer<T>> transferrers = new ArrayList<>();

    public TransferCollection() {
    }

    public TransferCollection(TransferCollection<T> other) {
        transferrers.addAll(other.transferrers);
    }

    public <D> Transferrer<T> add(Transferrer<T> transferrer) {
        transferrers.add(transferrer);
        return transferrer;
    }

    @Override
    public <E extends T> E transfer(T from, E to) {
        transferrers.forEach(t -> t.transfer(from, to));
        return to;
    }

    @Override
    public <E extends T> E remove(E from) {
        transferrers.forEach(t -> t.remove(from));
        return from;
    }
}
