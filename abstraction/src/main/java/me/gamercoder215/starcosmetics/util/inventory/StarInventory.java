package me.gamercoder215.starcosmetics.util.inventory;

import java.util.Map;

import org.bukkit.inventory.Inventory;

public interface StarInventory extends Inventory {
    
    String getKey();

    Map<String, Object> getAllAttributes();

    void setAttribute(String key, Object value);

    void removeAttribute(String key);

    default Object getAttribute(String key) {
        return getAllAttributes().get(key);
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

    default <T> T getAttribute(String key, Object def, Class<T> cast) {
        return cast.cast(getAttribute(key, def));
    }

}
