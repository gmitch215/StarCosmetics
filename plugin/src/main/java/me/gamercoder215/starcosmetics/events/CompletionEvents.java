package me.gamercoder215.starcosmetics.events;

import me.gamercoder215.starcosmetics.StarCosmetics;
import me.gamercoder215.starcosmetics.api.player.PlayerCompletion;
import me.gamercoder215.starcosmetics.api.player.StarPlayer;
import me.gamercoder215.starcosmetics.util.StarRunnable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import static me.gamercoder215.starcosmetics.api.player.PlayerCompletion.NETHER_ROOF;
import static me.gamercoder215.starcosmetics.api.player.PlayerCompletion.SONIC_BOOM_DEATH;

public final class CompletionEvents implements Listener {

    private final StarCosmetics plugin;

    public CompletionEvents(StarCosmetics plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onMoveAsync(PlayerMoveEvent e) {
        StarRunnable.async(() -> {
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
                default:
                    break;
            }
        });
    }

    @EventHandler
    public void onDeathAsync(PlayerDeathEvent e) {
        StarRunnable.async(() -> {
            Player p = e.getEntity();
            StarPlayer sp = new StarPlayer(p);

            if (sp.hasCompleted(SONIC_BOOM_DEATH)) return;
            if (p.getLastDamageCause().getCause().name().equals("SONIC_BOOM"))
                sp.setCompleted(SONIC_BOOM_DEATH, true);
        });
    }

    @EventHandler
    public void onDamageAsync(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        Player p = (Player) e.getEntity();

        StarRunnable.async(() -> {
            StarPlayer sp = new StarPlayer(p);
            if (sp.hasCompleted(PlayerCompletion.LIGHTNING)) return;
            if (e.getCause() == EntityDamageEvent.DamageCause.LIGHTNING) sp.setCompleted(PlayerCompletion.LIGHTNING, true);
        });
    }

}
