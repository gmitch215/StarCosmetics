package me.gamercoder215.starcosmetics.api.cosmetics;

import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.api.cosmetics.particle.ParticleShape;
import me.gamercoder215.starcosmetics.api.cosmetics.particle.ParticleSize;
import me.gamercoder215.starcosmetics.api.cosmetics.pet.PetCosmetics;
import me.gamercoder215.starcosmetics.api.player.PlayerSetting;
import me.gamercoder215.starcosmetics.api.player.StarPlayer;
import me.gamercoder215.starcosmetics.util.Constants;
import me.gamercoder215.starcosmetics.util.TriConsumer;
import me.gamercoder215.starcosmetics.util.selection.ParticleSelection;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import static me.gamercoder215.starcosmetics.api.player.StarPlayerUtil.getCached;
import static me.gamercoder215.starcosmetics.api.player.StarPlayerUtil.getPets;
import static me.gamercoder215.starcosmetics.util.Constants.dw;

public final class BaseShape implements ParticleShape {

    public static final BaseShape ALL = new BaseShape("all", (l, o, m) -> {});

    // Rings

    public static final BaseShape SMALL_RING = new BaseShape("small_ring", (l, o, m) -> circle(l, o, 35 / m, 4));

    public static final BaseShape SMALL_DETAILED_RING = new BaseShape("small_detailed_ring", (l, o, m) -> {
        circle(l, o, 50 / m, 5);
        circle(l, o, 35 / m, 3);
    });

    public static final BaseShape LARGE_RING = new BaseShape("large_ring", (l, o, m) -> circle(l, o, 50 / m, 6));

    public static final BaseShape LARGE_DETAILED_RING = new BaseShape("large_detailed_ring", (l, o, m) -> {
        circle(l, o, 60 / m, 10);
        circle(l, o, 45 / m, 7);
        circle(l, o, 20 / m, 2);
    });

    // Trianges

    public static final BaseShape SMALL_TRIANGLE = new BaseShape("small_triangle", (l, o, m) -> polygon(l, o, 3, 3, m));
    public static final BaseShape MEDIUM_TRIANGLE = new BaseShape("medium_triangle", (l, o, m) -> polygon(l, o, 3, 5.5, m));
    public static final BaseShape LARGE_TRIANGLE = new BaseShape("large_triangle", (l, o, m) -> polygon(l, o, 3, 8, m));

    public static final BaseShape LARGE_DETAILED_TRIANGLE = new BaseShape("large_detailed_triangle", (l, o, m) -> {
        polygon(l, o, 3, 8, m);
        polygon(l, o, 3, 6, m);
        polygon(l, o, 3, 4, m);
        polygon(l, o, 3, 2, m);
    });

    // Squares

    public static final BaseShape SMALL_SQUARE = new BaseShape("small_square", (l, o, m) -> polygon(l, o, 4, 2.5, m));
    public static final BaseShape LARGE_SQUARE = new BaseShape("large_square", (l, o, m) -> polygon(l, o, 4, 7, m));

    // Pentagons

    public static final BaseShape PENTAGON = new BaseShape("pentagon", (l, o, m) -> polygon(l, o, 5, 2.5, m));

    public static final BaseShape DETAILED_PENTAGON = new BaseShape("detailed_pentagon", (l, o, m) -> {
        polygon(l, o, 5, 3, m);
        polygon(l, o, 5, 2.5, m);
        polygon(l, o, 5, 2, m);
        polygon(l, o, 5, 1.5, m);
    });

    // Octagons

    public static final BaseShape OCTAGON = new BaseShape("octagon", (l, o, m) -> polygon(l, o, 8, 2, m));

    public static final BaseShape DETAILED_OCTAGON = new BaseShape("detailed_octagon", (l, o, m) -> {
        polygon(l, o, 8, 2, m);
        polygon(l, o, 8, 1.75, m);
        polygon(l, o, 8, 1.5, m);
    });

    // Combinations

    public static final BaseShape SQUARE_RING = new BaseShape("square_ring", (l, o, m) -> {
        circle(l, o, 50 / m, 3);
        polygon(l, o, 4, 3, m);
    });

    public static final BaseShape PENTAGON_RING = new BaseShape("pentagon_ring", (l, o, m) -> {
        circle(l, o, 60 / m, 3.5);
        polygon(l, o, 5, 3.5, m);
    });

    // Spawn Utilities

    public static void spawn(Location l, Object o) {
        if (o instanceof Material) {
            Material m = (Material) o;
            Object input = m.isBlock() ? m : new ItemStack(m);

            if (input instanceof ItemStack) {
                ItemStack item = (ItemStack) input;
                l.getWorld().spawnParticle(Particle.ITEM_CRACK, l, 1, 0, 0, 0, 0, item);
            } else
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

    public static void polygon(Location l, Object o, int points, double radius) { polygon(l, o, points, radius, 1); }

    public static void polygon(Location l, Object o, int points, double radius, int mod) {
        double step = 0.1 * mod;

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

            for (double d = 0; d < (dist - (2.0 - (2 * ((double) points / 10))) ); d += step) {
                l.add(x + dX * d, 0, z + dZ * d);
                spawn(l, o);
                l.subtract(x + dX * d, 0, z + dZ * d);
            }
        }
    }

    public static void line(Location l, Object o, int length, long stepDelay) {
        Vector dir = l.getDirection();
        double width = 0.1;

        int count = 0;
        for (double i = 0; i < length * (1 / width); i += width) {
            final double fi = i;

            BukkitRunnable r = new BukkitRunnable() {
                @Override
                public void run() {
                    dir.multiply(fi);
                    l.add(dir);
                    spawn(l, o);
                    l.subtract(dir);
                    dir.normalize();
                }
            };

            if (stepDelay == 0 || i == 0)
                r.run();
            else
                r.runTaskLater(StarConfig.getPlugin(), stepDelay * count);

            count++;
        }
    }


    private final TriConsumer<Location, Object, Integer> particle;
    private final ParticleSize size;
    private final String name;

    private BaseShape(String name, TriConsumer<Location, Object, Integer> runnable) {
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
    public void run(@NotNull Player p, @NotNull CosmeticLocation<?> location) throws IllegalArgumentException {
        if (!(location instanceof ParticleSelection)) return;

        StarPlayer sp = getCached(p);
        ParticleSelection sel = (ParticleSelection) location;
        Object input = sel.getInput();

        final int modifier = sp.getSetting(PlayerSetting.PARTICLE_REDUCTION).getModifier();
        final PetCosmetics setting = sp.getSetting(PlayerSetting.PET_COSMETICS);

        particle.accept(p.getLocation().add(0, 0.25, 0), input, modifier);

        if (setting.isStarPet() && getPets().containsKey(p.getUniqueId())) {
            LivingEntity petEntity = getPets().get(p.getUniqueId()).getEntity();
            particle.accept(petEntity.getLocation().add(0, 0.8, 0), input, modifier);
        }

        if (setting.isTameables())
            new BukkitRunnable() {
                public void run() {
                    Bukkit.getWorlds().forEach(w ->
                            w.getEntitiesByClass(LivingEntity.class)
                                    .stream()
                                    .filter(l -> l instanceof Tameable && ((Tameable) l).getOwner() != null && ((Tameable) l).getOwner().getUniqueId().equals(p.getUniqueId()))
                                    .map(l -> (Tameable & LivingEntity) l)
                                    .forEach(t -> particle.accept(t.getLocation(), input, modifier))
                    );
                }
            }.runTask(StarConfig.getPlugin());

    }
}
