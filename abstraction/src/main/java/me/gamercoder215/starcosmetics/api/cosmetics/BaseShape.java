package me.gamercoder215.starcosmetics.api.cosmetics;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;

import java.util.function.BiConsumer;

public enum BaseShape implements BiConsumer<Location, Particle>, ParticleShape {
    
    SMALL_RING((l, p) -> circle(l, p, 10, 4)),

    SMALL_DETAILED_RING((l, p) -> {
        circle(l, p, 20, 5);
        circle(l, p, 10, 3);
    }),

    LARGE_RING((l, p) -> circle(l, p, 20, 7)),

    LARGE_DETAILED_RING((l, p) -> {
        circle(l, p, 40, 10);
        circle(l, p, 20, 7);
    }),
    ;

    private static void circle(Location l, Particle p, int points, double radius) {
        for (int i = 0; i < points; i++) {
            double angle = 2 * Math.PI * i / points;
            l.add(radius * Math.sin(angle), 0, radius * Math.cos(angle));
            l.getWorld().spawnParticle(p, l, 1, 0, 0, 0, 0);
            l.subtract(radius * Math.sin(angle), 0, radius * Math.cos(angle));
        }
    }


    private final BiConsumer<Location, Particle> particle;

    BaseShape(BiConsumer<Location, Particle> runnable) {
        this.particle = runnable;
    }

    @Override
    public void accept(Location l, Particle t) {
        particle.accept(l, t);
    }

    @Override
    public String getNamespace() {
        return "particle_shapes:" + name().toLowerCase();
    }

    @Override
    public String getDisplayKey() {
        return "cosmetics.particle_shapes." + name().toLowerCase();
    }

    @Override
    public Material getIcon() {
        return Material.BEACON;
    }
}
