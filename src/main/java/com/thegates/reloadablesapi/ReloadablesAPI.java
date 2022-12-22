package com.thegates.reloadablesapi;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.BiConsumer;

public class ReloadablesAPI extends JavaPlugin {

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

    @Override
    public void onEnable() {
        getServer().getConsoleSender().sendMessage("[ReloadablesAPI] Ready to reload some stuff ;D");
    }
}