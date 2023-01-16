package me.gamercoder215.starcosmetics.api.cosmetics;

import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.api.cosmetics.trail.Trail;
import me.gamercoder215.starcosmetics.api.cosmetics.trail.TrailType;
import me.gamercoder215.starcosmetics.util.Constants;
import me.gamercoder215.starcosmetics.util.StarMaterial;
import me.gamercoder215.starcosmetics.util.StarRunnable;
import me.gamercoder215.starcosmetics.util.StarSound;
import me.gamercoder215.starcosmetics.util.entity.StarSelector;
import me.gamercoder215.starcosmetics.wrapper.DataWrapper;
import me.gamercoder215.starcosmetics.wrapper.Wrapper;
import org.bukkit.*;
import org.bukkit.block.BlockState;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import static me.gamercoder215.starcosmetics.util.Constants.r;
import static me.gamercoder215.starcosmetics.util.entity.StarSelector.isStopped;
import static org.bukkit.Material.*;

@SuppressWarnings("unchecked")
public final class BaseTrail<T> implements Trail<T> {

    public static final Wrapper w = Wrapper.getWrapper();
    public static final DataWrapper dw = Wrapper.getDataWrapper();

    private static final double DEFAULT_OFFSET = 0.2;
    
    public static final BaseTrail<Object> PROJECTILE_TRAIL = new BaseTrail<>("projectile", Object.class, TrailType.PROJECTILE, ARROW, (en, cloc) -> {
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
                    
                    w.spawnFakeItem(item, p.getLocation(), StarConfig.getConfig().getItemDisappearTime());
                }
            }.runTaskTimer(StarConfig.getPlugin(), 3, 1);
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

                    Location loc = p.getLocation();
                    float yaw = (float) (Math.atan2(loc.getZ(), loc.getX()) * 180 / Math.PI);
                    float pitch = (float) (Math.atan2(loc.getY(), loc.getX()) * 180 / Math.PI);

                    loc.setYaw(yaw);
                    loc.setPitch(pitch);

