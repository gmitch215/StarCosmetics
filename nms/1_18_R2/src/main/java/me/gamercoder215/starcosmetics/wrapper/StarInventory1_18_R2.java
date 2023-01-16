package me.gamercoder215.starcosmetics.wrapper;

import com.google.common.collect.ImmutableMap;
import me.gamercoder215.starcosmetics.util.inventory.StarInventory;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftInventoryCustom;

import java.util.HashMap;
import java.util.Map;

public final class StarInventory1_18_R2 extends CraftInventoryCustom implements StarInventory {
    
    private final String key;
    private final Map<String, Object> attributes = new HashMap<>();

    StarInventory1_18_R2(String key, int size, String title) {
        super(null, size, title);
        this.key = key;

        setAttribute("_name", title);
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public Map<String, Object> getAllAttributes() {
        return ImmutableMap.copyOf(attributes);
    }

    @Override
    public void setAttribute(String key, Object value) {
        attributes.put(key, value);
    }

    @Override
    public void removeAttribute(String key) {
        attributes.remove(key);
    }

}