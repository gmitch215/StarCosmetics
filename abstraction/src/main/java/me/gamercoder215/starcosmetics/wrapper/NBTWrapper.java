package me.gamercoder215.starcosmetics.wrapper;

import com.avaje.ebean.validation.NotNull;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public abstract class NBTWrapper {

    static final String ROOT = "StarCosmetics";

    ItemStack item;

    NBTWrapper(ItemStack item) {
        this.item = item;
    }

    @NotNull
    public ItemStack getItem() {
        return item;
    }

    public final void setID(String value) {
        setNBT("id", value);
    }

    public final String getID() {
        return getNBTString("id");
    }

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
}
