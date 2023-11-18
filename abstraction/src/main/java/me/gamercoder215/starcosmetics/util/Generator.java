package me.gamercoder215.starcosmetics.util;

import me.gamercoder215.starcosmetics.api.CompletionCriteria;
import me.gamercoder215.starcosmetics.api.Rarity;
import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import me.gamercoder215.starcosmetics.api.cosmetics.CosmeticLocation;
import me.gamercoder215.starcosmetics.api.cosmetics.CosmeticParent;
import me.gamercoder215.starcosmetics.api.cosmetics.emote.Emote;
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

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static me.gamercoder215.starcosmetics.util.Constants.w;
import static me.gamercoder215.starcosmetics.util.inventory.StarInventoryUtil.*;
import static me.gamercoder215.starcosmetics.wrapper.Wrapper.get;
import static me.gamercoder215.starcosmetics.wrapper.Wrapper.getWithArgs;
import static me.gamercoder215.starcosmetics.wrapper.nbt.NBTWrapper.builder;
import static me.gamercoder215.starcosmetics.wrapper.nbt.NBTWrapper.of;

@SuppressWarnings("unchecked")
public final class Generator {

    public static CommandWrapper cw;

    private Generator() { throw new UnsupportedOperationException(); }

    public static StarInventory genGUI(int size, String name) {
        return genGUI("", size, name);
    }

    @NotNull
    public static StarInventory genGUI(String key, int size, String name) {
        if (size < 9 || size > 54) throw new IllegalStateException("Invalid inventory size: " + size);
        if (size % 9 > 0) throw new IllegalStateException("Invalid inventory size: " + size);

        StarInventory inv = w.createInventory(key, size, name);
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
    public static List<StarInventory> createSelectionInventory(Player p, List<? extends CosmeticLocation<?>> it, @NotNull String display) {
        List<StarInventory> pages = generatePages(p, it, display);

        for (StarInventory inv : pages) {
            Rarity current = inv.getAttribute("rarity", Rarity.class);

            setReset(inv);

            Map<Integer, List<ItemStack>> rows = generateRows(it
                    .stream()
                    .filter(c -> c.getRarity() == current)
                    .map(loc -> toItemStack(p, loc))
                    .collect(Collectors.toList()));

            inv.setAttribute("rows", rows);
            setRows(inv, rows);
            setScrolls(inv);
        }

        setPages(pages);

        return pages;
    }

    @NotNull
    public static Map<Integer, List<ItemStack>> generateRows(@NotNull Collection<ItemStack> col) {
        Map<Integer, List<ItemStack>> map = new HashMap<>();
        List<ItemStack> list = col
                .stream()
                .filter(Objects::nonNull)
                .filter(m -> w.isItem(m.getType()))
                .collect(Collectors.toList());

        if (list.isEmpty()) return map;

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
                page.setAttribute("parent", loc.getParent());
            }

            page.addItem(toItemStack(p, loc));
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

        for (SoundEventSelection s : sp.getSoundSelections()) inv.addItem(toItemStack(s));

        if (size < selLimit) inv.addItem(builder(getHead("plus"),
                meta -> meta.setDisplayName(ChatColor.GREEN + get("constants.cosmetics.add_selection")),
                nbt -> nbt.setID("add:soundevent")
        ));

        for (int j = 0; j < BOTTOM_HALF_SLOTS.length; j++) {
            if (selLimit > j) continue;
            inv.setItem(BOTTOM_HALF_SLOTS[j], ItemBuilder.GUI_BACKGROUND);
        }

        if (selLimit < 35) {
            CompletionCriteria nextCriteria = CompletionCriteria.fromSelectionLimit(selLimit + 1);
            ItemStack limit = itemBuilder(Material.NETHER_STAR,
                    meta -> {
                        meta.setDisplayName(ChatColor.GOLD + get("constants.cosmetics.selection_limit"));
                        meta.setLore(Arrays.asList(
                                ChatColor.YELLOW + nextCriteria.getDisplayMessage(),
                                ChatColor.GOLD + getWithArgs("constants.completed", String.format("%,.2f", nextCriteria.getProgressPercentage(p)) + "%")
                        ));
                    }
            );

            inv.setItem(18, limit);
        }

        setBack(inv, 45, cw::cosmetics);

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

                setBack(page, cw::cosmetics);
            }

