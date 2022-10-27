package me.gamercoder215.starcosmetics.util.inventory;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class MockStarInventory implements StarInventory {

    private final Map<String, Object> attributes = new HashMap<>();
    private String key;

    public MockStarInventory(String key) {
        this.key = key;
    }

    public MockStarInventory() {
        this.key = "";
    }


    @Override
    public String getKey() {
        return key;
    }

    @Override
    public Map<String, Object> getAllAttributes() {
        return attributes;
    }

    @Override
    public void setAttribute(String key, Object value) {
        attributes.put(key, value);
    }

    @Override
    public void removeAttribute(String key) {
        attributes.remove(key);
    }

    @Override
    public int getSize() {
        return 54;
    }

    @Override
    public int getMaxStackSize() {
        return 64;
    }

    @Override
    public void setMaxStackSize(int size) {}

    @Override
    public String getName() {
        return key;
    }

    @Override
    public ItemStack getItem(int index) {
        return null;
    }

    @Override
    public void setItem(int index, ItemStack item) {}

    @Override
    public HashMap<Integer, ItemStack> addItem(ItemStack... items) throws IllegalArgumentException {
        return new HashMap<>();
    }

    @Override
    public HashMap<Integer, ItemStack> removeItem(ItemStack... items) throws IllegalArgumentException {
        return new HashMap<>();
    }

    @Override
    public ItemStack[] getContents() {
        return new ItemStack[0];
    }

    @Override
    public void setContents(ItemStack[] items) throws IllegalArgumentException {}

    @Override
    public ItemStack[] getStorageContents() {
        return new ItemStack[0];
    }

    @Override
    public void setStorageContents(ItemStack[] items) throws IllegalArgumentException {}

    @Override
    public boolean contains(int materialId) {
        return false;
    }

    @Override
    public boolean contains(Material material) throws IllegalArgumentException {
        return false;
    }

    @Override
    public boolean contains(ItemStack item) {
        return false;
    }

    @Override
    public boolean contains(int materialId, int amount) {
        return false;
    }

    @Override
    public boolean contains(Material material, int amount) throws IllegalArgumentException {
        return false;
    }

    @Override
    public boolean contains(ItemStack item, int amount) {
        return false;
    }

    @Override
    public boolean containsAtLeast(ItemStack item, int amount) {
        return false;
    }

    @Override
    public HashMap<Integer, ? extends ItemStack> all(int materialId) {
        return new HashMap<>();
    }

    @Override
    public HashMap<Integer, ? extends ItemStack> all(Material material) throws IllegalArgumentException {
        return new HashMap<>();
    }

    @Override
    public HashMap<Integer, ? extends ItemStack> all(ItemStack item) {
        return new HashMap<>();
    }

    @Override
    public int first(int materialId) {
        return 0;
    }

    @Override
    public int first(Material material) throws IllegalArgumentException {
        return 0;
    }

    @Override
    public int first(ItemStack item) {
        return 0;
    }

    @Override
    public int firstEmpty() {
        return 0;
    }

    @Override
    public void remove(int materialId) {}

    @Override
    public void remove(Material material) throws IllegalArgumentException {}

    @Override
    public void remove(ItemStack item) {}

    @Override
    public void clear(int index) {}

    @Override
    public void clear() {}

    @Override
    public List<HumanEntity> getViewers() {
        return new ArrayList<>();
    }

    @Override
    public String getTitle() { return key; }

    @Override
    public InventoryType getType() {
        return InventoryType.CHEST;
    }

    @Override
    public InventoryHolder getHolder() {
        return null;
    }

    @Override
    public ListIterator<ItemStack> iterator() {
        return null;
    }

    @Override
    public ListIterator<ItemStack> iterator(int index) {
        return null;
    }

    @Override
    public Location getLocation() {
        return null;
    }
}
