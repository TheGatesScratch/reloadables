package com.thegates.reloadablesapi;

import com.thegates.gatebase.util.ItemUtil;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ReloadablesAPI extends JavaPlugin {

    private final Map<Class<?>, List<Transferrer<?, ?>>> transferrers = new HashMap<>(0, 0.9f);

    public static <D> Transferrer<ItemStack, D> persistentDataTransferrer(NamespacedKey key, PersistentDataType<?, D> dataType, BiConsumer<ItemMeta, D> action) {
        return metaTransferrer(itemMeta -> itemMeta.getPersistentDataContainer().get(key, dataType), (m, d) -> m.getPersistentDataContainer().set(key, dataType, d), m -> m.getPersistentDataContainer().remove(key), action);
    }

    public static <D> Transferrer<ItemStack, D> metaTransferrer(Function<ItemMeta, D> getter, BiConsumer<ItemMeta, D> setter, Consumer<ItemMeta> remover, BiConsumer<ItemMeta, D> action) {
        return new Transferrer<>(i -> ItemUtil.withMeta(i, getter), (i, d) -> ItemUtil.withMeta(i, (Consumer<ItemMeta>) m -> setter.accept(m, d)), i -> ItemUtil.withMeta(i, remover), (d, i) -> ItemUtil.withMeta(i, (Consumer<ItemMeta>) m -> action.accept(m, d)));
    }

    public <T, D> Transferrer<T, D> registerTransferrer(Class<T> type, Transferrer<T, D> transferrer) {
        transferrers.computeIfAbsent(type, c -> new ArrayList<>(1)).add(transferrer);
        return transferrer;
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public <T> List<Transferrer<T, ?>> getTransferrers(Class<T> type) throws ClassCastException {
        final List<Transferrer<?, ?>> list = transferrers.get(type);
        if (list == null) return null;
        try {
            return list.stream().map(transferrer -> (Transferrer<T, ?>) transferrer).collect(Collectors.toList());
        } catch (ClassCastException e) {
            throw new ClassCastException("Something went wrong casting data transferrers: " + e.getMessage());
        } catch (Exception e) {
            return null;
        }
    }

    public <T> void transfer(Class<T> tClass, T from, T to) {
        final List<Transferrer<T, ?>> list = getTransferrers(tClass);
        if (list != null) list.forEach(transferrer -> transferrer.transfer(from, to));
    }
}