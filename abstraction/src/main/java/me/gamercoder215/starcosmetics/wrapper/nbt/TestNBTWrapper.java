package me.gamercoder215.starcosmetics.wrapper.nbt;

import me.gamercoder215.starcosmetics.api.player.SoundEventSelection;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TestNBTWrapper extends NBTWrapper {

    private final StarItemStack item;

    public TestNBTWrapper(ItemStack item) {
        super(item);
        this.item = new StarItemStack(item);
    }

    @Override
    public StarItemStack getItem() {
        return item;
    }

    public static final class StarItemStack extends ItemStack {
        Map<String, Object> nbt = new HashMap<>();

        public StarItemStack(Material type) {
            super(type);
        }

        public StarItemStack(Material type, int amount) {
            super(type, amount);
        }

        public StarItemStack(Material type, int amount, short damage) {
            super(type, amount, damage);
        }

        public StarItemStack(ItemStack item) {
            super(item);
        }

        public Map<String, Object> getNBT() {
            return nbt;
        }

        public void setNBT(Map<String, Object> nbt) {
            this.nbt = nbt;
        }

        public void setNBT(String key, Object value) {
            nbt.put(key, value);
        }

        public Object getNBT(String key) {
            return nbt.get(key);
        }
    }

    @Override
    public String getString(String key) {
        return item.getNBT(key).toString();
    }

    @Override
    public void set(String key, String value) {
        item.setNBT(key, value);
    }

    @Override
    public boolean getBoolean(String key) {
        return (boolean) item.getNBT(key);
    }

    @Override
    public void set(String key, boolean value) {
        item.setNBT(key, value);
    }

    @Override
    public int getInt(String key) {
        return (int) item.getNBT(key);
    }

    @Override
    public void set(String key, int value) {
        item.setNBT(key, value);
    }

    @Override
    public double getDouble(String key) {
        return (double) item.getNBT(key);
    }

    @Override
    public void set(String key, double value) {
        item.setNBT(key, value);
    }

    @Override
    public UUID getUUID(String key) {
        return UUID.fromString(getString(key));
    }

    @Override
    public void set(String key, UUID value) {
        set(key, value.toString());
    }

    @Override
    public Class<?> getClass(String key) {
        return (Class<?>) item.getNBT(key);
    }

    @Override
    public void set(String key, Class<?> clazz) {
        item.setNBT(key, clazz);
    }

    @Override
    public void set(String key, SoundEventSelection value) {
        item.setNBT(key, value);
    }

    @Override
    public SoundEventSelection getSoundEventSelection(String key) {
        return (SoundEventSelection) item.getNBT(key);
    }

    @Override
    public float getFloat(String key) {
        return (float) item.getNBT(key);
    }

    @Override
    public void set(String key, float value) {
        item.setNBT(key, value);
    }
}
