package me.gamercoder215.starcosmetics.api.cosmetics;

import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.api.cosmetics.particle.ParticleShape;
import me.gamercoder215.starcosmetics.api.cosmetics.particle.ParticleSize;
import me.gamercoder215.starcosmetics.util.Constants;
import me.gamercoder215.starcosmetics.util.selection.ParticleSelection;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;

import static me.gamercoder215.starcosmetics.util.Constants.dw;

public final class BaseShape implements ParticleShape {

    public static final BaseShape ALL = new BaseShape("all", (l, o) -> {});

    // Rings

    public static final BaseShape SMALL_RING = new BaseShape("small_ring", (l, o) -> {
        if (o instanceof Material) {
            Material m = (Material) o;
            Object input = m.isBlock() ? m : new ItemStack(m);
            circle(l, input, 35, 4);
        } else
            circle(l, o, 35, 4);
    });

    public static final BaseShape SMALL_DETAILED_RING = new BaseShape("small_detailed_ring", (l, o) -> {
        if (o instanceof Material) {
            Material m = (Material) o;
            Object input = m.isBlock() ? m : new ItemStack(m);
            circle(l, input, 50, 5);
            circle(l, input, 35, 3);
        } else {
            circle(l, o, 50, 5);
            circle(l, o, 35, 3);
        }
    });

    public static final BaseShape LARGE_RING = new BaseShape("large_ring", (l, o) -> {
        if (o instanceof Material) {
            Material m = (Material) o;
            Object input = m.isBlock() ? m : new ItemStack(m);
            circle(l, input, 50, 6);
        } else
            circle(l, o, 50, 6);
    });

    public static final BaseShape LARGE_DETAILED_RING = new BaseShape("large_detailed_ring", (l, o) -> {
        if (o instanceof Material) {
            Material m = (Material) o;
            Object input = m.isBlock() ? m : new ItemStack(m);

            circle(l, input, 60, 10);
            circle(l, input, 45, 7);
            circle(l, input, 20, 2);
        } else {
            circle(l, o, 60, 10);
            circle(l, o, 45, 7);
            circle(l, o, 20, 2);
        }
    });

    // Trianges

    public static final BaseShape SMALL_TRIANGLE = new BaseShape("small_triangle", (l, o) -> {
        if (o instanceof Material) {
            Material m = (Material) o;
            Object input = m.isBlock() ? m : new ItemStack(m);
            polygon(l, input, 3, 3);
        } else
            polygon(l, o, 3, 3);
    });

    public static final BaseShape MEDIUM_TRIANGLE = new BaseShape("medium_triangle", (l, o) -> {
        if (o instanceof Material) {
            Material m = (Material) o;
            Object input = m.isBlock() ? m : new ItemStack(m);
            polygon(l, input, 3, 5.5);
        } else
            polygon(l, o, 3, 5.5);
    });

    public static final BaseShape LARGE_TRIANGLE = new BaseShape("large_triangle", (l, o) -> {
        if (o instanceof Material) {
            Material m = (Material) o;
            Object input = m.isBlock() ? m : new ItemStack(m);
            polygon(l, input, 3, 8);
        } else
            polygon(l, o, 3, 8);
    });

    public static final BaseShape LARGE_DETAILED_TRIANGLE = new BaseShape("large_detailed_triangle", (l, o) -> {
        if (o instanceof Material) {
            Material m = (Material) o;
            Object input = m.isBlock() ? m : new ItemStack(m);
            polygon(l, input, 3, 8);
            polygon(l, input, 3, 6);
            polygon(l, input, 3, 4);
            polygon(l, input, 3, 2);
        } else {
            polygon(l, o, 3, 8);
            polygon(l, o, 3, 6);
            polygon(l, o, 3, 4);
            polygon(l, o, 3, 2);
        }
    });

    // Squares

