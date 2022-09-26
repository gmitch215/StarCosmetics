package me.gamercoder215.starcosmetics.api.cosmetics;

import me.gamercoder215.starcosmetics.api.StarConfig;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public enum BaseTrail implements BiConsumer<Entity, Object>, CosmeticKey {
    
    PROJECTILE_TRAIL(Material.ARROW, (e, o) -> {
        if (!(e instanceof Projectile)) return;
        Projectile p = (Projectile) e;
        Location loc = p.getLocation();

        if (o instanceof Particle) {
            Particle part = (Particle) o;
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (p.isDead() || !p.isValid()) {
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
                    if (p.isDead() || !p.isValid()) {
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
                    if (p.isDead() || !p.isValid()) {
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
                        if (p.isDead() || !p.isValid()) {
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
    });

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

    @Override
    public void accept(Object... args) {
        accept((Projectile) args[0], args[1]);
    }

    public static class TrailSelection extends CosmeticSelection {

        private final String name;
        private final BaseTrail parent;

        public TrailSelection(String name, BaseTrail parent, Object value, CompletionCriteria criteria, CosmeticRarity rarity) {
            super(value, criteria, rarity);

            this.name = name;
            this.parent = parent;
        }

        @Override
        public String getKey() {
            return name;
        }

        @Override
        public CosmeticKey getParent() {
            return parent;
        }
    }

}