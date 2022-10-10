package me.gamercoder215.starcosmetics.events;

import me.gamercoder215.starcosmetics.StarCosmetics;
import me.gamercoder215.starcosmetics.api.player.StarPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import static me.gamercoder215.starcosmetics.api.player.PlayerCompletion.NETHER_ROOF;

public class CompletionEvents implements Listener {

    private final StarCosmetics plugin;

    public CompletionEvents(StarCosmetics plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onMoveAsync(PlayerMoveEvent e) {
        new BukkitRunnable() {

            @Override
            public void run() {
                Player p = e.getPlayer();
                StarPlayer sp = new StarPlayer(p);
                Location to = e.getTo();
                switch (to.getWorld().getEnvironment()) {
                    case NORMAL: {

                        break;
                    }
                    case NETHER: {
                        if (sp.hasCompleted(NETHER_ROOF)) return;
                        if (to.getBlockY() >= 128) sp.setCompleted(NETHER_ROOF, true);

                        break;
                    }
                    default: break;
                }
            }
        }.runTaskAsynchronously(plugin);
    }

}
