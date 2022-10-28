package me.gamercoder215.starcosmetics.events;

import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.api.player.StarPlayer;
import me.gamercoder215.starcosmetics.api.player.cosmetics.SoundCompletion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CompletionEvents1_12_R1 implements Listener {

    private final Plugin plugin = StarConfig.getPlugin();

    public CompletionEvents1_12_R1() {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    private static final Map<UUID, Integer> SESSION_COUNT = new HashMap<>();

    @EventHandler
    public void onAdvancementDoneAsync(PlayerAdvancementDoneEvent e) {
        new BukkitRunnable() {
            @Override
            public void run() {
                Player p = e.getPlayer();
                StarPlayer sp = new StarPlayer(p);

                SESSION_COUNT.put(p.getUniqueId(), SESSION_COUNT.getOrDefault(p.getUniqueId(), 0) + 1);

                if (SESSION_COUNT.get(p.getUniqueId()) >= 10 && !sp.hasCompleted(SoundCompletion.ADVANCEMENT_COMPLETION)) {
                    SESSION_COUNT.put(p.getUniqueId(), 0);
                    sp.setCompleted(SoundCompletion.ADVANCEMENT_COMPLETION);
                }
            }
        }.runTaskAsynchronously(plugin);
    }


}
