package me.gamercoder215.starcosmetics.util.inventory;

import com.google.common.collect.ImmutableList;
import me.gamercoder215.starcosmetics.util.StarMaterial;
import me.gamercoder215.starcosmetics.wrapper.nbt.NBTWrapper;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public final class ItemBuilder {

    private ItemStack item;

    public static final ItemStack GUI_BACKGROUND = ItemBuilder.of(StarMaterial.BLACK_STAINED_GLASS_PANE)
            .id("gui_background")
            .name(" ")
            .build();

    private ItemBuilder(ItemStack item) {
        this.item = item;
    }

    public static ItemBuilder of(@NotNull ItemStack item) {
        return new ItemBuilder(item);
    }

    public static ItemBuilder of(@NotNull Material m) {
        return of(m, 1);
    }

    public static ItemBuilder of(@NotNull Material m, int amount) {
        return of(m, amount, 0);
    }

    public static ItemBuilder of(@NotNull Material m, int amount, int data) {
        return new ItemBuilder(new ItemStack(m, amount, (short) data));
    }

    public static ItemBuilder of(@NotNull StarMaterial sm) {
        return of(sm.findStack());
    }

    public static ItemBuilder of(@NotNull StarMaterial sm, int amount) {
        return of(sm.findStack(amount));
    }

    public ItemBuilder amount(int amount) {
        item.setAmount(amount);
        return this;
    }

    public ItemBuilder name(@NotNull String name) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder id(@NotNull String id) {
        NBTWrapper w = NBTWrapper.of(item);
        w.setID(id);
        this.item = w.getItem();
        return this;
    }

    public ItemBuilder lore(String... lore) {
        ItemMeta meta = item.getItemMeta();
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder lore(@NotNull Iterable<String> lore) {
        ItemMeta meta = item.getItemMeta();
        meta.setLore(ImmutableList.copyOf(lore));
        item.setItemMeta(meta);
        return this;
    }

    public ItemStack build() {
        return this.item;
    }

}
