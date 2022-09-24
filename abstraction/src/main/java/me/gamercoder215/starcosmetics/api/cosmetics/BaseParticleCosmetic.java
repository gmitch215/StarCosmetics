package me.gamercoder215.starcosmetics.api.cosmetics;

import org.bukkit.Location;
import org.bukkit.Particle;

import java.util.function.BiConsumer;

public enum BaseParticleCosmetic implements BiConsumer<Location, Particle> {
    
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

    BaseParticleCosmetic(BiConsumer<Location, Particle> runnable) {
        this.particle = runnable;
    }

    @Override
    public void accept(Location l, Particle t) {
        particle.accept(l, t);
    }

}
