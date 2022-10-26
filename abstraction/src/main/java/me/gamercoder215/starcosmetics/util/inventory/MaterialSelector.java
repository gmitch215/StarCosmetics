package me.gamercoder215.starcosmetics.util.inventory;

import com.google.common.collect.ImmutableList;

import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.util.Constants;
import me.gamercoder215.starcosmetics.util.StarMaterial;
import me.gamercoder215.starcosmetics.wrapper.Wrapper;
import me.gamercoder215.starcosmetics.wrapper.nbt.NBTWrapper;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.SheepDyeWoolEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.ChatPaginator;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static me.gamercoder215.starcosmetics.wrapper.Wrapper.*;

public final class MaterialSelector {

    private static final Wrapper w = Constants.w;
    
    private MaterialSelector() { throw new UnsupportedOperationException(); }

    @NotNull
    public static Material toMaterial(@NotNull Class<? extends Event> eventClass) {
        if (Modifier.isAbstract(eventClass.getModifiers())) throw new IllegalArgumentException("Using Abstract Class");

        String n = eventClass.getSimpleName().toLowerCase();
        Material chosen = null;

        if (n.contains("player")) chosen = Material.IRON_SWORD;
        if (chosen == null && n.contains("block")) chosen = Material.DIRT;
        if (chosen == null && n.contains("server")) chosen = StarMaterial.OBSERVER.find();
        if (chosen == null && n.contains("inventory")) chosen = StarMaterial.CRAFTING_TABLE.find();
        if (chosen == null && n.contains("weather")) chosen = Material.BUCKET;
        if (chosen == null && n.contains("vehicle")) chosen = Material.MINECART;

        switch (n) {
            case "asyncplayerchatevent": return StarMaterial.COMMAND_BLOCK.find();
            case "playerjoinevent": return StarMaterial.GRASS_BLOCK.find();
            case "playerrespawnevent": return Material.BEACON;
        }

        if (chosen == null) chosen = Material.REDSTONE;
        return chosen;
    }

    public static final List<Class<? extends Event>> PLAYER_CLASSES = ImmutableList.<Class<? extends Event>>builder()
            .add(AsyncPlayerChatEvent.class)
            .add(BlockBreakEvent.class)
            .add(BlockPlaceEvent.class)
            .add(FurnaceExtractEvent.class)
            .add(InventoryOpenEvent.class)
            .add(InventoryCloseEvent.class)
            .add(optional("player.PlayerAdvancementDoneEvent"))
            .add(PlayerBedEnterEvent.class)
            .add(PlayerBedLeaveEvent.class)
            .add(PlayerChangedWorldEvent.class)
            .add(PlayerDeathEvent.class)
            .add(PlayerEditBookEvent.class)
            .add(PlayerEggThrowEvent.class)
            .add(PlayerExpChangeEvent.class)
            .add(PlayerFishEvent.class)
            .add(PlayerJoinEvent.class)
            .add(PlayerRespawnEvent.class)
            .add(optional("player.PlayerRiptideEvent"))
            .add(PlayerUnleashEntityEvent.class)
            .add(SheepDyeWoolEvent.class)
            .add(SignChangeEvent.class)
            .build()

            .stream()
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

    private static Class<? extends Event> optional(String name) {
        try {
            return Class.forName("org.bukkit.event." + name)
                    .asSubclass(Event.class);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    @NotNull
    public static Material toMaterial(@NotNull Sound s) {
        String n = s.name();
        Material chosen = null;

        for (Material m : Arrays.stream(Material.values()).filter(w::isItem).collect(Collectors.toList())) {
            if (n.equals(m.name())) return m; // Takes Absolute Priority
            
            if (n.contains(m.name())) {
                chosen = m;
                break;
            }
        }
        
        if (chosen == null && n.startsWith("ENTITY")) chosen = StarMaterial.POPPY.find();   
        if (chosen == null && n.contains("GENERIC")) chosen = Material.LEATHER_CHESTPLATE;
        if (chosen == null && n.contains("VILLAGER")) chosen = Material.EMERALD;

        if (n.startsWith("MUSIC")) if (n.contains("OVERWORLD")) chosen = StarMaterial.GRASS_BLOCK.find();
        else if (n.contains("NETHER")) chosen = Material.NETHERRACK;
        else chosen = Material.NOTE_BLOCK;

        if (chosen == null && n.startsWith("RECORD")) chosen = Material.JUKEBOX;
        if (chosen == null && n.startsWith("UI")) chosen = Material.REDSTONE_BLOCK;
        if (chosen == null && n.startsWith("WEATHER")) chosen = Material.BUCKET;

        return chosen == null ? Material.STONE : chosen;
    }

    public static void chooseEvent(@NotNull Player p, Consumer<Class<? extends Event>> clickAction) {
        StarInventory inv = w.createInventory("choose:event_inv", 54, get("gui.choose.event"));
        inv.setAttribute("chosen_action", clickAction);

        List<ItemStack> items = new ArrayList<>();
        for (Class<? extends Event> clazz : PLAYER_CLASSES) {
            Material m = toMaterial(clazz);
            ItemStack item = new ItemStack(m);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.YELLOW + clazz.getSimpleName());
            
            List<String> lore = new ArrayList<>();
            lore.add(" ");
            lore.addAll(Arrays.stream(ChatPaginator.wordWrap(
                get("gui.choose.event_desc." + clazz.getSimpleName(), ""), 30
            )).map(s -> ChatColor.GRAY + s).collect(Collectors.toList()));
            lore.add(" ");
            meta.setLore(lore);

            item.setItemMeta(meta);

            NBTWrapper nbt = NBTWrapper.of(item);
            nbt.setID("choose:event");
            nbt.setNBT("event", clazz);
            item = nbt.getItem();

            items.add(item);
        }

        Map<Integer, List<ItemStack>> rows = generateRows(items);
        inv.setAttribute("rows", rows);
        setRows(inv, rows);

        p.openInventory(inv);
    }

    public static ItemStack getHead(String key) {
        try {
            Properties p = new Properties();
            p.load(MaterialSelector.class.getResourceAsStream("/heads.properties"));

            String value = p.getProperty(key);
            if (value == null) return null;

            // TODO Create Texture Parser
        } catch (IOException e) {
            StarConfig.print(e);
        }

        return null;
    } 

    @NotNull
    public static Map<Integer, List<ItemStack>> generateRows(ItemStack... items) {
        return generateRows(Arrays.asList(items));
    }

    public static void setRows(Inventory inv, Map<Integer, List<ItemStack>> rows) {
        int limit = (inv.getSize() - 18) / 9;
        if (limit < 1) return;

        for (int i = 0; i < Math.min(rows.size(), limit); i++) {
            List<ItemStack> row = rows.get(i);
            if (row.isEmpty() || row.size() > 7) throw new IllegalArgumentException("Unexpected row size: " + row.size() + "(" + i + ")");
            
            for (int j = 0; j < row.size(); j++) {
                int slot = ((i + 1) * 9) + j + 1;
                inv.setItem(slot, row.get(j));
            }
        }
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