                    for (Player pl : p.getWorld().getPlayers()) w.spawnFakeEntity(pl, type, p.getLocation(), StarConfig.getConfig().getEntityDisappearTime());
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
                        w.spawnFakeItem(item, p.getLocation(), StarConfig.getConfig().getItemDisappearTime());
                    }
                }.runTaskTimer(StarConfig.getPlugin(), 3, 1);
            }
        }

        if (o instanceof Effect) {
            Effect effect = (Effect) o;
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (isStopped(p)) {
                        cancel();
                        return;
                    }

                    p.getWorld().playEffect(p.getLocation(), effect, null);
                }
            }.runTaskTimer(StarConfig.getPlugin(), 2, 1);
        }

        if (o instanceof EntityEffect) {
            EntityEffect effect = (EntityEffect) o;
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (isStopped(p)) {
                        cancel();
                        return;
                    }

                    p.playEffect(effect);
                }
            }.runTaskTimer(StarConfig.getPlugin(), 2, 1);
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
                        Material m = matchMaterial(input);
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

                                EulerAngle angle = new EulerAngle(
                                        r.nextDouble() * 2 * Math.PI,
                                        r.nextDouble() * 2 * Math.PI,
                                        r.nextDouble() * 2 * Math.PI
                                );

                                as.setRightArmPose(angle);
                                as.getEquipment().setItemInMainHand(item);

                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        as.remove();
                                    }
                                }.runTaskLater(StarConfig.getPlugin(), StarConfig.getConfig().getBlockDisappearTime());
                            }
                        }.runTaskTimer(StarConfig.getPlugin(), 5, 1);
                        break;
                    }
                    case "fancy_block": {
                        Material m = matchMaterial(input);
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
                                        -Math.atan2(l.getY(), Math.hypot(l.getX(), l.getZ())),
                                        -Math.atan2(l.getX(), l.getZ()),
                                        0));

                                as.getEquipment().setHelmet(item);

                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        as.remove();
                                    }
                                }.runTaskLater(StarConfig.getPlugin(), StarConfig.getConfig().getBlockDisappearTime());
                            }
                        }.runTaskTimer(StarConfig.getPlugin(), 5, 1);
                        break;
                    }
                    case "crack": {
                        Material m = matchMaterial(input);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if (isStopped(p)) {
                                    cancel();
                                    return;
                                }
            
                                dw.blockDataParticle(Particle.BLOCK_CRACK, p.getLocation(), 2, m);
                            }
                        }.runTaskTimer(StarConfig.getPlugin(), 2, 1);
                        break;
                    }
                    case "dust": {
                        Material m = matchMaterial(input);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if (isStopped(p)) {
                                    cancel();
                                    return;
                                }
            
                                dw.blockDataParticle(Particle.BLOCK_DUST, p.getLocation(), 2, m);
                            }
                        }.runTaskTimer(StarConfig.getPlugin(), 2, 1);
                        break;
                    }
                }
            }
        }
    });

    public static final BaseTrail<Object> GROUND_TRAIL = new BaseTrail<>("ground", Object.class, TrailType.GROUND, STONE, (e, cloc) -> {
        if (!(e instanceof Player)) return;
        Player p = (Player) e;
        Location loc = p.getLocation().add(0, 0.1, 0);
        Object o = cloc.getInput();

        if (o instanceof Particle) {
            Particle part = (Particle) o;
            e.getWorld().spawnParticle(part, loc, r.nextInt(2), 0, 0, 0, 0);
        }

        if (o instanceof Effect) {
            Effect effect = (Effect) o;
            p.getWorld().playEffect(p.getLocation(), effect, null);
        }

        if (o instanceof EntityEffect) {
            EntityEffect effect = (EntityEffect) o;
            p.playEffect(effect);
        }

        if (o instanceof String) {
            String s = o.toString();
            
            if (!s.contains(":")) {

                switch (s) {
                    case "head": {
                        ItemStack item = StarMaterial.PLAYER_HEAD.findStack();
                        SkullMeta meta = (SkullMeta) item.getItemMeta();
                        meta.setOwner(p.getName());
                        item.setItemMeta(meta);

                        w.spawnFakeItem(item, loc, StarConfig.getConfig().getItemDisappearTime());
                        break;
                    }
                }

                return;
            }

            String prefix = s.split(":")[0];
            String input = s.split(":")[1];

            switch (prefix) {
                case "ground_block": {
                    Material m = matchMaterial(input);

                    Set<Location> area = new HashSet<>();
                    area.add(p.getLocation().subtract(0, 1, 0));

                    Location behind = p.getLocation()
                            .subtract(0, 1, 0)
                            .subtract(p.getLocation().getDirection());

                    area.add(behind);
                    area.add(behind.add(1, 0, 0));
                    area.add(behind.add(0, 0, 1));
                    if (r.nextBoolean()) area.add(behind.add(-1, 0, 0)); else area.add(behind.add(0, 0, -1));

                    final boolean onGround = Math.abs(p.getVelocity().getY()) < 0.1 && !p.isFlying();

                    area.forEach(l -> {
                        if (onGround && l.getBlock().getType().isSolid()) {
                            Material original = l.getBlock().getType();
                            BlockState state = l.getBlock().getState();

                            for (Player pl : p.getWorld().getPlayers()) {
                                w.sendBlockChange(pl, l, m);
                                StarRunnable.syncLater(() -> w.sendBlockChange(pl, l, original, state), 60);
                            }
                        }
                    });

                    break;
                }
                case "side_block": {
                    Material m = matchMaterial(input);

                    Location l = p.getLocation();
                    Location under = p.getLocation().subtract(0, 1, 0);

                    boolean inSolid = under.getBlock().getType().isSolid() && !p.isFlying() && !l.getBlock().isLiquid();

                    if (inSolid) {
                        Material original = l.getBlock().getType();
                        BlockState state = l.getBlock().getState();

                        for (Player pl : p.getWorld().getPlayers()) {
                            w.sendBlockChange(pl, l, m);
                            StarRunnable.syncLater(() -> w.sendBlockChange(pl, l, original, state), 60);
                        }
                    }

                    break;
                }
                case "crack": {
                    Material m = matchMaterial(input);
                    dw.blockDataParticle(Particle.BLOCK_CRACK, loc, 2, m);
                    break;
                }
                case "dust": {
                    Material m = matchMaterial(input);
                    dw.blockDataParticle(Particle.BLOCK_DUST, loc, 2, m);
                    break;
                }
            }

        }

        if (o instanceof Material || o instanceof ItemStack) {
            ItemStack item = o instanceof Material ? new ItemStack((Material) o) : (ItemStack) o;
            w.spawnFakeItem(item, loc, 10);
        }
    });

    public static final BaseTrail<Sound> SOUND_TRAIL = new BaseTrail<>("sound", Sound.class, TrailType.PROJECTILE_SOUND, JUKEBOX, (e, cloc) -> {
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

                p.getWorld().playSound(p.getLocation(), sound, 1.05F, 1);
            }
        }.runTaskTimer(StarConfig.getPlugin(), 2, 3);
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
