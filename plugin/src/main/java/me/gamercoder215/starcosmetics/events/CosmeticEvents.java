package me.gamercoder215.starcosmetics.events;

import me.gamercoder215.starcosmetics.StarCosmetics;
import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.api.cosmetics.CosmeticLocation;
import me.gamercoder215.starcosmetics.api.cosmetics.pet.Pet;
import me.gamercoder215.starcosmetics.api.cosmetics.pet.StarPet;
import me.gamercoder215.starcosmetics.api.cosmetics.trail.Trail;
import me.gamercoder215.starcosmetics.api.cosmetics.trail.TrailType;
import me.gamercoder215.starcosmetics.api.events.cosmetics.PlayerDamageEntityEvent;
import me.gamercoder215.starcosmetics.api.events.cosmetics.PlayerDamagePlayerEvent;
import me.gamercoder215.starcosmetics.api.events.cosmetics.PlayerTakeDamageByEntityEvent;
import me.gamercoder215.starcosmetics.api.events.cosmetics.PlayerTakeDamageByPlayerEvent;
import me.gamercoder215.starcosmetics.api.player.SoundEventSelection;
import me.gamercoder215.starcosmetics.api.player.StarPlayer;
import me.gamercoder215.starcosmetics.api.player.StarPlayerUtil;
import me.gamercoder215.starcosmetics.util.StarRunnable;
import me.gamercoder215.starcosmetics.util.selection.GadgetSelection;
import me.gamercoder215.starcosmetics.wrapper.nbt.NBTWrapper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.lang.reflect.Method;

import static me.gamercoder215.starcosmetics.util.Constants.w;

public final class CosmeticEvents implements Listener {

    private final StarCosmetics plugin;

    public CosmeticEvents(StarCosmetics plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);