            page.addItem(item);
        });

        rarityPages.forEach((r, inv) -> pages.add(inv));
        pages.sort(Comparator.comparing(inv -> inv.getAttribute("rarity", Rarity.class)));

        setPages(pages);
        return pages.get(0);
    }

    @NotNull
    public static StarInventory createSettingsInventory(@NotNull Player p) {
        StarInventory inv = genGUI(36, get("menu.settings"));
        inv.setCancelled();

        List<ItemStack> items = new ArrayList<>();

        for (PlayerSetting<?> setting : PlayerSetting.values()) items.add(generateSetting(p, setting));

        Map<Integer, List<ItemStack>> rows = Generator.generateRows(items);
        inv.setAttribute("rows", rows);
        setRows(inv, rows);
        setScrolls(inv);

        return inv;
    }

    public static ItemStack generateSetting(@NotNull Player p, @NotNull PlayerSetting<?> setting) {
        StarPlayer sp = new StarPlayer(p);
        ItemStack item;

        if (Boolean.class.isAssignableFrom(setting.getType())) {
            boolean on = sp.getSetting((PlayerSetting<Boolean>) setting);
            item = builder(on ? StarMaterial.LIME_TERRACOTTA.findStack() : StarMaterial.RED_TERRACOTTA.findStack(),
                    meta -> {
                        meta.setDisplayName(ChatColor.YELLOW + setting.getDisplayName() + ": " + (on ? ChatColor.GREEN + get("constants.on") : ChatColor.RED + get("constants.off")));
                        if (on) {
                            meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
                            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        }
                    },
                    nbt -> {
                        nbt.setID("toggle:setting:boolean");
                        nbt.set("setting", setting.getId());
                    }
            );
        } else if (Enum.class.isAssignableFrom(setting.getType())) {
            Enum<?> value = sp.getSetting((PlayerSetting<Enum<?>>) setting);
            item = builder(StarMaterial.LIGHT_BLUE_TERRACOTTA.findStack(),
                meta -> meta.setDisplayName(ChatColor.YELLOW + setting.getDisplayName() + ": " + ChatColor.AQUA + value.name()),
                nbt -> {
                    nbt.setID("toggle:setting:enum");
                    nbt.set("setting", setting.getId());
                }
            );
        } else {
            Object value = sp.getSetting(setting);
            item = builder(StarMaterial.LIGHT_BLUE_TERRACOTTA.findStack(),
                meta ->  meta.setDisplayName(ChatColor.YELLOW + setting.getDisplayName() + ": " + ChatColor.YELLOW + value),
                nbt -> {
                    nbt.setID("toggle:setting");
                    nbt.set("setting", setting.getId());
                }
            );
        }

        String desc = setting.getDescription() == null ? null : StarConfig.getConfig().get(setting.getDescription());
        if (desc != null && !desc.equalsIgnoreCase("Unknown Value")) {
            ItemMeta meta = item.getItemMeta();
            List<String> lore = new ArrayList<>();

            lore.add(" ");
            lore.addAll(Arrays.stream(
                    ChatPaginator.wordWrap(desc, 30)
            ).map(s -> ChatColor.GRAY + s).collect(Collectors.toList()));

            meta.setLore(lore);
            item.setItemMeta(meta);
        }

        return item;
    }

    @NotNull
    public static StarInventory createPetInventory(@NotNull Player p) {
        StarInventory inv = genGUI(54, get("menu.cosmetics.choose.pet"));

        Map<Rarity, List<ItemStack>> itemsMap = new HashMap<>();
        StarConfig.getRegistry().getAllPets().keySet().forEach(type -> {
            Rarity r = type.getRarity();
            List<ItemStack> items = itemsMap.containsKey(r) ? itemsMap.get(r) : new ArrayList<>();
            items.add(toItemStack(p, type.getInfo()));
            itemsMap.put(r, items);
        });

        itemsMap.values().forEach(l -> l.sort(Comparator.comparing(i ->
                i.hasItemMeta() ? (i.getItemMeta().hasDisplayName() ? i.getItemMeta().getDisplayName() : "") : "")
        ));

        List<ItemStack> items = new ArrayList<>();

        // Rarities are ordered from lowest to highest
        for (Rarity r : Rarity.values()) if (itemsMap.containsKey(r)) items.addAll(itemsMap.get(r));

        Map<Integer, List<ItemStack>> rows = Generator.generateRows(items);
        inv.setAttribute("rows", rows);
        setRows(inv, rows);

        setScrolls(inv);
        setBack(inv, cw::cosmetics);

        inv.setItem(18, ItemBuilder.of(Material.BARRIER)
                .id("cancel:pet")
                .name(ChatColor.RED + get("menu.cosmetics.choose.pet.disable"))
                .build());

        return inv;
    }

    @NotNull
    public static StarInventory createParentInventory(@NotNull CosmeticParent parent) {
        int size = parent.getChildren().size() > 7 ? 45 : 27;

        StarInventory parentInv = genGUI("cosmetics_menu:" + parent.name(), size, get("menu.cosmetics." + parent.name().toLowerCase()));
        List<Integer> places = getGUIPlacements(size, parent.getChildren().size());

        for (int i = 0; i < parent.getChildren().size(); i++) {
            Cosmetic c = parent.getChildren().get(i);
            int place = places.get(i);
            ItemStack cItem = toItemStack(c);
            parentInv.setItem(place, cItem);
        }

        ItemStack resetAll = builder(Material.BARRIER,
            meta -> meta.setDisplayName(ChatColor.RED + get("constants.cosmetics.reset_all")),
            nbt -> {
                nbt.setID("cancel:cosmetic:all");
                nbt.set("parent", parent.name());
            }
        );
        parentInv.setItem(size - 1, resetAll);

        setBack(parentInv, cw::cosmetics);
        parentInv.setAttribute("selection_back", (Consumer<Player>) pl -> pl.openInventory(parentInv));

        return parentInv;
    }

    @NotNull
    public static StarInventory createEmotesInventory(@NotNull Player p) {
        StarInventory inv = genGUI(54, get("menu.cosmetics.choose.emote"));

        Map<Rarity, List<ItemStack>> itemsMap = new HashMap<>();
        for (Emote e : Emote.values()) {
            Rarity r = e.getRarity();
            List<ItemStack> items = itemsMap.containsKey(r) ? itemsMap.get(r) : new ArrayList<>();
            items.add(toItemStack(p, e));
            itemsMap.put(r, items);
        }

        itemsMap.values().forEach(l -> l.sort(Comparator.comparing(i ->
                i.hasItemMeta() ? (i.getItemMeta().hasDisplayName() ? i.getItemMeta().getDisplayName() : "") : "")
        ));

        List<ItemStack> items = new ArrayList<>();

        // Rarities are ordered from lowest to highest
        for (Rarity r : Rarity.values()) if (itemsMap.containsKey(r)) items.addAll(itemsMap.get(r));

        Map<Integer, List<ItemStack>> rows = Generator.generateRows(items);
        inv.setAttribute("rows", rows);
        setRows(inv, rows);

        setScrolls(inv);
        setBack(inv, cw::cosmetics);

        return inv;
    }

}
