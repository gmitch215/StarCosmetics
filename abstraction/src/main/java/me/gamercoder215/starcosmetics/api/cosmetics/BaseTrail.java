package me.gamercoder215.starcosmetics.api.cosmetics;

import me.gamercoder215.starcosmetics.api.StarConfig;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public enum BaseTrail implements Trail {
    
    PROJECTILE_TRAIL(Material.ARROW, (e, o) -> {
        if (!(e instanceof Projectile)) return;
        Projectile p = (Projectile) e;
        Location loc = p.getLocation();

        if (o instanceof Particle) {
            Particle part = (Particle) o;
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (p.isDead() || !p.isValid() || p.hasMetadata("stopped")) {
                        cancel();
                        return;
                    }

                    p.getWorld().spawnParticle(part, loc, 1, 0, 0, 0, 0);
                }
            }.runTaskTimer(StarConfig.getPlugin(), 2, 3);
        }

        if (o instanceof Material || o instanceof ItemStack) {
            ItemStack item = o instanceof Material ? new ItemStack((Material) o) : (ItemStack) o;
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (p.isDead() || !p.isValid() || p.hasMetadata("stopped")) {
                        cancel();
                        return;
                    }
                    
                    for (Player pl : p.getWorld().getPlayers()) StarConfig.getWrapper().spawnFakeItem(pl, item, p.getLocation(), 10);
                }
            }.runTaskTimer(StarConfig.getPlugin(), 5, 5);
        }

        if (o instanceof EntityType) {
            EntityType type = (EntityType) o;
            if (!LivingEntity.class.isAssignableFrom(type.getEntityClass())) return;
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (p.isDead() || !p.isValid() || p.hasMetadata("stopped")) {
                        cancel();
                        return;
                    }

                    for (Player pl : p.getWorld().getPlayers()) StarConfig.getWrapper().spawnFakeEntity(pl, type, p.getLocation(), 10);
                }
            }.runTaskTimer(StarConfig.getPlugin(), 6, 6);
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
                        if (p.isDead() || !p.isValid() || p.hasMetadata("stopped")) {
                            cancel();
                            return;
                        }

                        for (Player pl : p.getWorld().getPlayers()) {
                            ItemStack item = items.get(r.nextInt(items.size()));
                            StarConfig.getWrapper().spawnFakeItem(pl, item, p.getLocation(), 10);
                        }
                    }
                }.runTaskTimer(StarConfig.getPlugin(), 5, 5);
            }
        }

        // Custom Trails

        if (o instanceof String) {
            String type = (String) o;
            switch (type.toLowerCase()) {
                case "riptide": {
                    w.attachRiptide(p);
                    break;
                }
            }

            // Custom Trails with Input
            if (type.contains(":")) {
                type = type.split(":")[0];
                String input = type.split(":")[1];
                switch (type) {
                    case "fancy_item": {
                        Material m = Material.getMaterial(input);
                        ItemStack item = new ItemStack(m);
                        
                        new BukkitRunnable() {
                            public void run() {
                                if (p.isDead() || !p.isValid() || p.hasMetadata("stopped")) {
                                    cancel();
                                    return;
                                }
                                
                                // TODO: Fix Location for right arm aligned with projectile
                                ArmorStand as = p.getWorld().spawn(p.getLocation(), ArmorStand.class);
                                as.setInvulnerable(true);
                                as.setVisible(false);
                                as.setArms(true);
                                as.setMarker(true);

                                Vector dir = p.getLocation().getDirection();
                                
                                as.setRightArmPose(new EulerAngle(dir.getX(), dir.getY(), dir.getZ()));
                                as.getEquipment().setItemInMainHand(item);

                                new BukkitRunnable() {
                                    public void run() {
                                        as.remove();
                                    }
                                }.runTaskLater(StarConfig.getPlugin(), 8);
                        }
                        }.runTaskTimer(StarConfig.getPlugin(), 5, 8);
                        break;
                    }
                }
            }
        }
    }),

    GROUND_TRAIL(Material.STONE, (e, o) -> {
        Location loc = e.getLocation().add(0, 0.1, 0);

        if (o instanceof Particle) {
            Particle part = (Particle) o;
            e.getWorld().spawnParticle(part, loc, r.nextInt(2), 0, 0, 0, 0);
        }

        if (o instanceof Material || o instanceof ItemStack) {
            ItemStack item = o instanceof Material ? new ItemStack((Material) o) : (ItemStack) o;
            for (Player pl : e.getWorld().getPlayers()) StarConfig.getWrapper().spawnFakeItem(pl, item, loc, 5);
        }
    }),

    SOUND_TRAIL(Material.JUKEBOX, (e, o) -> {
        if (!(o instanceof Sound)) return;
        if (!(e instanceof Projectile)) return;
        Projectile p = (Projectile) e;
        Sound sound = (Sound) o;

        new BukkitRunnable() {
            @Override
            public void run() {
                if (p.isDead() || !p.isValid() || p.hasMetadata("stopped")) {
                    cancel();
                    return;
                }

                p.getWorld().playSound(p.getLocation(), sound, r.nextInt(2) + 3, 1);
            }
        }.runTaskTimer(StarConfig.getPlugin(), 2, 2);
        e.getWorld().playSound(e.getLocation(), sound, 1, 1);
    }),

    ;

    private final Material icon;
    private final BiConsumer<Entity, Object> trail;

    BaseTrail(Material icon, BiConsumer<Entity, Object> runnable) {
        this.icon = icon;
        this.trail = runnable;
    }

    @Override
    public void accept(Entity e, Object u) {
        trail.accept(e, u);
    }

    @Override
    public String getNamespace() {
        return "trail";
    } 

    @Override
    public String getDisplayKey() {
        return "cosmetics.trails." + name().toLowerCase();
    }

    @Override
    public Material getIcon() {
        return icon;
    }

}