        RegisteredListener reg = new RegisteredListener(this, (l, e) -> onEvent(e), EventPriority.NORMAL, plugin, false);
        for (HandlerList h : HandlerList.getHandlerLists()) h.register(reg);
    }

    public void onEvent(Event e) {
        if (SoundEventSelection.isValid(e.getClass())) {
            Player p = null;
            try {
                Method get = e.getClass().getMethod("getPlayer");
                get.setAccessible(true);
                p = (Player) get.invoke(e);
            } catch (NoSuchMethodException ignored) {
                Class<?> superC = e.getClass();
                while (superC.getSuperclass() != null) try {
                    superC = superC.getSuperclass();
                    Method get = superC.getDeclaredMethod("getPlayer");
                    get.setAccessible(true);

                    p = (Player) get.invoke(e);
                    break;
                } catch (NoSuchMethodException ignored2) {
                } catch (ReflectiveOperationException err2) {
                    StarConfig.print(err2);
                }
            } catch (ReflectiveOperationException err) {
                StarConfig.print(err);
            }

            if (p == null) throw new IllegalStateException("Invalid Selection Event: " + e.getClass().getName());

            StarPlayer sp = new StarPlayer(p);

            for (SoundEventSelection sel : sp.getSoundSelections())
                if (sel.getEvent().isAssignableFrom(e.getClass())) {
                    sel.play(p);
                    break;
                }
        }
    }

    @EventHandler
    public void onShoot(ProjectileLaunchEvent e) {
        Projectile en = e.getEntity();
        if (!(en.getShooter() instanceof Player)) return;
        Player p = (Player) en.getShooter();
        StarPlayer sp = new StarPlayer(p);

        CosmeticLocation<?> proj = sp.getSelectedTrail(TrailType.PROJECTILE);
        if (proj != null) ((Trail<?>) proj.getParent()).run(en, proj);

        CosmeticLocation<?> sProj = sp.getSelectedTrail(TrailType.PROJECTILE_SOUND);
        if (sProj != null) ((Trail<?>) sProj.getParent()).run(en, sProj);
    }

    @EventHandler
    public void onHit(ProjectileHitEvent e) {
        Projectile en = e.getEntity();
        en.setMetadata("stopped", new FixedMetadataValue(plugin, true));
    }

    private static boolean pitchChanged(Location l1, Location l2) {
        return !l1.getDirection().equals(l2.getDirection()) &&
                l1.getX() == l2.getX() &&
                l1.getY() == l2.getY() &&
                l1.getZ() == l2.getZ();
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        StarPlayer sp = StarPlayerUtil.getCached(p);

        if (sp.getSpawnedPet() != null) {
            Pet spawned = sp.getSpawnedPet();

            ArmorStand as = spawned.getEntity();
            Vector d = p.getLocation().getDirection();

            as.teleport(StarPlayerUtil.createPetLocation(p));
            as.setHeadPose(new EulerAngle(
                    Math.toRadians(d.getX()),
                    Math.toRadians(d.getY()),
                    Math.toRadians(d.getZ())
            ));
            w.setRotation(as, p.getLocation().getYaw(), p.getLocation().getPitch());

            if (pitchChanged(e.getFrom(), e.getTo())) return;

            if (spawned instanceof StarPet) {
                StarPet shp = (StarPet) spawned;
                shp.getInfo().tick(as);
            }
        }

        if (StarPlayerUtil.getHologram(p) != null) {
            ArmorStand as = StarPlayerUtil.getHologram(p);
            as.teleport(p.getEyeLocation().add(0, 0.3, 0));
        }

        if (!pitchChanged(e.getFrom(), e.getTo())) {
            CosmeticLocation<?> ground = sp.getSelectedTrail(TrailType.GROUND);
            if (ground != null) ((Trail<?>) ground.getParent()).run(p, ground);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        ItemStack item = event.getItem();
        if (item == null) return;

        NBTWrapper nbt = NBTWrapper.of(item);
        if (!nbt.hasString("gadget")) return;

        GadgetSelection selection = (GadgetSelection) CosmeticLocation.getByFullKey(nbt.getString("gadget"));
        if (selection == null) return;

        event.setUseItemInHand(Event.Result.DENY);
        event.setUseInteractedBlock(Event.Result.DENY);

        selection.getInput().accept(p.getEyeLocation());
    }

    @EventHandler
    public void onInteract(PlayerArmorStandManipulateEvent event) {
        ArmorStand stand = event.getRightClicked();
        if (stand.hasMetadata("starcosmetics:nointeract"))
            event.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) return;

        Entity entity = event.getEntity();
        Entity damager = event.getDamager();

        if (damager.hasMetadata("cosmetic"))
            event.setCancelled(true);

        if (entity instanceof Player || damager instanceof Player) {
            boolean isDamagee = entity instanceof Player;
            boolean isDamager = damager instanceof Player;

            if (isDamagee && isDamager) {
                Bukkit.getPluginManager().callEvent(new PlayerDamagePlayerEvent(event));
                Bukkit.getPluginManager().callEvent(new PlayerTakeDamageByPlayerEvent(event));

                damager.setMetadata("pvp", new FixedMetadataValue(plugin, true));
                entity.setMetadata("pvp", new FixedMetadataValue(plugin, true));

                StarRunnable.syncLater(() -> {
                    damager.removeMetadata("pvp", plugin);
                    entity.removeMetadata("pvp", plugin);
                }, 20 * 5);
            } else if (isDamagee && !isDamager) {
                Bukkit.getPluginManager().callEvent(new PlayerTakeDamageByEntityEvent(event));
                entity.setMetadata("pve", new FixedMetadataValue(plugin, true));

                StarRunnable.syncLater(() -> entity.removeMetadata("pve", plugin), 20 * 3);
            }
            else if (!isDamagee && isDamager) {
                Bukkit.getPluginManager().callEvent(new PlayerDamageEntityEvent(event));
                damager.setMetadata("pve", new FixedMetadataValue(plugin, true));

                StarRunnable.syncLater(() -> damager.removeMetadata("pve", plugin), 20 * 3);
            }
        }

    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) return;
        if (e.getClickedInventory() instanceof PlayerInventory) return;

        if (e.getCursor() != null) {
            NBTWrapper w = NBTWrapper.of(e.getCursor());
            if (w.hasString("gadget")) e.setCancelled(true);
        }

        if (e.getCurrentItem() != null) {
            NBTWrapper w = NBTWrapper.of(e.getCurrentItem());
            if (w.hasString("gadget")) e.setCancelled(true);
        }

    }

    // Function Events

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        w.addPacketInjector(e.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        w.removePacketInjector(e.getPlayer());
    }

}
