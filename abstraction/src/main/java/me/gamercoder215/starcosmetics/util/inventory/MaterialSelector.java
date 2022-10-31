package me.gamercoder215.starcosmetics.util.inventory;

import me.gamercoder215.starcosmetics.api.player.cosmetics.SoundEventSelection;
import me.gamercoder215.starcosmetics.util.Constants;
import me.gamercoder215.starcosmetics.wrapper.Wrapper;
import me.gamercoder215.starcosmetics.wrapper.nbt.NBTWrapper;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
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

public final class MaterialSelector {

    private static final Wrapper w = Constants.w;
    
    private MaterialSelector() { throw new UnsupportedOperationException(); }

    public static void chooseSound(@NotNull Player p, Consumer<Sound> clickAction) {
        StarInventory inv = w.createInventory("choose:sound_inv", 54, get("gui.choose.sound"));
        inv.setCancelled();
        inv.setAttribute("chosen_action", clickAction);

        List<ItemStack> items = new ArrayList<>();
        for (Sound s : Sound.values()) {
            Material m = StarInventoryUtil.toMaterial(s);
            ItemStack item = new ItemStack(m);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.YELLOW + StarInventoryUtil.getFriendlyName(s));

            List<String> lore = new ArrayList<>();
            lore.add(" ");
            lore.addAll(Arrays.stream(ChatPaginator.wordWrap(
                    get("gui.choose.sound_desc." + s.name().toLowerCase(), "null"), 30
            )).map(str -> ChatColor.GRAY + str).collect(Collectors.toList()));
            lore.add(" ");
            if (!lore.get(1).equalsIgnoreCase("null")) meta.setLore(lore);

            item.setItemMeta(meta);

            NBTWrapper nbt = NBTWrapper.of(item);
            nbt.set("sound", s.name());
            item = nbt.getItem();

            items.add(item);
        }

        Map<Integer, List<ItemStack>> rows = StarInventoryUtil.generateRows(items);
        inv.setAttribute("rows", rows);
        StarInventoryUtil.setRows(inv, rows);
        StarInventoryUtil.setScrolls(inv);

        p.openInventory(inv);
    }

    public static void chooseEvent(@NotNull Player p, Consumer<Class<? extends Event>> clickAction) {
        StarInventory inv = w.createInventory("choose:event_inv", 54, get("gui.choose.event"));
        inv.setCancelled();
        inv.setAttribute("chosen_action", clickAction);

        List<ItemStack> items = new ArrayList<>();
        for (Class<? extends Event> clazz : SoundEventSelection.AVAILABLE_EVENTS) {
            Material m = StarInventoryUtil.toMaterial(clazz);
            ItemStack item = new ItemStack(m);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.YELLOW + clazz.getSimpleName());

            List<String> lore = new ArrayList<>();
            lore.add(" ");
            lore.addAll(Arrays.stream(ChatPaginator.wordWrap(
                get("gui.choose.event_desc." + clazz.getSimpleName(), "null"), 30
            )).map(str -> ChatColor.GRAY + str).collect(Collectors.toList()));
            lore.add(" ");
            if (!lore.get(1).equalsIgnoreCase("null")) meta.setLore(lore);

            item.setItemMeta(meta);

            NBTWrapper nbt = NBTWrapper.of(item);
            nbt.set("event", clazz);
            item = nbt.getItem();

            items.add(item);
        }

        Map<Integer, List<ItemStack>> rows = StarInventoryUtil.generateRows(items);
        inv.setAttribute("rows", rows);
        StarInventoryUtil.setRows(inv, rows);
        StarInventoryUtil.setScrolls(inv);

        p.openInventory(inv);
    }

}
