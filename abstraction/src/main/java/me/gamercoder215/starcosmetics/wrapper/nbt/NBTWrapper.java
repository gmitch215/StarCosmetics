package me.gamercoder215.starcosmetics.wrapper.nbt;

import me.gamercoder215.starcosmetics.api.cosmetics.registry.CosmeticLocation;
import me.gamercoder215.starcosmetics.util.Constants;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public abstract class NBTWrapper {

    static final String ROOT = "StarCosmetics";

    ItemStack item;

    NBTWrapper(ItemStack item) {
        this.item = item;
    }
    
    public ItemStack getItem() {
        return item;
    }

    public static NBTWrapper of(ItemStack item) {
        return Constants.w.getNBTWrapper(item);
    }

    public final void setID(String value) {
        set("id", value);
    }

    public final String getID() {
        return getString("id");
    }

    public final boolean hasID() { return getID() != null && !getID().isEmpty(); }

    public abstract String getString(String key);

    public abstract void set(String key, String value);

    public abstract boolean getBoolean(String key);

    public abstract void set(String key, boolean value);

    public abstract int getInt(String key);

    public abstract void set(String key, int value);

    public abstract double getDouble(String key);

    public abstract void set(String key, double value);

    public abstract UUID getUUID(String key);

    public abstract void set(String key, UUID value);

    public abstract Class<?> getClass(String key);

    public abstract void set(String key, Class<?> clazz);

    public <T> Class<? extends T> getClass(String key, Class<T> parent) {
        Class<?> clazz = getClass(key);
        return parent.isAssignableFrom(clazz) ? clazz.asSubclass(parent) : null;
    }

    public final CosmeticLocation<?> getCosmeticLocation(String key) {
        return CosmeticLocation.getByFullKey(getString(key));
    }

    public final void set(String key, CosmeticLocation<?> value) {
        set(key, value.getFullKey());
    }
}
