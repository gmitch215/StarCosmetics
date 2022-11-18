package me.gamercoder215.starcosmetics.api.cosmetics;

import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.api.cosmetics.registry.CosmeticLocation;
import me.gamercoder215.starcosmetics.api.cosmetics.trail.Trail;
import me.gamercoder215.starcosmetics.api.cosmetics.trail.TrailType;
import me.gamercoder215.starcosmetics.util.Constants;
import me.gamercoder215.starcosmetics.util.StarSound;
import me.gamercoder215.starcosmetics.util.entity.StarSelector;
import me.gamercoder215.starcosmetics.wrapper.Wrapper;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import static me.gamercoder215.starcosmetics.util.Constants.r;
import static me.gamercoder215.starcosmetics.util.entity.StarSelector.isStopped;

@SuppressWarnings("unchecked")
public final class BaseTrail<T> implements Trail<T> {

    public static final Wrapper w = Wrapper.getWrapper();

    private static final double DEFAULT_OFFSET = 0.2;

    private static double normalize(float rot) {
        float v = rot;
        while (v < -180.0F) v += 360.0F;
        while (v >= 180.0F) v -= 360.0F;
        return Math.toRadians(v);
    }
    
    public static final BaseTrail<Object> PROJECTILE_TRAIL = new BaseTrail<>("projectile", Object.class, TrailType.PROJECTILE, Material.ARROW, (en, cloc) -> {
        if (!(en instanceof Projectile)) return;
        Projectile p = (Projectile) en;
        Object o = cloc.getInput();

        if (o instanceof Particle) {
            Particle part = (Particle) o;
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (isStopped(p)) {
                        cancel();
                        return;
                    }

                    p.getWorld().spawnParticle(part, p.getLocation(), 3, DEFAULT_OFFSET, DEFAULT_OFFSET, DEFAULT_OFFSET, 0);
                }
            }.runTaskTimer(StarConfig.getPlugin(), 2, 1);
        }

