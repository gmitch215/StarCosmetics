package me.gamercoder215.starcosmetics.util.inventory;

import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.api.player.SoundEventSelection;
import me.gamercoder215.starcosmetics.api.player.StarPlayer;
import me.gamercoder215.starcosmetics.util.Generator;
import me.gamercoder215.starcosmetics.util.StarMaterial;
import me.gamercoder215.starcosmetics.util.StarSound;
import me.gamercoder215.starcosmetics.wrapper.nbt.NBTWrapper;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.ChatPaginator;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static me.gamercoder215.starcosmetics.util.inventory.ItemBuilder.SAVE;
import static me.gamercoder215.starcosmetics.wrapper.Wrapper.get;
import static me.gamercoder215.starcosmetics.wrapper.Wrapper.getWithArgs;
import static me.gamercoder215.starcosmetics.wrapper.nbt.NBTWrapper.of;

public final class InventorySelector {

    // private static final Wrapper w = getWrapper();
    
    private InventorySelector() { throw new UnsupportedOperationException(); }

    // Generator Methods

    public static void loadInventories() {
        // CHOOSE_SOUND_INVENTORIES
        Map<String, StarInventory> categories = new HashMap<>();
        Map<String, List<ItemStack>> categoryItems = new HashMap<>();

        Set<Sound> blacklisted = StarConfig.getConfig().getBlacklistedSounds();
        for (Sound s : Sound.values()) {
            if (blacklisted.contains(s)) continue;

            String category = s.name().split("_")[0].toLowerCase();
            if (!categories.containsKey(category)) {
                StarInventory inv = Generator.genGUI("choose:sound_inv", 54, get("menu.cosmetics.choose.sound.item"));
                inv.setCancelled();
                inv.setAttribute("_category", category);

                categories.put(category, inv);
                categoryItems.put(category, new ArrayList<>());
            }

            List<ItemStack> items = categoryItems.get(category);

            Material m = StarInventoryUtil.toMaterial(s);
            ItemStack item = new ItemStack(m);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.GOLD + StarInventoryUtil.getFriendlyName(s));

            List<String> lore = new ArrayList<>();
            String desc = get("menu.cosmetics.choose.sound_desc." + s.name().toLowerCase(), "");

            if (!desc.isEmpty()) {
                lore.add(" ");
                lore.addAll(Arrays.stream(ChatPaginator.wordWrap(desc, 30))
                        .map(str -> ChatColor.GRAY + str)
                        .collect(Collectors.toList()));
                lore.add(" ");
            }

            lore.add(" ");
            lore.add(ChatColor.YELLOW + get("constants.menu.right_click_hear"));
            lore.add(ChatColor.YELLOW + get("constants.menu.left_click_select"));

            meta.setLore(lore);

            meta.addItemFlags(ItemFlag.values());
            item.setItemMeta(meta);

            NBTWrapper nbt = of(item);
            nbt.set("sound", s.name());
            item = nbt.getItem();

            items.add(item);
        }

        categories.values().forEach(inv -> {
            inv.setItem(18, ItemBuilder.STOP_SOUND);

            String category = inv.getAttribute("_category", String.class);

            List<ItemStack> items = categoryItems.get(category);
            Map<Integer, List<ItemStack>> rows = Generator.generateRows(items);
            inv.setAttribute("rows", rows);
            StarInventoryUtil.setRows(inv, rows);
            StarInventoryUtil.setScrolls(inv);
        });

