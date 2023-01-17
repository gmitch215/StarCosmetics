package me.gamercoder215.starcosmetics.events;

import me.gamercoder215.starcosmetics.api.StarConfig;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class CompletionEvents1_12_R1 implements Listener {

    private final Plugin plugin;

    public CompletionEvents1_12_R1() {
        this.plugin = StarConfig.getPlugin();
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }


}
