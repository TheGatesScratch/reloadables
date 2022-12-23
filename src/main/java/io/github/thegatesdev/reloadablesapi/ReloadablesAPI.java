package io.github.thegatesdev.reloadablesapi;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.function.BiConsumer;

public class ReloadablesAPI {

    public static <D> SingleTransferrer<ItemMeta, D> persistentDataTransferrer(NamespacedKey key, PersistentDataType<?, D> dataType, BiConsumer<ItemMeta, D> action) {
        return new SingleTransferrer<>(m -> {
            final var c = m.getPersistentDataContainer();
            if (c.isEmpty()) return null;
            return c.get(key, dataType);
        }, (m, d) -> m.getPersistentDataContainer().set(key, dataType, d), m -> m.getPersistentDataContainer().remove(key), action);
    }

    public static <D> SingleTransferrer<ItemMeta, D> persistentDataTransferrer(NamespacedKey key, PersistentDataType<?, D> dataType) {
        return persistentDataTransferrer(key, dataType, null);
    }
}