        if (o instanceof Material || o instanceof ItemStack) {
            ItemStack item = o instanceof Material ? new ItemStack((Material) o) : (ItemStack) o;
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (isStopped(p)) {
                        cancel();
                        return;
                    }
                    
                    w.spawnFakeItem(item, p.getLocation(), 5);
                }
            }.runTaskTimer(StarConfig.getPlugin(), 5, 2);
        }

        if (o instanceof EntityType) {
            EntityType type = (EntityType) o;
            if (!LivingEntity.class.isAssignableFrom(type.getEntityClass())) return;
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (isStopped(p)) {
                        cancel();
                        return;
                    }

                    for (Player pl : p.getWorld().getPlayers()) w.spawnFakeEntity(pl, type, p.getLocation(), 10);
                }
            }.runTaskTimer(StarConfig.getPlugin(), 6, 2);
        }

        if (o instanceof Collection<?>) {
            List<?> list = new ArrayList<>(((Collection<?>) o));
            if (list.isEmpty()) return;

            Object first = list.get(0);
            if (first instanceof Material || first instanceof ItemStack) {
                List<ItemStack> items = first instanceof Material
                        ? list.stream().map(m -> new ItemStack((Material) m)).collect(Collectors.toList())
                        : (List<ItemStack>) list;
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (isStopped(p)) {
                            cancel();
                            return;
                        }

                        ItemStack item = items.get(r.nextInt(items.size()));
                        w.spawnFakeItem(item, p.getLocation(), 10);
                    }
                }.runTaskTimer(StarConfig.getPlugin(), 5, 5);
            }
        }

        // Custom Trails

        if (o instanceof String) {
            String str = (String) o;
            switch (str.toLowerCase()) {
                case "riptide": {
                    w.attachRiptide(p);
                    StarSound.ITEM_TRIDENT_RIPTIDE_1.play(p, 5F, 1F);
                    break;
                }
            }

            // Custom Trails with Input
            if (str.contains(":")) {
                String type = str.split(":")[0];
                String input = str.split(":")[1];
                switch (type) {
                    case "fancy_item": {
                        Material m = Material.matchMaterial(input);
                        ItemStack item = new ItemStack(m);
                        
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if (StarSelector.isStopped(en)) {
                                    cancel();
                                    return;
                                }

                                Location spawn = p.getLocation().subtract(0, 1, 0).setDirection(new Vector(0, 0, 0));
                                
                                ArmorStand as = p.getWorld().spawn(spawn, ArmorStand.class);
                                as.setInvulnerable(true);
                                as.setBasePlate(false);
                                as.setVisible(false);
                                as.setArms(true);
                                as.setMarker(true);
                                w.setRotation(as, 90.0F, 90.0F);

                                Location l = p.getLocation();

                                EulerAngle angle = new EulerAngle(
                                        normalize(l.getPitch() + 170.0F),
                                        0,
                                        normalize(-l.getYaw())
                                );

                                as.setRightArmPose(angle);
                                as.getEquipment().setItemInMainHand(item);

                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        as.remove();
                                    }
                                }.runTaskLater(StarConfig.getPlugin(), 4);
                            }
                        }.runTaskTimer(StarConfig.getPlugin(), 5, 1);
                        break;
                    }
                    case "fancy_block": {
                        Material m = Material.matchMaterial(input);
                        ItemStack item = new ItemStack(m);
                        
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if (StarSelector.isStopped(en)) {
                                    cancel();
                                    return;
                                }

                                Location spawn = p.getLocation().subtract(0, 1, 0).setDirection(new Vector(0, 0, 0));
                                
                                ArmorStand as = p.getWorld().spawn(spawn, ArmorStand.class);
                                as.setInvulnerable(true);
                                as.setBasePlate(false);
                                as.setVisible(false);
                                as.setArms(true);
                                as.setMarker(true);

                                Location l = p.getLocation();

                                as.setHeadPose(new EulerAngle(
                                    Math.toRadians(l.getYaw()) - 180.0F,
                                    Math.toRadians(l.getPitch()) - 180.0F,
                                    0
                                ));
                                as.getEquipment().setHelmet(item);

                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        as.remove();
                                    }
                                }.runTaskLater(StarConfig.getPlugin(), 4);
                            }
                        }.runTaskTimer(StarConfig.getPlugin(), 5, 1);
                        break;
                    }
                }
            }
        }
    });

    public static final BaseTrail<Object> GROUND_TRAIL = new BaseTrail<>("ground", Object.class, TrailType.GROUND, Material.STONE, (e, cloc) -> {
        if (!(e instanceof Player)) return;
        Player p = (Player) e;
        Location loc = p.getLocation().add(0, 0.1, 0);
        Object o = cloc.getInput();

        if (o instanceof Particle) {
            Particle part = (Particle) o;
            e.getWorld().spawnParticle(part, loc, r.nextInt(2), 0, 0, 0, 0);
        }

        if (o instanceof Material || o instanceof ItemStack) {
            ItemStack item = o instanceof Material ? new ItemStack((Material) o) : (ItemStack) o;
            w.spawnFakeItem(item, loc, 5);
        }
    });

    public static final BaseTrail<Sound> SOUND_TRAIL = new BaseTrail<>("sound", Sound.class, TrailType.PROJECTILE_SOUND, Material.JUKEBOX, (e, cloc) -> {
        Object o = cloc.getInput();
        if (!(o instanceof Sound)) return;
        Sound sound = (Sound) o;

        if (!(e instanceof Projectile)) return;
        Projectile p = (Projectile) e;

        new BukkitRunnable() {
            @Override
            public void run() {
                if (isStopped(p)) {
                    cancel();
                    return;
                }

                p.getWorld().playSound(p.getLocation(), sound, r.nextInt(2) + 3, 1);
            }
        }.runTaskTimer(StarConfig.getPlugin(), 2, 2);

        e.getWorld().playSound(e.getLocation(), sound, 1, 1);
    });

    private final Material icon;
    private final BiConsumer<Entity, CosmeticLocation<?>> trail;
    private final Class<T> otype;
    private final TrailType type;
    private final String name;

    private BaseTrail(String name, Class<T> otype, TrailType type, Material icon, BiConsumer<Entity, CosmeticLocation<?>> runnable) {
        this.icon = icon;
        this.trail = runnable;
        this.otype = otype;
        this.type = type;
        this.name = name;

        Constants.PARENTS.add(this);
    }

    @Override
    public String getNamespace() {
        return "trail/" + name;
    } 

    @Override
    public String getDisplayName() {
        return StarConfig.getConfig().get("cosmetics.trail." + name.toLowerCase());
    }

    @Override
    public Material getIcon() {
        return icon;
    }

    @Override
    public Class<T> getObjectType() {
        return otype;
    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BaseTrail)) return false;
        BaseTrail<?> other = (BaseTrail<?>) obj;
        if (this == other) return true;
        return other.type == type;
    }

    @Override
    public void run(@NotNull Entity en, @NotNull CosmeticLocation<?> location) throws IllegalArgumentException {
        trail.accept(en, location);
    }

    @Override
    public String toString() {
        return "Trail[" + name + "]";
    }

    @Override
    public @NotNull TrailType getType() {
        return type;
    }

}
