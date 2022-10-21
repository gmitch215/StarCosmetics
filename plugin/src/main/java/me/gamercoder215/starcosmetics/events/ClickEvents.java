package me.gamercoder215.starcosmetics.events;

import com.google.common.collect.ImmutableMap;
import me.gamercoder215.starcosmetics.StarCosmetics;
import me.gamercoder215.starcosmetics.util.inventory.ItemBuilder;
import me.gamercoder215.starcosmetics.util.inventory.StarInventory;
import me.gamercoder215.starcosmetics.wrapper.nbt.NBTWrapper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Map;
import java.util.function.Consumer;

public final class ClickEvents implements Listener {

    private final StarCosmetics plugin;

    public ClickEvents(StarCosmetics plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    private static final Map<String, Consumer<InventoryClickEvent>> CLICK_ITEMS = ImmutableMap.<String, Consumer<InventoryClickEvent>>builder()

            .build();

    @EventHandler
    public void click(InventoryClickEvent e) {
        Inventory inv = e.getClickedInventory();
        if (!(inv instanceof StarInventory)) return;
        if (!(e.getWhoClicked() instanceof Player)) return;
        if (e.getCurrentItem() == null) return;
        ItemStack item = e.getCurrentItem();
        Player p = (Player) e.getWhoClicked();

        NBTWrapper w = NBTWrapper.of(item);

        if (w.hasID() && !e.isCancelled()) {
            String id = w.getID();
            if (CLICK_ITEMS.containsKey(id)) {
                e.setCancelled(true);
                CLICK_ITEMS.get(id).accept(e);
            }
        }
    }

    @EventHandler
    public void drag(InventoryDragEvent e) {
        Inventory inv = e.getView().getTopInventory();
        if (inv == null) return;
        if (inv instanceof PlayerInventory) return;

        if (inv instanceof StarInventory) {
            StarInventory sinv = (StarInventory) inv;
            if (sinv.hasAttribute("cancel")) e.setCancelled(true);
        }

        for (ItemStack item : e.getNewItems().values()) {
            if (item == null) return;
            if (item.isSimilar(ItemBuilder.GUI_BACKGROUND)) e.setCancelled(true);
        }
    }

}
