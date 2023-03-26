package me.gamercoder215.starcosmetics.util.inventory;

import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

import static me.gamercoder215.starcosmetics.util.Constants.w;

public interface StarInventory extends Inventory, Cloneable {

    @Nullable
    String getKey();

    Map<String, Object> getAllAttributes();

    void setAttribute(String key, Object value);

    default void setAttributes(@NotNull Map<String, Object> map) {
        map.forEach(this::setAttribute);
    }

    default StarInventory copy() {
        StarInventory inv = w.createInventory(getKey(), getSize(), getAttribute("_name", String.class));
        inv.setContents(getContents());
        inv.setAttributes(getAllAttributes());
        return inv;
    }

    void removeAttribute(String key);

    default Object getAttribute(String key) {
        return getAllAttributes().get(key);
    }

    default boolean hasAttribute(String key) {
        return getAttribute(key) != null;
    }

    default boolean isCancelled() {
        return getAttribute("cancel", Boolean.class);
    }

    default void setCancelled(boolean cancel) {
        setAttribute("cancel", cancel);
    }

    default void setCancelled() {
        setCancelled(true);
    }

    default <T> T getAttribute(String key, Class<T> cast) {
        Object o = getAttribute(key);
        if (o == null) return null;
        return cast.cast(o);
    }

    default Object getAttribute(String key, Object def) {
        Object o = getAttribute(key);
        return o == null ? def : o;
    }

    default <T> T getAttribute(String key, T def, Class<T> cast) {
        return cast.cast(getAttribute(key, def));
    }

}
