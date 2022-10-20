package me.gamercoder215.starcosmetics.wrapper.nbt;

import net.minecraft.server.v1_15_R1.ItemStack;
import net.minecraft.server.v1_15_R1.NBTTagCompound;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;

import me.gamercoder215.starcosmetics.api.StarConfig;

import java.util.UUID;

public class NBTWrapper1_15_R1 extends NBTWrapper {

    public NBTWrapper1_15_R1(org.bukkit.inventory.ItemStack item) {
        super(item);
    }

    @Override
    public String getNBTString(String key) {
        ItemStack nmsitem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = nmsitem.getOrCreateTag();
        NBTTagCompound starcosmetics = tag.getCompound(ROOT);

        return starcosmetics.getString(key);
    }

    @Override
    public void setNBT(String key, String value) {
        ItemStack nmsitem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = nmsitem.getOrCreateTag();
        NBTTagCompound starcosmetics = tag.getCompound(ROOT);

        starcosmetics.setString(key, value);
        tag.set(ROOT, starcosmetics);
        nmsitem.setTag(tag);
        this.item = CraftItemStack.asBukkitCopy(nmsitem);
    }

    @Override
    public boolean getNBTBoolean(String key) {
        ItemStack nmsitem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = nmsitem.getOrCreateTag();
        NBTTagCompound starcosmetics = tag.getCompound(ROOT);

        return starcosmetics.getBoolean(key);
    }

    @Override
    public void setNBT(String key, boolean value) {
        ItemStack nmsitem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = nmsitem.getOrCreateTag();
        NBTTagCompound starcosmetics = tag.getCompound(ROOT);

        starcosmetics.setBoolean(key, value);
        tag.set(ROOT, starcosmetics);
        nmsitem.setTag(tag);
        this.item = CraftItemStack.asBukkitCopy(nmsitem);
    }

    @Override
    public int getNBTInt(String key) {
        ItemStack nmsitem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = nmsitem.getOrCreateTag();
        NBTTagCompound starcosmetics = tag.getCompound(ROOT);

        return starcosmetics.getInt(key);
    }

    @Override
    public void setNBT(String key, int value) {
        ItemStack nmsitem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = nmsitem.getOrCreateTag();
        NBTTagCompound starcosmetics = tag.getCompound(ROOT);

        starcosmetics.setInt(key, value);
        tag.set(ROOT, starcosmetics);
        nmsitem.setTag(tag);
        this.item = CraftItemStack.asBukkitCopy(nmsitem);
    }

    @Override
    public double getNBTDouble(String key) {
        ItemStack nmsitem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = nmsitem.getOrCreateTag();
        NBTTagCompound starcosmetics = tag.getCompound(ROOT);

        return starcosmetics.getDouble(key);
    }

    @Override
    public void setNBT(String key, double value) {
        ItemStack nmsitem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = nmsitem.getOrCreateTag();
        NBTTagCompound starcosmetics = tag.getCompound(ROOT);

        starcosmetics.setDouble(key, value);
        tag.set(ROOT, starcosmetics);
        nmsitem.setTag(tag);
        this.item = CraftItemStack.asBukkitCopy(nmsitem);
    }

    @Override
    public UUID getNBTUUID(String key) {
        ItemStack nmsitem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = nmsitem.getOrCreateTag();
        NBTTagCompound starcosmetics = tag.getCompound(ROOT);

        return UUID.fromString(new String(starcosmetics.getByteArray(key)));
    }

    @Override
    public void setNBT(String key, UUID value) {
        ItemStack nmsitem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = nmsitem.getOrCreateTag();
        NBTTagCompound starcosmetics = tag.getCompound(ROOT);

        starcosmetics.setByteArray(key, value.toString().getBytes());
        tag.set(ROOT, starcosmetics);
        nmsitem.setTag(tag);
        this.item = CraftItemStack.asBukkitCopy(nmsitem);
    }

    @Override
    public Class<?> getNBTClass(String key) {
        ItemStack nmsitem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = nmsitem.getOrCreateTag();
        NBTTagCompound starcosmetics = tag.getCompound(ROOT);

        try {
            return Class.forName(new String(starcosmetics.getByteArray(key)));
        } catch (ClassNotFoundException e) {
            StarConfig.print(e);
            return null;
        }
    }

    @Override
    public void setNBT(String key, Class<?> clazz) {
        ItemStack nmsitem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = nmsitem.getOrCreateTag();
        NBTTagCompound starcosmetics = tag.getCompound(ROOT);

        starcosmetics.setByteArray(key, clazz.getName().getBytes());
        tag.set(ROOT, starcosmetics);
        nmsitem.setTag(tag);
        this.item = CraftItemStack.asBukkitCopy(nmsitem);
    }

}
