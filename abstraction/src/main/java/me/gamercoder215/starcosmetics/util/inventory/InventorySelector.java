package me.gamercoder215.starcosmetics.util.inventory;

import me.gamercoder215.starcosmetics.api.player.cosmetics.SoundEventSelection;
import me.gamercoder215.starcosmetics.util.Generator;
import me.gamercoder215.starcosmetics.util.StarMaterial;
import me.gamercoder215.starcosmetics.util.StarSound;
import me.gamercoder215.starcosmetics.wrapper.Wrapper;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static me.gamercoder215.starcosmetics.wrapper.Wrapper.get;
import static me.gamercoder215.starcosmetics.wrapper.Wrapper.getWrapper;

public final class InventorySelector {

    private static final Wrapper w = getWrapper();
    
    private InventorySelector() { throw new UnsupportedOperationException(); }

    public static void chooseSound(Player p, Consumer<Sound> clickAction) {
        chooseSound(p, clickAction, null);
    }

    public static void chooseSound(@NotNull Player p, Consumer<Sound> clickAction, Consumer<Player> back) {
        StarInventory inv = Generator.genGUI("choose:sound_inv", 54, get("menu.cosmetics.choose.sound.item"));
        inv.setCancelled();
        inv.setAttribute("chosen_action", clickAction);

        List<ItemStack> items = new ArrayList<>();
        for (Sound s : Sound.values()) {
            Material m = StarInventoryUtil.toMaterial(s);
            ItemStack item = new ItemStack(m);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.YELLOW + StarInventoryUtil.getFriendlyName(s));

            List<String> lore = new ArrayList<>();
            String desc = get("menu.cosmetics.choose.sound_desc." + s.name().toLowerCase(), "");

            if (!desc.isEmpty()) {
                lore.add(" ");
                lore.addAll(Arrays.stream(ChatPaginator.wordWrap(desc, 30))
                        .map(str -> ChatColor.GRAY + str)
                        .collect(Collectors.toList()));
                lore.add(" ");

                meta.setLore(lore);
            }

            item.setItemMeta(meta);

            NBTWrapper nbt = NBTWrapper.of(item);
            nbt.set("sound", s.name());
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

            NBTWrapper nbt = NBTWrapper.of(item);
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

    public static void confirm(@NotNull Player p, @NotNull Runnable confirmR, @NotNull Runnable cancelR) {
        StarInventory inv = Generator.genGUI("confirm_inv", 27, get("menu.are_you_sure"));
        inv.setCancelled();
        inv.setAttribute("confirm_action", confirmR);
        inv.setAttribute("cancel_action", cancelR);

        ItemStack confirm = StarMaterial.LIME_WOOL.findStack();
        ItemMeta cMeta = confirm.getItemMeta();
        cMeta.setDisplayName(ChatColor.GREEN + get("menu.confirm"));
        confirm.setItemMeta(cMeta);

        NBTWrapper confirmNBT = NBTWrapper.of(confirm);
        confirmNBT.set("item", "confirm");
        confirm = confirmNBT.getItem();
        inv.setItem(11, confirm);

        ItemStack cancel = StarMaterial.RED_WOOL.findStack();
        ItemMeta caMeta = cancel.getItemMeta();
        caMeta.setDisplayName(ChatColor.RED + get("menu.cancel"));
        cancel.setItemMeta(caMeta);

        NBTWrapper cancelNBT = NBTWrapper.of(cancel);
        cancelNBT.set("item", "cancel");
        cancel = cancelNBT.getItem();
        inv.setItem(15, cancel);

        p.openInventory(inv);
    }

    public static void confirm(@NotNull Player p, @NotNull Runnable confirmR) {
        confirm(p, confirmR, () -> {
            p.closeInventory();
            StarSound.BLOCK_NOTE_BLOCK_PLING.playFailure(p);
        });
    }

}
