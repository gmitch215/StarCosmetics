package me.gamercoder215.starcosmetics.util;

import me.gamercoder215.starcosmetics.util.inventory.StarInventory;
import me.gamercoder215.starcosmetics.wrapper.Wrapper;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public final class Generator {

    private static final Wrapper w = Wrapper.w;

    public static ItemStack getGUIBackground() {
        ItemStack item = Wrapper.getWrapper().isLegacy() ? new ItemStack(Material.matchMaterial("STAINED_GLASS_PANE"), 1, (short) 7) : new ItemStack(Material.matchMaterial("GRAY_STAINED_GLASS_PANE"));
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(" ");
        item.setItemMeta(meta);
        return item;
    }

    public static StarInventory genGUI(String key, int size, String name) {
        if (size < 9 || size > 54) return null;
        if (size % 9 > 0) return null;

        StarInventory inv = w.createInventory(key, size, name);
        ItemStack bg = getGUIBackground();

        if (size < 27) return inv;

        for (int i = 0; i < 9; i++) inv.setItem(i, bg);
        for (int i = size - 9; i < size; i++) inv.setItem(i, bg);
        for (int i = 1; i < Math.floor((double) size / 9D) - 1; i++) {
            inv.setItem(i * 9, bg);
            inv.setItem(((i + 1) * 9) - 1, bg);
        }

        return inv;
    }

}
