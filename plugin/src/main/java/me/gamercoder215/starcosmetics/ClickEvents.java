package me.gamercoder215.starcosmetics;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ClickEvents implements Listener {

    // private final StarCosmetics plugin;

    public ClickEvents(StarCosmetics plugin) {
        // this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        
    }

}
