package me.gamercoder215.starcosmetics.api.cosmetics;

import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.api.cosmetics.particle.ParticleShape;
import me.gamercoder215.starcosmetics.api.cosmetics.particle.ParticleSize;
import me.gamercoder215.starcosmetics.api.cosmetics.registry.CosmeticLocation;
import me.gamercoder215.starcosmetics.util.Constants;
import me.gamercoder215.starcosmetics.util.selection.ParticleSelection;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;

public enum BaseShape implements ParticleShape {
    
    SMALL_RING((l, p) -> circle(l, p, 35, 4)),

    SMALL_DETAILED_RING((l, p) -> {
        circle(l, p, 50, 5);
        circle(l, p, 35, 3);
    }),

    LARGE_RING((l, p) -> circle(l, p, 50, 7)),

    LARGE_DETAILED_RING((l, p) -> {
        circle(l, p, 60, 10);
        circle(l, p, 45, 7);
        circle(l, p, 20, 2);
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
    private final ParticleSize size;

    BaseShape(BiConsumer<Location, Particle> runnable) {
        this.particle = runnable;
        this.size = ParticleSize.valueOf(name());

        Constants.PARENTS.add(this);
    }

    @Override
    public String getNamespace() {
        return "shape/" + name().toLowerCase();
    }

    @Override
    public String getDisplayName() {
        return StarConfig.getConfig().get("menu.cosmetics.shape");
    }

    @Override
    public Material getIcon() {
        return Material.BEACON;
    }

    @Override
    @NotNull
    public ParticleSize getSize() {
        return size;
    }

    @Override
    public void run(@NotNull Location l, @NotNull CosmeticLocation<?> location) throws IllegalArgumentException {
        if (!(location instanceof ParticleSelection)) return;
        ParticleSelection sel = (ParticleSelection) location;
        particle.accept(l, sel.getInput());
    }
}