    public static final BaseShape SMALL_SQUARE = new BaseShape("small_square", (l, o) -> {
        if (o instanceof Material) {
            Material m = (Material) o;
            Object input = m.isBlock() ? m : new ItemStack(m);
            polygon(l, input, 4, 2.5);
        } else
            polygon(l, o, 4, 2.5);
    });

    public static final BaseShape LARGE_SQUARE = new BaseShape("large_square", (l, o) -> {
        if (o instanceof Material) {
            Material m = (Material) o;
            Object input = m.isBlock() ? m : new ItemStack(m);
            polygon(l, input, 4, 7);
        } else
            polygon(l, o, 4, 7);
    });

    // Pentagons

    public static final BaseShape PENTAGON = new BaseShape("pentagon", (l, o) -> {
        if (o instanceof Material) {
            Material m = (Material) o;
            Object input = m.isBlock() ? m : new ItemStack(m);
            polygon(l, input, 5, 2.5);
        } else
            polygon(l, o, 5, 2.5);
    });

    public static final BaseShape DETAILED_PENTAGON = new BaseShape("detailed_pentagon", (l, o) -> {
        if (o instanceof Material) {
            Material m = (Material) o;
            Object input = m.isBlock() ? m : new ItemStack(m);

            polygon(l, input, 5, 3);
            polygon(l, input, 5, 2.5);
            polygon(l, input, 5, 2);
            polygon(l, input, 5, 1.5);
        } else {
            polygon(l, o, 5, 3);
            polygon(l, o, 5, 2.5);
            polygon(l, o, 5, 2);
            polygon(l, o, 5, 1.5);
        }
    });

    // Spawn Utilities

    public static void spawn(Location l, Object o) {
        if (o instanceof ItemStack) {
            ItemStack item = (ItemStack) o;
            l.getWorld().spawnParticle(Particle.ITEM_CRACK, l, 1, 0, 0, 0, 0, item);
        } else if (o instanceof Material) {
            Material m = (Material) o;
            dw.blockDataParticle(Particle.BLOCK_CRACK, l, 1, m);
        } else if (o instanceof Particle) {
            Particle p = (Particle) o;
            l.getWorld().spawnParticle(p, l, 1, 0, 0, 0, 0);
        }
    }

    public static void circle(Location l, Object o, int points, double radius) {
        for (int i = 0; i < points; i++) {
            double angle = 2 * Math.PI * i / points;
            l.add(radius * Math.sin(angle), 0, radius * Math.cos(angle));
            spawn(l, o);
            l.subtract(radius * Math.sin(angle), 0, radius * Math.cos(angle));
        }
    }

    public static void polygon(Location l, Object o, int points, double radius) {
        for (int i = 0; i < points; i++) {
            double a = Math.toRadians(360.0 / points * i);
            double a2 = Math.toRadians(360.0 / points * (i + 1));

            double x = Math.cos(a) * radius;
            double z = Math.sin(a) * radius;
            double x2 = Math.cos(a2) * radius;
            double z2 = Math.sin(a2) * radius;

            double dX = x2 - x;
            double dZ = z2 - z;
            double dist = Math.sqrt((dX - x) * (dX - x) + (dZ - z) * (dZ - z)) / radius;

            for (double d = 0; d < (dist - (2.0 - (2 * ((double) points / 10))) ); d += .1) {
                l.add(x + dX * d, 0, z + dZ * d);
                spawn(l, o);
                l.subtract(x + dX * d, 0, z + dZ * d);
            }
        }
    }


    private final BiConsumer<Location, Object> particle;
    private final ParticleSize size;
    private final String name;

    private BaseShape(String name, BiConsumer<Location, Object> runnable) {
        this.name = name;
        this.particle = runnable;
        this.size = name.equalsIgnoreCase("all") ? null : ParticleSize.valueOf(name.toUpperCase());

        Constants.PARENTS.add(this);
    }

    @Override
    public String getNamespace() {
        return "shape/" + name.toLowerCase();
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
