package me.gamercoder215.starcosmetics.wrapper;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack;

import java.util.UUID;

public class NBTWrapper1_18_R2 extends NBTWrapper {

    public NBTWrapper1_18_R2(org.bukkit.inventory.ItemStack item) {
        super(item);
    }

    @Override
    public String getNBTString(String key) {
        ItemStack nmsitem = CraftItemStack.asNMSCopy(item);
        CompoundTag tag = nmsitem.getOrCreateTag();
        CompoundTag starcosmetics = tag.getCompound(ROOT);

        return starcosmetics.getString(key);
    }

    @Override
    public void setNBT(String key, String value) {
        ItemStack nmsitem = CraftItemStack.asNMSCopy(item);
        CompoundTag tag = nmsitem.getOrCreateTag();
        CompoundTag starcosmetics = tag.getCompound(ROOT);

        starcosmetics.putString(key, value);
        tag.put(ROOT, starcosmetics);
        nmsitem.setTag(tag);
        this.item = CraftItemStack.asBukkitCopy(nmsitem);
    }

    @Override
    public boolean getNBTBoolean(String key) {
        ItemStack nmsitem = CraftItemStack.asNMSCopy(item);
        CompoundTag tag = nmsitem.getOrCreateTag();
        CompoundTag starcosmetics = tag.getCompound(ROOT);

        return starcosmetics.getBoolean(key);
    }

    @Override
    public void setNBT(String key, boolean value) {
        ItemStack nmsitem = CraftItemStack.asNMSCopy(item);
        CompoundTag tag = nmsitem.getOrCreateTag();
        CompoundTag starcosmetics = tag.getCompound(ROOT);

        starcosmetics.putBoolean(key, value);
        tag.put(ROOT, starcosmetics);
        nmsitem.setTag(tag);
        this.item = CraftItemStack.asBukkitCopy(nmsitem);
    }

    @Override
    public int getNBTInt(String key) {
        ItemStack nmsitem = CraftItemStack.asNMSCopy(item);
        CompoundTag tag = nmsitem.getOrCreateTag();
        CompoundTag starcosmetics = tag.getCompound(ROOT);

        return starcosmetics.getInt(key);
    }

    @Override
    public void setNBT(String key, int value) {
        ItemStack nmsitem = CraftItemStack.asNMSCopy(item);
        CompoundTag tag = nmsitem.getOrCreateTag();
        CompoundTag starcosmetics = tag.getCompound(ROOT);

        starcosmetics.putInt(key, value);
        tag.put(ROOT, starcosmetics);
        nmsitem.setTag(tag);
        this.item = CraftItemStack.asBukkitCopy(nmsitem);
    }

    @Override
    public double getNBTDouble(String key) {
        ItemStack nmsitem = CraftItemStack.asNMSCopy(item);
        CompoundTag tag = nmsitem.getOrCreateTag();
        CompoundTag starcosmetics = tag.getCompound(ROOT);

        return starcosmetics.getDouble(key);
    }

    @Override
    public void setNBT(String key, double value) {
        ItemStack nmsitem = CraftItemStack.asNMSCopy(item);
        CompoundTag tag = nmsitem.getOrCreateTag();
        CompoundTag starcosmetics = tag.getCompound(ROOT);

        starcosmetics.putDouble(key, value);
        tag.put(ROOT, starcosmetics);
        nmsitem.setTag(tag);
        this.item = CraftItemStack.asBukkitCopy(nmsitem);
    }

    @Override
    public UUID getNBTUUID(String key) {
        ItemStack nmsitem = CraftItemStack.asNMSCopy(item);
        CompoundTag tag = nmsitem.getOrCreateTag();
        CompoundTag starcosmetics = tag.getCompound(ROOT);

        return UUID.fromString(new String(starcosmetics.getByteArray(key)));
    }

    @Override
    public void setNBT(String key, UUID value) {
        ItemStack nmsitem = CraftItemStack.asNMSCopy(item);
        CompoundTag tag = nmsitem.getOrCreateTag();
        CompoundTag starcosmetics = tag.getCompound(ROOT);

        starcosmetics.putByteArray(key, value.toString().getBytes());
        tag.put(ROOT, starcosmetics);
        nmsitem.setTag(tag);
        this.item = CraftItemStack.asBukkitCopy(nmsitem);
    }

}
