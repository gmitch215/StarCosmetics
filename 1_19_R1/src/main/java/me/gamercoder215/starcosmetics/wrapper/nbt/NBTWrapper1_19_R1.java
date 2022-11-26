package me.gamercoder215.starcosmetics.wrapper.nbt;

import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.api.player.SoundEventSelection;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftItemStack;
import org.bukkit.event.Event;

import java.util.Date;
import java.util.UUID;

public final class NBTWrapper1_19_R1 extends NBTWrapper {

    public NBTWrapper1_19_R1(org.bukkit.inventory.ItemStack item) {
        super(item);
    }

    @Override
    public String getString(String key) {
        ItemStack nmsitem = CraftItemStack.asNMSCopy(item);
        CompoundTag tag = nmsitem.getOrCreateTag();
        CompoundTag starcosmetics = tag.getCompound(ROOT);

        return starcosmetics.getString(key);
    }

    @Override
    public void set(String key, String value) {
        ItemStack nmsitem = CraftItemStack.asNMSCopy(item);
        CompoundTag tag = nmsitem.getOrCreateTag();
        CompoundTag starcosmetics = tag.getCompound(ROOT);

        starcosmetics.putString(key, value);
        tag.put(ROOT, starcosmetics);
        nmsitem.setTag(tag);
        this.item = CraftItemStack.asBukkitCopy(nmsitem);
    }

    @Override
    public boolean getBoolean(String key) {
        ItemStack nmsitem = CraftItemStack.asNMSCopy(item);
        CompoundTag tag = nmsitem.getOrCreateTag();
        CompoundTag starcosmetics = tag.getCompound(ROOT);

        return starcosmetics.getBoolean(key);
    }

    @Override
    public void set(String key, boolean value) {
        ItemStack nmsitem = CraftItemStack.asNMSCopy(item);
        CompoundTag tag = nmsitem.getOrCreateTag();
        CompoundTag starcosmetics = tag.getCompound(ROOT);

        starcosmetics.putBoolean(key, value);
        tag.put(ROOT, starcosmetics);
        nmsitem.setTag(tag);
        this.item = CraftItemStack.asBukkitCopy(nmsitem);
    }

    @Override
    public int getInt(String key) {
        ItemStack nmsitem = CraftItemStack.asNMSCopy(item);
        CompoundTag tag = nmsitem.getOrCreateTag();
        CompoundTag starcosmetics = tag.getCompound(ROOT);

        return starcosmetics.getInt(key);
    }

    @Override
    public void set(String key, int value) {
        ItemStack nmsitem = CraftItemStack.asNMSCopy(item);
        CompoundTag tag = nmsitem.getOrCreateTag();
        CompoundTag starcosmetics = tag.getCompound(ROOT);

        starcosmetics.putInt(key, value);
        tag.put(ROOT, starcosmetics);
        nmsitem.setTag(tag);
        this.item = CraftItemStack.asBukkitCopy(nmsitem);
    }

    @Override
    public double getDouble(String key) {
        ItemStack nmsitem = CraftItemStack.asNMSCopy(item);
        CompoundTag tag = nmsitem.getOrCreateTag();
        CompoundTag starcosmetics = tag.getCompound(ROOT);

        return starcosmetics.getDouble(key);
    }

    @Override
    public void set(String key, double value) {
        ItemStack nmsitem = CraftItemStack.asNMSCopy(item);
        CompoundTag tag = nmsitem.getOrCreateTag();
        CompoundTag starcosmetics = tag.getCompound(ROOT);

        starcosmetics.putDouble(key, value);
        tag.put(ROOT, starcosmetics);
        nmsitem.setTag(tag);
        this.item = CraftItemStack.asBukkitCopy(nmsitem);
    }

    @Override
    public UUID getUUID(String key) {
        ItemStack nmsitem = CraftItemStack.asNMSCopy(item);
        CompoundTag tag = nmsitem.getOrCreateTag();
        CompoundTag starcosmetics = tag.getCompound(ROOT);

        return starcosmetics.getUUID(key);
    }

