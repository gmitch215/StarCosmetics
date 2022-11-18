package me.gamercoder215.starcosmetics.util;

import me.gamercoder215.starcosmetics.api.CompletionCriteria;
import me.gamercoder215.starcosmetics.api.Rarity;
import me.gamercoder215.starcosmetics.api.cosmetics.registry.CosmeticLocation;
import me.gamercoder215.starcosmetics.api.player.StarPlayer;
import me.gamercoder215.starcosmetics.api.player.cosmetics.SoundEventSelection;
import me.gamercoder215.starcosmetics.util.inventory.ItemBuilder;
import me.gamercoder215.starcosmetics.util.inventory.StarInventory;
import me.gamercoder215.starcosmetics.util.inventory.StarInventoryUtil;
import me.gamercoder215.starcosmetics.wrapper.nbt.NBTWrapper;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

import static me.gamercoder215.starcosmetics.wrapper.Wrapper.get;
import static me.gamercoder215.starcosmetics.wrapper.Wrapper.getWrapper;
import static me.gamercoder215.starcosmetics.wrapper.nbt.NBTWrapper.of;

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
    public static List<StarInventory> createSelectionInventory(Player p, List<? extends CosmeticLocation<?>> it, @NotNull String display) {
        List<StarInventory> pages = generatePages(p, it, display);

        for (StarInventory inv : pages) {
            Rarity current = inv.getAttribute("rarity", Rarity.class);

            Map<Integer, List<ItemStack>> rows = generateRows(it
                    .stream()
                    .filter(c -> c.getRarity() == current)
                    .map(loc -> StarInventoryUtil.toItemStack(p, loc))
                    .collect(Collectors.toList()));

            inv.setAttribute("rows", rows);
            StarInventoryUtil.setRows(inv, rows);
            StarInventoryUtil.setScrolls(inv);
        }

        StarInventoryUtil.setPages(pages);

        return pages;
    }

    @NotNull
    public static Map<Integer, List<ItemStack>> generateRows(@NotNull Collection<ItemStack> col) {
        Map<Integer, List<ItemStack>> map = new HashMap<>();
        List<ItemStack> list = col
                .stream()
                .filter(Objects::nonNull)
                .filter(m -> getWrapper().isItem(m.getType()))
                .collect(Collectors.toList());

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

    public static List<StarInventory> generatePages(Player p, Collection<? extends CosmeticLocation<?>> locs) {
        Optional<? extends CosmeticLocation<?>> o = locs.stream().findFirst();
        return o.map(cosmeticLocation -> generatePages(p, locs, cosmeticLocation.getParent().getDisplayName())).orElseGet(ArrayList::new);
    }

    public static List<StarInventory> generatePages(Player p, Collection<? extends CosmeticLocation<?>> locs, String display) {
        List<StarInventory> pages = new ArrayList<>();

        Map<Rarity, StarInventory> rarityPages = new HashMap<>();
        locs.forEach(loc -> {
            StarInventory page = rarityPages.get(loc.getRarity());
            if (page == null) {
                page = genGUI(54, display + " | " + loc.getRarity());
                rarityPages.put(loc.getRarity(), page);
                page.setAttribute("rarity", loc.getRarity());
            }

            page.addItem(StarInventoryUtil.toItemStack(p, loc));
        });

        rarityPages.forEach((r, inv) -> pages.add(inv));

        pages.sort(Comparator.comparing(inv -> inv.getAttribute("rarity", Rarity.class)));

        return pages;
    }

    public static ItemStack generateHead(OfflinePlayer p) {
        ItemStack head = StarMaterial.PLAYER_HEAD.findStack();
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.setDisplayName(p.getName());
        meta.setOwner(p.getName());
        head.setItemMeta(meta);

        return head;
    }

    public static final int[] BOTTOM_HALF_SLOTS = {
            10, 11, 12, 13, 14, 15, 16,
            19, 20, 21, 22, 23, 24, 25,
            28, 29, 30, 31, 32, 33, 34,
            37, 38, 39, 40, 41, 42, 43,
            46, 47, 48, 49, 50, 51, 52
    };

    @NotNull
    public static StarInventory createSelectionInventory(@NotNull Player p) {
        StarInventory inv = genGUI(54, get("menu.cosmetics.selection"));
        inv.setCancelled();
        for (int i = 46; i < 53; i++) inv.setItem(i, null);

        StarPlayer sp = new StarPlayer(p);
        int selLimit = sp.getSelectionLimit();
        int size = sp.getSoundSelections().size();

        for (SoundEventSelection s : sp.getSoundSelections()) inv.addItem(StarInventoryUtil.toItemStack(s));

        ItemStack add = StarInventoryUtil.getHead("plus");
        ItemMeta aMeta = add.getItemMeta();
        aMeta.setDisplayName(ChatColor.GREEN + get("constants.cosmetics.add_selection"));
        add.setItemMeta(aMeta);

        NBTWrapper nbt = of(add);
        nbt.setID("add:soundevent");
        add = nbt.getItem();

        if (size < selLimit) inv.addItem(add);

        for (int j = 0; j < BOTTOM_HALF_SLOTS.length; j++) {
            if (selLimit > j) continue;
            inv.setItem(BOTTOM_HALF_SLOTS[j], ItemBuilder.GUI_BACKGROUND);
        }

        if (selLimit < 35) {
            ItemStack limit = new ItemStack(Material.NETHER_STAR);
            ItemMeta lMeta = limit.getItemMeta();
            lMeta.setDisplayName(ChatColor.GOLD + get("constants.cosmetics.selection_limit"));
            lMeta.setLore(Collections.singletonList(
                    ChatColor.YELLOW + CompletionCriteria.fromSelectionLimit(selLimit + 1).getDisplayMessage()
            ));
            limit.setItemMeta(lMeta);
            inv.setItem(18, limit);
        }

        return inv;
    }

}
