package me.gamercoder215.starcosmetics.wrapper.nbt;

import org.bukkit.inventory.ItemStack;

import me.gamercoder215.starcosmetics.api.cosmetics.registry.CosmeticLocation;

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

    public final void setID(String value) {
        setNBT("id", value);
    }

    public final String getID() {
        return getNBTString("id");
    }

    public final boolean hasID() { return getID() != null && !getID().isEmpty(); }

    public abstract String getNBTString(String key);

    public abstract void setNBT(String key, String value);

    public abstract boolean getNBTBoolean(String key);

    public abstract void setNBT(String key, boolean value);

    public abstract int getNBTInt(String key);

    public abstract void setNBT(String key, int value);

    public abstract double getNBTDouble(String key);

    public abstract void setNBT(String key, double value);

    public abstract UUID getNBTUUID(String key);

    public abstract void setNBT(String key, UUID value);

    public abstract Class<?> getNBTClass(String key);

    public abstract void setNBT(String key, Class<?> clazz);

    public <T> Class<T> getNBTClass(String key, Class<T> cast) {
        Class<?> clazz = getNBTClass(key);
        return cast.isAssignableFrom(clazz) ? cast : null;
    }

    public final CosmeticLocation<?> getNBTLocation(String key) {
        return CosmeticLocation.getByFullKey(getNBTString(key));
    }

    public final void setNBT(String key, CosmeticLocation<?> value) {
        setNBT(key, value.getFullKey());
    }
}