        CHOOSE_SOUND_INVENTORIES = categories;
    }

    public static Map<String, StarInventory> CHOOSE_SOUND_INVENTORIES;

    public static void chooseSound(Player p, Consumer<Sound> clickAction) {
        chooseSound(p, clickAction, null);
    }

    public static void chooseSound(@NotNull Player p, Consumer<Sound> clickAction, Consumer<Player> back) {
        List<StarInventory> pages = CHOOSE_SOUND_INVENTORIES.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());

        StarInventoryUtil.setPages(pages);

        pages.forEach(inv -> {
            inv.setAttribute("chosen_action", clickAction);
            if (back != null) StarInventoryUtil.setBack(inv, back);
        });

        p.openInventory(pages.get(0));
    }

    public static void chooseEvent(@NotNull Player p, Consumer<Class<? extends Event>> clickAction) {
        chooseEvent(p, clickAction, null);
    }

    public static void chooseEvent(@NotNull Player p, Consumer<Class<? extends Event>> clickAction, Consumer<Player> back) {
        StarInventory inv = Generator.genGUI("choose:event_inv", 54, get("menu.cosmetics.choose.event"));
        inv.setCancelled();
        inv.setAttribute("chosen_action", clickAction);

        List<ItemStack> items = new ArrayList<>();
        for (Class<? extends Event> clazz : SoundEventSelection.AVAILABLE_EVENTS) {
            Material m = StarInventoryUtil.toMaterial(clazz);
            ItemStack item = new ItemStack(m);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.YELLOW + clazz.getSimpleName());

            List<String> lore = new ArrayList<>();
            String desc = get("menu.cosmetics.choose.event_desc." + clazz.getSimpleName().toLowerCase(), "");

            if (!desc.isEmpty()) {
                lore.add(" ");
                lore.addAll(Arrays.stream(ChatPaginator.wordWrap(desc, 30))
                        .map(str -> ChatColor.GRAY + str)
                        .collect(Collectors.toList()));
                lore.add(" ");

                meta.setLore(lore);
            }

            meta.addItemFlags(ItemFlag.values());
            item.setItemMeta(meta);

            NBTWrapper nbt = of(item);
            nbt.set("event", clazz);
            item = nbt.getItem();

            items.add(item);
        }

        Map<Integer, List<ItemStack>> rows = Generator.generateRows(items);
        inv.setAttribute("rows", rows);
        StarInventoryUtil.setRows(inv, rows);
        StarInventoryUtil.setScrolls(inv);
        if (back != null) StarInventoryUtil.setBack(inv, back);

        p.openInventory(inv);
    }

    public static StarInventory confirm(@NotNull Player p, @NotNull Runnable confirmR, @NotNull Runnable cancelR) {
        StarInventory inv = Generator.genGUI("confirm_inv", 27, get("menu.are_you_sure"));
        inv.setCancelled();
        inv.setAttribute("confirm_action", confirmR);
        inv.setAttribute("cancel_action", cancelR);

        ItemStack confirm = StarMaterial.LIME_WOOL.findStack();
        ItemMeta cMeta = confirm.getItemMeta();
        cMeta.setDisplayName(ChatColor.GREEN + get("menu.confirm"));
        confirm.setItemMeta(cMeta);

        NBTWrapper confirmNBT = of(confirm);
        confirmNBT.set("item", "confirm");
        confirm = confirmNBT.getItem();
        inv.setItem(11, confirm);

        ItemStack cancel = StarMaterial.RED_WOOL.findStack();
        ItemMeta caMeta = cancel.getItemMeta();
        caMeta.setDisplayName(ChatColor.RED + get("menu.cancel"));
        cancel.setItemMeta(caMeta);

        NBTWrapper cancelNBT = of(cancel);
        cancelNBT.set("item", "cancel");
        cancel = cancelNBT.getItem();
        inv.setItem(15, cancel);

        p.openInventory(inv);
        return inv;
    }

    public static StarInventory confirm(@NotNull Player p, @NotNull Runnable confirmR) {
        return confirm(p, confirmR, () -> {
            p.closeInventory();
            StarSound.BLOCK_NOTE_BLOCK_PLING.playFailure(p);
        });
    }

    public static void choosePitchVolume(@NotNull Player p, @NotNull Sound sound, @NotNull BiConsumer<Float, Float> clickAction, @NotNull Consumer<Player> back) {
        StarInventory inv = Generator.genGUI("choose:pitch_volume_inv", 36, get("menu.cosmetics.choose.pitch_volume"));
        inv.setAttribute("chosen_action", clickAction);
        inv.setAttribute("sound", sound);
        inv.setCancelled();

        ItemStack pitch = new ItemStack(Material.NOTE_BLOCK);
        ItemMeta pMeta = pitch.getItemMeta();
        pMeta.setDisplayName(ChatColor.YELLOW + get("constants.pitch"));
        pMeta.setLore(Arrays.asList(
                ChatColor.GREEN + "1.0",
                " ",
                ChatColor.YELLOW + get("constants.menu.right_click_up"),
                ChatColor.YELLOW + get("constants.menu.left_click_down")
        ));
        pitch.setItemMeta(pMeta);

        NBTWrapper pitchNBT = of(pitch);
        pitchNBT.set("item", "pitch");
        pitchNBT.set("value", 1.0f);
        pitchNBT.set("min", 0.0f);
        pitchNBT.set("max", 2.0f);
        pitch = pitchNBT.getItem();
        inv.setItem(11, pitch);

        ItemStack volume = new ItemStack(Material.JUKEBOX);
        ItemMeta vMeta = volume.getItemMeta();
        vMeta.setDisplayName(ChatColor.YELLOW + get("constants.volume"));
        vMeta.setLore(Arrays.asList(
                ChatColor.GREEN + "2.0",
                " ",
                ChatColor.YELLOW + get("constants.menu.right_click_up"),
                ChatColor.YELLOW + get("constants.menu.left_click_down")
        ));
        volume.setItemMeta(vMeta);

        NBTWrapper volumeNBT = of(volume);
        volumeNBT.set("item", "volume");
        volumeNBT.set("value", 2.0f);
        volumeNBT.set("max", 10.0f);
        volumeNBT.set("min", 0.1f);
        volume = volumeNBT.getItem();
        inv.setItem(15, volume);

        ItemStack test = new ItemStack(StarInventoryUtil.toMaterial(sound));
        ItemMeta tMeta = test.getItemMeta();
        tMeta.setDisplayName(ChatColor.YELLOW + get("constants.test_sound"));
        test.setItemMeta(tMeta);

        NBTWrapper testNBT = of(test);
        testNBT.set("item", "test");
        test = testNBT.getItem();
        inv.setItem(22, test);

        inv.setItem(23, ItemBuilder.STOP_SOUND);

        if (back != null) {
            StarInventoryUtil.setBack(inv, 32, back);
            inv.setItem(30, SAVE);
        } else inv.setItem(31, SAVE);

        p.openInventory(inv);
    }

    public static void editSelection(@NotNull Player p, @NotNull SoundEventSelection initial, @NotNull Consumer<SoundEventSelection> edited) {
        StarInventory inv = Generator.genGUI("edit:soundevent", 27, get("constants.menu.edit.sound_event"));
        inv.setAttribute("current_event", initial);
        inv.setAttribute("chosen_action", edited);

        ItemStack sound = new ItemStack(Material.NOTE_BLOCK);
        ItemMeta sMeta = sound.getItemMeta();
        sMeta.setDisplayName(ChatColor.YELLOW + get("constants.menu.edit.sound"));
        sound.setItemMeta(sMeta);

        NBTWrapper soundNBT = of(sound);
        soundNBT.set("item", "sound");
        sound = soundNBT.getItem();
        inv.setItem(11, sound);

        ItemStack pitchVolume = new ItemStack(Material.NOTE_BLOCK);
        ItemMeta pMeta = pitchVolume.getItemMeta();
        pMeta.setDisplayName(ChatColor.YELLOW + get("constants.menu.edit.pitch_volume"));
        pMeta.setLore(Arrays.asList(
                ChatColor.GREEN + getWithArgs("constants.menu.pitch", initial.getPitch()),
                ChatColor.GREEN + getWithArgs("constants.menu.volume", initial.getVolume())
        ));
        pitchVolume.setItemMeta(pMeta);

        NBTWrapper pitchNBT = of(pitchVolume);
        pitchNBT.set("item", "pitch_volume");
        pitchVolume = pitchNBT.getItem();

        inv.setItem(13, pitchVolume);

        ItemStack event = new ItemStack(Material.BOOK);
        ItemMeta eMeta = event.getItemMeta();
        eMeta.setDisplayName(ChatColor.YELLOW + get("constants.menu.edit.event"));
        event.setItemMeta(eMeta);

        NBTWrapper eNBT = of(event);
        eNBT.set("item", "event");
        event = eNBT.getItem();
        inv.setItem(15, event);

        inv.setItem(22, SAVE);

        p.openInventory(inv);
    }

    public static void createSelection(final Player p) {
        final StarPlayer sp = new StarPlayer(p);
        final Consumer<Player> back = pl -> {
            pl.openInventory(Generator.createSelectionInventory(pl));
            StarSound.BLOCK_NOTE_BLOCK_PLING.playFailure(pl);
        };

        final Runnable backR = () -> back.accept(p);

        StarSound.ENTITY_ARROW_HIT_PLAYER.playFailure(p);
        InventorySelector.chooseEvent(p, event -> {

            StarSound.ENTITY_ARROW_HIT_PLAYER.playFailure(p);
            InventorySelector.chooseSound(p, sound -> {

                p.getWorld().playSound(p.getLocation(), sound, 2F, 1F);
                InventorySelector.choosePitchVolume(p, sound, (pitch, volume) -> {

                    p.getWorld().playSound(p.getLocation(), sound, volume, pitch);
                    InventorySelector.confirm(p, () -> {
                        SoundEventSelection sel = SoundEventSelection.builder()
                                .event(event)
                                .sound(sound, volume, pitch)
                                .player(p)
                                .build();

                        sp.addSelection(sel);
                        StarConfig.updateCache();
                        StarSound.ENTITY_ARROW_HIT_PLAYER.playSuccess(p);

                        p.openInventory(Generator.createSelectionInventory(p));
                    }, backR);
                }, back);
            }, back);
        }, back);
    }

}
