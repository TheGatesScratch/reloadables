package com.thegates.reloadablesapi;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class ReloadablesAPI extends JavaPlugin {

    private final List<Reloadable> globalReloadables = new ArrayList<>();

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

    public void addReloadable(Reloadable reloadable) {
        globalReloadables.add(reloadable);
    }

    public synchronized void reloadAll() {
        for (final Reloadable reloadable : globalReloadables) {
            reloadable.reload();
        }
    }

    @Override
    public void onEnable() {
        getServer().getConsoleSender().sendMessage("[ReloadablesAPI] Ready to reload some stuff ;D");
    }
}