package me.gamercoder215.starcosmetics.wrapper.nbt;

import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.api.cosmetics.CosmeticLocation;
import me.gamercoder215.starcosmetics.api.cosmetics.structure.StructureInfo;
import me.gamercoder215.starcosmetics.api.player.SoundEventSelection;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.function.Consumer;

import static me.gamercoder215.starcosmetics.util.Constants.w;

public abstract class NBTWrapper {

    static final String ROOT = "StarCosmetics";

    ItemStack item;

    NBTWrapper(ItemStack item) {
        this.item = item;
    }
    
    public ItemStack getItem() {
        return item;
    }

    public static ItemStack builder(ItemStack item, Consumer<NBTWrapper> nbtConsumer) {
        NBTWrapper nbt = of(item);
        nbtConsumer.accept(nbt);
        return nbt.getItem();
    }

    public static NBTWrapper of(ItemStack item) {
        return w.getNBTWrapper(item);
    }

    public static float getFloat(ItemStack item, String key) {
        return of(item).getFloat(key);
    }

    public static ItemStack setID(ItemStack item, String id) {
        NBTWrapper nbt = of(item);
        nbt.setID(id);
        return nbt.getItem();
    }

    public final void setID(String value) {
        set("id", value);
    }

    public final String getID() {
        return getString("id");
    }

    public final boolean hasID() { return getID() != null && !getID().isEmpty(); }

    public final boolean hasString(String key) { return getString(key) != null && !getString(key).isEmpty(); }

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

    public abstract void set(String key, SoundEventSelection value);

    public abstract SoundEventSelection getSoundEventSelection(String key);

    public abstract float getFloat(String key);

    public abstract void set(String key, float value);

    @Nullable
    public final <T> Class<? extends T> getClass(String key, Class<T> parent) {
        Class<?> clazz = getClass(key);
        return parent.isAssignableFrom(clazz) ? clazz.asSubclass(parent) : null;
    }

    public final CosmeticLocation<?> getCosmeticLocation(String key) {
        return CosmeticLocation.getByFullKey(getString(key));
    }

    public final void set(String key, @NotNull CosmeticLocation<?> value) {
        set(key, value.getFullKey());
    }

    public final StructureInfo getStructureInfo(String key) {
        return StarConfig.getRegistry().getAvailableStructures()
                .stream()
                .filter(info -> info.getUniqueId().equals(getUUID(key)))
                .findFirst()
                .orElse(null);
    }

    public final void set(String key, @NotNull StructureInfo value) {
        set(key, value.getUniqueId());
    }
}
