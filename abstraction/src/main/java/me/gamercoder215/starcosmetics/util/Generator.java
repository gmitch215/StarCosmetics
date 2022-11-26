package me.gamercoder215.starcosmetics.util;

import me.gamercoder215.starcosmetics.api.CompletionCriteria;
import me.gamercoder215.starcosmetics.api.Rarity;
import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.api.cosmetics.CosmeticLocation;
import me.gamercoder215.starcosmetics.api.cosmetics.pet.PetType;
import me.gamercoder215.starcosmetics.api.cosmetics.structure.StructureCompletion;
import me.gamercoder215.starcosmetics.api.cosmetics.structure.StructureInfo;
import me.gamercoder215.starcosmetics.api.player.PlayerSetting;
import me.gamercoder215.starcosmetics.api.player.SoundEventSelection;
import me.gamercoder215.starcosmetics.api.player.StarPlayer;
import me.gamercoder215.starcosmetics.util.inventory.ItemBuilder;
import me.gamercoder215.starcosmetics.util.inventory.StarInventory;
import me.gamercoder215.starcosmetics.util.inventory.StarInventoryUtil;
import me.gamercoder215.starcosmetics.wrapper.commands.CommandWrapper;
import me.gamercoder215.starcosmetics.wrapper.nbt.NBTWrapper;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.ChatPaginator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

import static me.gamercoder215.starcosmetics.wrapper.Wrapper.*;
import static me.gamercoder215.starcosmetics.wrapper.nbt.NBTWrapper.of;

@SuppressWarnings("unchecked")
public final class Generator {

    public static CommandWrapper cw;

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

            CompletionCriteria nextCriteria = CompletionCriteria.fromSelectionLimit(selLimit + 1);

            lMeta.setLore(Arrays.asList(
                    ChatColor.YELLOW + nextCriteria.getDisplayMessage(),
                    ChatColor.GOLD + getWithArgs("constants.completed", String.format("%,.2f", nextCriteria.getProgressPercentage(p)) + "%")
            ));
            limit.setItemMeta(lMeta);
            inv.setItem(18, limit);
        }

        return inv;
    }

    @NotNull
    public static StarInventory createStructureInventory(@NotNull Player p) {
        StarPlayer sp = new StarPlayer(p);

        boolean completed = false;
        for (StructureCompletion c : StructureCompletion.values())
            if (sp.hasCompleted(c)) {
                completed = true;
                break;
            }

        if (!completed && !p.hasPermission("starcosmetics.admin.bypasscheck")) throw new IllegalArgumentException("Player has not unlocked any structures!");

        List<ItemStack> items = StarConfig.getRegistry().getAvailableStructures()
                .stream()
                .map(StarInventoryUtil::toItemStack)
                .collect(Collectors.toList());


        List<StarInventory> pages = new ArrayList<>();
        Map<Rarity, StarInventory> rarityPages = new HashMap<>();

        items.forEach(item -> {
            NBTWrapper nbt = of(item);
            StructureInfo info = nbt.getStructureInfo("info");

            StarInventory page = rarityPages.get(info.getRarity());

            if (page == null) {
                page = genGUI(54, get("menu.cosmetics.choose.structure") + " | " + info.getRarity());
                rarityPages.put(info.getRarity(), page);
                page.setAttribute("rarity", info.getRarity());

                StarInventoryUtil.setBack(page, cw::cosmetics);
            }

            page.addItem(item);
        });

        rarityPages.forEach((r, inv) -> pages.add(inv));
        pages.sort(Comparator.comparing(inv -> inv.getAttribute("rarity", Rarity.class)));

        StarInventoryUtil.setPages(pages);
        return pages.get(0);
    }

    @NotNull
    public static StarInventory createSettingsInventory(@NotNull Player p) {
        StarPlayer sp = new StarPlayer(p);
        StarInventory inv = genGUI(36, get("menu.settings"));
        inv.setCancelled();

        List<ItemStack> items = new ArrayList<>();

        for (PlayerSetting<?> setting : PlayerSetting.values()) {
            ItemStack item = null;

            if (Boolean.class.isAssignableFrom(setting.getType())) {
                boolean on = sp.getSetting((PlayerSetting<Boolean>) setting);

                item = on ? StarMaterial.LIME_TERRACOTTA.findStack() : StarMaterial.RED_TERRACOTTA.findStack();
                ItemMeta meta = item.getItemMeta();

                meta.setDisplayName(ChatColor.YELLOW + setting.getDisplayName() + ": " + (on ? ChatColor.GREEN + get("constants.on") : ChatColor.RED + get("constants.off")));
                if (on) {
                    meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
                    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                }
                item.setItemMeta(meta);

                NBTWrapper nbt = of(item);
                nbt.setID("toggle:setting:boolean");
                nbt.set("setting", setting.getId());
                item = nbt.getItem();
            }
            // TODO Add Implementation for Non-Boolean Settings

            ItemMeta meta = item.getItemMeta();
            List<String> lore = new ArrayList<>();
            lore.add(" ");
            lore.addAll(Arrays.stream(
                            ChatPaginator.wordWrap(setting.getDescription(), 30)
                    ).map(s -> ChatColor.GRAY + s).collect(Collectors.toList()));

            meta.setLore(lore);
            item.setItemMeta(meta);

            items.add(item);
        }

        Map<Integer, List<ItemStack>> rows = Generator.generateRows(items);
        inv.setAttribute("rows", rows);
        StarInventoryUtil.setRows(inv, rows);
        StarInventoryUtil.setScrolls(inv);

        return inv;
    }

    @NotNull
    public static StarInventory createPetInventory(@NotNull Player p) {
        List<StarInventory> pages = new ArrayList<>();

        Map<Rarity, StarInventory> rarityPages = new HashMap<>();
        Map<Rarity, List<ItemStack>> itemsMap = new HashMap<>();

        for (PetType type : PetType.values()) {
            List<ItemStack> items = itemsMap.get(type.getRarity());
            StarInventory page = rarityPages.get(type.getRarity());

            if (page == null) {
                page = genGUI(54, get("menu.cosmetics.choose.pet") + " | " + type.getRarity());
                page.setAttribute("rarity", type.getRarity());
                items = new ArrayList<>();

                rarityPages.put(type.getRarity(), page);
                itemsMap.put(type.getRarity(), items);
            }

            ItemStack item = StarInventoryUtil.toItemStack(p, type.getInfo());
            items.add(item);
        }

        rarityPages.forEach((r, inv) -> {
            Map<Integer, List<ItemStack>> rows = Generator.generateRows(itemsMap.get(r));
            inv.setAttribute("rows", rows);
            StarInventoryUtil.setRows(inv, rows);
            StarInventoryUtil.setScrolls(inv);

            pages.add(inv);
        });

        pages.sort(Comparator.comparing(inv -> inv.getAttribute("rarity", Rarity.class)));
        StarInventoryUtil.setPages(pages);

        return pages.get(0);
    }

}
