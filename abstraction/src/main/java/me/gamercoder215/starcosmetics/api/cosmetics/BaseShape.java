package me.gamercoder215.starcosmetics.api.cosmetics;

import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.api.cosmetics.particle.ParticleShape;
import me.gamercoder215.starcosmetics.api.cosmetics.particle.ParticleSize;
import me.gamercoder215.starcosmetics.util.Constants;
import me.gamercoder215.starcosmetics.util.selection.ParticleSelection;
import me.gamercoder215.starcosmetics.wrapper.DataWrapper;
import me.gamercoder215.starcosmetics.wrapper.Wrapper;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;

public final class BaseShape<T> implements ParticleShape {

    private static final DataWrapper dw = Wrapper.getDataWrapper();
    
    public static final BaseShape<Object> SMALL_RING = new BaseShape<>("small_ring", (l, o) -> {
        if (o instanceof Particle) {
            Particle p = (Particle) o;
            circle(l, p, 35, 4);
        }

        if (o instanceof Material) {
            Material m = (Material) o;
            if (m.isBlock()) circle(l, m, 35, 4);
            else {
                ItemStack item = new ItemStack(m);
                circle(l, item, 35, 4);
            }
        }

        if (o instanceof ItemStack) {
            ItemStack i = (ItemStack) o;
            circle(l, i, 35, 4);
        }
    });

    public static final BaseShape<Object> SMALL_DETAILED_RING = new BaseShape<>("small_detailed_ring", (l, o) -> {
        if (o instanceof Particle) {
            Particle p = (Particle) o;
            circle(l, p, 50, 5);
            circle(l, p, 35, 3);
        }

        if (o instanceof Material) {
            Material m = (Material) o;
            if (m.isBlock()) {
                circle(l, m, 50, 5);
                circle(l, m, 35, 3);
            }
            else {
                ItemStack item = new ItemStack(m);
                circle(l, item, 50, 5);
                circle(l, item, 35, 3);
            }
        }

        if (o instanceof ItemStack) {
            ItemStack i = (ItemStack) o;
            circle(l, i, 50, 5);
            circle(l, i, 35, 3);
        }
    });

    public static final BaseShape<Object> LARGE_RING = new BaseShape<>("large_ring", (l, o) -> {
        if (o instanceof Particle) {
            Particle p = (Particle) o;
            circle(l, p, 50, 5);
        }

        if (o instanceof Material) {
            Material m = (Material) o;
            if (m.isBlock()) circle(l, m, 50, 5);
            else {
                ItemStack item = new ItemStack(m);
                circle(l, item, 50, 5);
            }
        }

        if (o instanceof ItemStack) {
            ItemStack i = (ItemStack) o;
            circle(l, i, 50, 5);
        }
    });

    public static final BaseShape<Object> LARGE_DETAILED_RING = new BaseShape<>("large_detailed_ring", (l, o) -> {
        if (o instanceof Particle) {
            Particle p = (Particle) o;
            circle(l, p, 60, 10);
            circle(l, p, 45, 7);
            circle(l, p, 20, 2);
        }

        if (o instanceof Material) {
            Material m = (Material) o;
            if (m.isBlock()) {
                circle(l, m, 60, 10);
                circle(l, m, 45, 7);
                circle(l, m, 20, 2);
            } else {
                ItemStack item = new ItemStack(m);
                circle(l, item, 60, 10);
                circle(l, item, 45, 7);
                circle(l, item, 20, 2);
            }
        }

        if (o instanceof ItemStack) {
            ItemStack i = (ItemStack) o;
            circle(l, i, 60, 10);
            circle(l, i, 45, 7);
            circle(l, i, 20, 2);
        }

    });

    public static void circle(Location l, Particle p, int points, double radius) {
        for (int i = 0; i < points; i++) {
            double angle = 2 * Math.PI * i / points;
            l.add(radius * Math.sin(angle), 0, radius * Math.cos(angle));
            l.getWorld().spawnParticle(p, l, 1, 0, 0, 0, 0);
            l.subtract(radius * Math.sin(angle), 0, radius * Math.cos(angle));
        }
    }

    public static void circle(Location l, Material m, int points, double radius) {
        for (int i = 0; i < points; i++) {
            double angle = 2 * Math.PI * i / points;
            l.add(radius * Math.sin(angle), 0, radius * Math.cos(angle));
            dw.blockDataParticle(Particle.BLOCK_CRACK, l, 1, m);
            l.subtract(radius * Math.sin(angle), 0, radius * Math.cos(angle));
        }
    }

    public static void circle(Location l, ItemStack item, int points, double radius) {
        for (int i = 0; i < points; i++) {
            double angle = 2 * Math.PI * i / points;
            l.add(radius * Math.sin(angle), 0, radius * Math.cos(angle));
            l.getWorld().spawnParticle(Particle.ITEM_CRACK, l, 1, 0, 0, 0, 0, item);
            l.subtract(radius * Math.sin(angle), 0, radius * Math.cos(angle));
        }
    }


    private final BiConsumer<Location, T> particle;
    private final ParticleSize size;
    private final String name;

    private BaseShape(String name, BiConsumer<Location, T> runnable) {
        this.name = name;
        this.particle = runnable;
        this.size = ParticleSize.valueOf(name.toUpperCase());

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
    @SuppressWarnings("unchecked")
    public void run(@NotNull Location l, @NotNull CosmeticLocation<?> location) throws IllegalArgumentException {
        if (!(location instanceof ParticleSelection)) return;
        ParticleSelection sel = (ParticleSelection) location;
        particle.accept(l, (T) sel.getInput());
    }
}
