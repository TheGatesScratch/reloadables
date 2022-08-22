package com.thegates.reloadablesapi;

import com.thegates.gatebase.util.ItemUtil;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class ReloadablesAPI extends JavaPlugin {

    private final Map<Class<?>, TransferCollection<?>> transferrers = new HashMap<>(0, 0.9f);

    public static <D> Transferrer<ItemStack, D> persistentDataTransferrer(NamespacedKey key, PersistentDataType<?, D> dataType, BiConsumer<ItemMeta, D> action) {
        return metaTransferrer(itemMeta -> itemMeta.getPersistentDataContainer().get(key, dataType), (m, d) -> m.getPersistentDataContainer().set(key, dataType, d), m -> m.getPersistentDataContainer().remove(key), action);
    }

    public static <D> Transferrer<ItemStack, D> metaTransferrer(Function<ItemMeta, D> getter, BiConsumer<ItemMeta, D> setter, Consumer<ItemMeta> remover, BiConsumer<ItemMeta, D> action) {
        return new Transferrer<>(i -> ItemUtil.withMeta(i, getter), (i, d) -> ItemUtil.withMeta(i, (Consumer<ItemMeta>) m -> setter.accept(m, d)), i -> ItemUtil.withMeta(i, remover), (d, i) -> ItemUtil.withMeta(i, (Consumer<ItemMeta>) m -> action.accept(m, d)));
    }

    public <T> TransferCollection<T> getGlobalTransferrers(Class<T> type) {
        return (TransferCollection<T>) transferrers.get(type);
    }

    public <T, D> Transferrer<T, D> addGlobalTransferrer(Class<T> type, Transferrer<T, D> transferrer) {
        return ((TransferCollection<T>) transferrers.computeIfAbsent(type, c -> new TransferCollection<>())).add(transferrer);
    }
}