    @Override
    public void set(String key, UUID value) {
        ItemStack nmsitem = CraftItemStack.asNMSCopy(item);
        CompoundTag tag = nmsitem.getOrCreateTag();
        CompoundTag starcosmetics = tag.getCompound(ROOT);

        starcosmetics.putUUID(key, value);
        tag.put(ROOT, starcosmetics);
        nmsitem.setTag(tag);
        this.item = CraftItemStack.asBukkitCopy(nmsitem);
    }

    @Override
    public Class<?> getClass(String key) {
        ItemStack nmsitem = CraftItemStack.asNMSCopy(item);
        CompoundTag tag = nmsitem.getOrCreateTag();
        CompoundTag starcosmetics = tag.getCompound(ROOT);

        try {
            return Class.forName(new String(starcosmetics.getByteArray(key)));
        } catch (ClassNotFoundException e) {
            StarConfig.print(e);
            return null;
        }
    }

    @Override
    public void set(String key, Class<?> clazz) {
        ItemStack nmsitem = CraftItemStack.asNMSCopy(item);
        CompoundTag tag = nmsitem.getOrCreateTag();
        CompoundTag starcosmetics = tag.getCompound(ROOT);

        starcosmetics.putByteArray(key, clazz.getName().getBytes());
        tag.put(ROOT, starcosmetics);
        nmsitem.setTag(tag);
        this.item = CraftItemStack.asBukkitCopy(nmsitem);
    }

    @Override
    public void set(String key, SoundEventSelection value) {
        ItemStack nmsitem = CraftItemStack.asNMSCopy(item);
        CompoundTag tag = nmsitem.getOrCreateTag();
        CompoundTag starcosmetics = tag.getCompound(ROOT);

        CompoundTag selection = new CompoundTag();
        selection.putString("sound", value.getSound().name());
        selection.putString("event", value.getEvent().getName());
        selection.putLong("timestamp", value.getTimestamp().getTime());
        selection.putUUID("player", value.getPlayer().getUniqueId());
        selection.putFloat("volume", value.getVolume());
        selection.putFloat("pitch", value.getPitch());

        starcosmetics.put(key, selection);
        tag.put(ROOT, starcosmetics);
        nmsitem.setTag(tag);
        this.item = CraftItemStack.asBukkitCopy(nmsitem);
    }

    @Override
    public SoundEventSelection getSoundEventSelection(String key) {
        try {
            ItemStack nmsitem = CraftItemStack.asNMSCopy(item);
            CompoundTag tag = nmsitem.getOrCreateTag();
            CompoundTag starcosmetics = tag.getCompound(ROOT);

            CompoundTag selection = starcosmetics.getCompound(key);

            return SoundEventSelection.of(
                    Class.forName(selection.getString("event")).asSubclass(Event.class),
                    Sound.valueOf(selection.getString("sound")),
                    selection.getFloat("volume"),
                    selection.getFloat("pitch"),
                    Bukkit.getOfflinePlayer(selection.getUUID("player")),
                    new Date(selection.getLong("timestamp"))
            );
        } catch (ClassNotFoundException | ClassCastException e) {
            StarConfig.print(e);
            return null;
        }
    }

    @Override
    public void set(String key, float value) {
        ItemStack nmsitem = CraftItemStack.asNMSCopy(item);
        CompoundTag tag = nmsitem.getOrCreateTag();
        CompoundTag starcosmetics = tag.getCompound(ROOT);

        starcosmetics.putFloat(key, value);
        tag.put(ROOT, starcosmetics);
        nmsitem.setTag(tag);
        this.item = CraftItemStack.asBukkitCopy(nmsitem);
    }

    @Override
    public float getFloat(String key) {
        ItemStack nmsitem = CraftItemStack.asNMSCopy(item);
        CompoundTag tag = nmsitem.getOrCreateTag();
        CompoundTag starcosmetics = tag.getCompound(ROOT);

        return starcosmetics.getFloat(key);
    }

}
