package me.gamercoder215.starcosmetics.util;

import com.google.common.collect.ImmutableList;
import me.gamercoder215.starcosmetics.api.cosmetics.registry.CosmeticLocation;
import me.gamercoder215.starcosmetics.util.inventory.ItemBuilder;
import me.gamercoder215.starcosmetics.util.inventory.StarInventory;
import me.gamercoder215.starcosmetics.util.inventory.StarInventoryUtil;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static me.gamercoder215.starcosmetics.wrapper.Wrapper.getWrapper;

public final class Generator {

    private Generator() { throw new UnsupportedOperationException(); }

    public static StarInventory genGUI(int size, String name) {
        return genGUI("", size, name);
    }

    @Nullable
    public static StarInventory genGUI(String key, int size, String name) {
        if (size < 9 || size > 54) return null;
        if (size % 9 > 0) return null;

        StarInventory inv = getWrapper().createInventory(key, size, name);
        ItemStack bg = ItemBuilder.GUI_BACKGROUND;

        if (size < 27) return inv;

        for (int i = 0; i < 9; i++) inv.setItem(i, bg);
        for (int i = size - 9; i < size; i++) inv.setItem(i, bg);
        for (int i = 1; i < Math.floor((double) size / 9D) - 1; i++) {
            inv.setItem(i * 9, bg);
            inv.setItem(((i + 1) * 9) - 1, bg);
        }

        return inv;
    }

    @NotNull
    public static Map<Integer, List<ItemStack>> generateRows(ItemStack... items) {
        return generateRows(Arrays.asList(items));
    }

    @NotNull
    public static StarInventory createSelectionInventory(List<? extends CosmeticLocation<?>> it, @NotNull String display) {
        StarInventory inv = genGUI(54, display);

        Map<Integer, List<ItemStack>> rows = generateRows(ImmutableList.copyOf(it)
                .stream()
                .map(StarInventoryUtil::toItemStack)
                .collect(Collectors.toList()));

        inv.setAttribute("rows", rows);
        StarInventoryUtil.setRows(inv, rows);
        StarInventoryUtil.setScrolls(inv);

        return inv;
    }

    @NotNull
    public static Map<Integer, List<ItemStack>> generateRows(Iterable<ItemStack> it) {
        Map<Integer, List<ItemStack>> map = new HashMap<>();
        List<ItemStack> list = ImmutableList.copyOf(it);
        if (list.size() == 0) return map;

        int size = list.size();

        if (size < 7) {
            map.put(0, list);
            return map;
        } else {
            int rows = size / 7;
            int remainder = size % 7;

            for (int i = 0; i < rows; i++) map.put(i, list.subList(i * 7, (i + 1) * 7));

            if (remainder != 0) map.put(rows, list.subList(rows * 7, rows * 7 + remainder));
        }

        return map;
    }
}
