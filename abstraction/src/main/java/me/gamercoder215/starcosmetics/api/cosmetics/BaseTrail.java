package me.gamercoder215.starcosmetics.api.cosmetics;

import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.wrapper.Wrapper;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.function.BiConsumer;

public enum BaseTrail implements BiConsumer<Projectile, Object> {
    
    ARROW_TRAIL((p, o) -> {
        Location loc = p.getLocation();

        if (o instanceof Particle) {
            Particle part = (Particle) o;
            new BukkitRunnable() {
                public void run() {
                    if (p.isDead() || !p.isValid()) {
                        cancel();
                        return;
                    }

                    p.getWorld().spawnParticle(part, loc, 1, 0, 0, 0, 0);
                }
            }.runTaskTimer(StarConfig.getPlugin(), 0, 2);
        }

        if (o instanceof Material || o instanceof ItemStack) {
            ItemStack item = o instanceof Material ? new ItemStack((Material) o) : (ItemStack) o;
            new BukkitRunnable() {
                public void run() {
                    if (p.isDead() || !p.isValid()) {
                        cancel();
                        return;
                    }
                    
                    for (Player pl : p.getWorld().getPlayers()) {
                        Wrapper.getWrapper().spawnFakeItem(pl, item, p.getLocation(), 10);
                    }
                }
            }.runTaskTimer(StarConfig.getPlugin(), 0, 2);
        }
    });

    private final BiConsumer<Projectile, Object> trail;

    BaseTrail(BiConsumer<Projectile, Object> runnable) {
        this.trail = runnable;
    }

    @Override
    public void accept(Projectile t, Object u) {
        trail.accept(t, u);
    }

}
