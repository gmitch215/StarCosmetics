package me.gamercoder215.starcosmetics.util.inventory;

import com.google.common.collect.ImmutableList;
import me.gamercoder215.starcosmetics.util.StarMaterial;
import me.gamercoder215.starcosmetics.wrapper.nbt.NBTWrapper;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.function.Consumer;

import static me.gamercoder215.starcosmetics.wrapper.Wrapper.get;

public final class ItemBuilder {

    private ItemStack item;

    private static boolean loaded = false;

    public static void loadItems() {
        if (loaded) throw new IllegalArgumentException("Items already loaded!");

        STOP_SOUND = ItemBuilder.of(Material.BARRIER)
                .name(ChatColor.RED + get("constants.stop_sound"))
                .id("stop_sound")
                .build();

        SAVE = ItemBuilder.of(StarMaterial.LIME_WOOL)
                .name(ChatColor.GREEN + get("constants.save"))
                .nbt(nbt -> nbt.set("item", "save"))
                .build();

        GUI_BACKGROUND = ItemBuilder.of(StarMaterial.BLACK_STAINED_GLASS_PANE)
                .id("gui_background")
                .name(" ")
                .build();

        loaded = true;
    }

    public static ItemStack GUI_BACKGROUND;
    public static ItemStack SAVE;
    public static ItemStack STOP_SOUND;


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

    public static ItemBuilder ofHead(@NotNull String head) {
        return of(StarInventoryUtil.getHead(head));
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

    public ItemBuilder nbt(@NotNull Consumer<NBTWrapper> nbt) {
        NBTWrapper w = NBTWrapper.of(item);
        nbt.accept(w);
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
