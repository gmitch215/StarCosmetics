package me.gamercoder215.starcosmetics.util.inventory;

import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.api.player.SoundEventSelection;
import me.gamercoder215.starcosmetics.api.player.StarPlayer;
import me.gamercoder215.starcosmetics.util.Generator;
import me.gamercoder215.starcosmetics.util.StarMaterial;
import me.gamercoder215.starcosmetics.util.StarSound;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.ChatPaginator;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static me.gamercoder215.starcosmetics.util.inventory.ItemBuilder.SAVE;
import static me.gamercoder215.starcosmetics.wrapper.Wrapper.get;
import static me.gamercoder215.starcosmetics.wrapper.Wrapper.getWithArgs;
import static me.gamercoder215.starcosmetics.wrapper.nbt.NBTWrapper.builder;

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
            ItemStack item = builder(StarInventoryUtil.toMaterial(s),
                    meta -> {
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
                    },
                    nbt -> nbt.set("sound", s.name())
            );
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
            ItemStack item = builder(StarInventoryUtil.toMaterial(clazz),
                    meta -> {
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
                    }, nbt -> nbt.set("event", clazz)
            );
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

        ItemStack confirm = builder(StarMaterial.LIME_WOOL.findStack(),
                meta -> meta.setDisplayName(ChatColor.GREEN + get("menu.confirm")),
                nbt -> nbt.set("item", "confirm")
        );
        inv.setItem(11, confirm);

        ItemStack cancel = builder(StarMaterial.RED_WOOL.findStack(),
                meta -> meta.setDisplayName(ChatColor.RED + get("menu.cancel")),
                nbt -> nbt.set("item", "cancel")
        );
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

        ItemStack pitch = builder(Material.NOTE_BLOCK,
            meta -> {
                meta.setDisplayName(ChatColor.YELLOW + get("constants.pitch"));
                meta.setLore(Arrays.asList(
                        ChatColor.GREEN + "1.0",
                        " ",
                        ChatColor.YELLOW + get("constants.menu.right_click_up"),
                        ChatColor.YELLOW + get("constants.menu.left_click_down")
                ));
            },
            nbt -> {
                nbt.set("item", "pitch");
                nbt.set("value", 1.0f);
                nbt.set("min", 0.0f);
                nbt.set("max", 2.0f);
            }
        );
        inv.setItem(11, pitch);

        ItemStack volume = builder(Material.JUKEBOX,
            meta -> {
                meta.setDisplayName(ChatColor.YELLOW + get("constants.volume"));
                meta.setLore(Arrays.asList(
                        ChatColor.GREEN + "2.0",
                        " ",
                        ChatColor.YELLOW + get("constants.menu.right_click_up"),
                        ChatColor.YELLOW + get("constants.menu.left_click_down")
                ));
            },
            nbt -> {
                nbt.set("item", "volume");
                nbt.set("value", 2.0f);
                nbt.set("max", 10.0f);
                nbt.set("min", 0.1f);
            }
        );
        inv.setItem(15, volume);

        ItemStack test = builder(StarInventoryUtil.toMaterial(sound),
                meta -> meta.setDisplayName(ChatColor.YELLOW + get("constants.test_sound")),
                nbt -> nbt.set("item", "test")
        );
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

        ItemStack sound = builder(Material.NOTE_BLOCK,
                meta -> meta.setDisplayName(ChatColor.YELLOW + get("constants.menu.edit.sound")),
                nbt -> nbt.set("item", "sound")
        );
        inv.setItem(11, sound);

        ItemStack pitchVolume = builder(Material.NOTE_BLOCK,
                meta -> {
                    meta.setDisplayName(ChatColor.YELLOW + get("constants.menu.edit.pitch_volume"));
                    meta.setLore(Arrays.asList(
                            ChatColor.GREEN + getWithArgs("constants.menu.pitch", initial.getPitch()),
                            ChatColor.GREEN + getWithArgs("constants.menu.volume", initial.getVolume())
                    ));
                },
                nbt -> nbt.set("item", "pitch_volume")
        );
        inv.setItem(13, pitchVolume);

        ItemStack event = builder(Material.BOOK,
                meta -> meta.setDisplayName(ChatColor.YELLOW + get("constants.menu.edit.event")),
                nbt -> nbt.set("item", "event")
        );
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
