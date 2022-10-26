package com.thegates.reloadablesapi;

import com.thegates.gatebase.util.ItemUtil;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ReloadablesAPI extends JavaPlugin {

    public static <D> Transferrer<ItemStack, D> persistentDataTransferrer(NamespacedKey key, PersistentDataType<?, D> dataType, BiConsumer<ItemMeta, D> action) {
        return metaTransferrer(new Transferrer<>(m -> m.getPersistentDataContainer().get(key, dataType), (m, d) -> m.getPersistentDataContainer().set(key, dataType, d), m -> m.getPersistentDataContainer().remove(key), action));
    }

    public static <D> Transferrer<ItemStack, D> persistentDataTransferrer(NamespacedKey key, PersistentDataType<?, D> dataType) {
        return persistentDataTransferrer(key, dataType, null);
    }

    public static <D> Transferrer<ItemStack, D> metaTransferrer(Transferrer<ItemMeta, D> transferrer) {
        return new Transferrer<>(i -> ItemUtil.withMeta(i, transferrer::get), (i, d) -> ItemUtil.withMeta(i, (Consumer<ItemMeta>) m -> transferrer.set(m, d)), i -> ItemUtil.withMeta(i, transferrer::remove), (i, d) -> ItemUtil.withMeta(i, (Consumer<ItemMeta>) m -> transferrer.apply(m, d)));
    }
}