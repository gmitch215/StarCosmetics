package me.gamercoder215.starcosmetics.events;

import me.gamercoder215.starcosmetics.StarCosmetics;
import me.gamercoder215.starcosmetics.api.cosmetics.registry.CosmeticLocation;
import me.gamercoder215.starcosmetics.api.cosmetics.trail.Trail;
import me.gamercoder215.starcosmetics.api.cosmetics.trail.TrailType;
import me.gamercoder215.starcosmetics.api.player.StarPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public final class CosmeticEvents implements Listener {
    
    private final StarCosmetics plugin;

    public CosmeticEvents(StarCosmetics plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onShoot(ProjectileLaunchEvent e) {
        Projectile en = e.getEntity();
        if (!(en.getShooter() instanceof Player)) return;
        Player p = (Player) en.getShooter();
        StarPlayer sp = new StarPlayer(p);

        CosmeticLocation<?> proj = sp.getSelectedTrail(TrailType.PROJECTILE);
        if (proj != null) ((Trail<?>) proj.getParent()).run(en, proj);

        CosmeticLocation<?> sProj = sp.getSelectedTrail(TrailType.PROJECTILE_SOUND);
        if (sProj != null) ((Trail<?>) sProj.getParent()).run(en, sProj);
    }

}
