package me.gamercoder215.starcosmetics.api.cosmetics.structure;

import me.gamercoder215.starcosmetics.api.Rarity;
import me.gamercoder215.starcosmetics.util.StarRunnable;
import me.gamercoder215.starcosmetics.wrapper.Wrapper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static me.gamercoder215.starcosmetics.wrapper.Wrapper.getWrapper;

public final class LegacyStructure extends Structure {

    private static final Wrapper w = getWrapper();

    private final Map<StructurePoint, Material> points;

    private final Rarity rarity;

    private final Set<StructurePoint> placed = new HashSet<>();

    LegacyStructure(String key, String minVersion, String displayKey, Map<StructurePoint, Material> points, Rarity rarity) {
        super(minVersion, displayKey, key);

        this.points = points;
        this.rarity = rarity == null ? Rarity.COMMON : rarity;
    }

    @Override
    public @NotNull Map<StructurePoint, Material> getMaterials() {
        return points;
    }

    @Override
    public void place(@NotNull Location center) throws IllegalArgumentException, IllegalStateException {
        if (center == null) throw new IllegalArgumentException("Location Center cannot be null");

        points.forEach((p, m) -> {
            Location l = p.toLocation(center);
            if (!l.getBlock().isEmpty()) return;

            StarRunnable.async(() -> { for (Player pl : Bukkit.getOnlinePlayers()) w.sendBlockChange(pl, l, m); });

            placed.add(p);
        });
    }

    @Override
    public void placeAndRemove(@NotNull Location center, long delay) throws IllegalArgumentException, IllegalStateException {
        if (delay < 0) throw new IllegalArgumentException("Delay must be greater than 0");
        place(center);

        StarRunnable.syncLater(() -> {
            Iterator<StructurePoint> it = getPointsPlaced().iterator();
            while (it.hasNext()) {
                StructurePoint p = it.next();
                Location l = p.toLocation(center);

                StarRunnable.async(() -> {
                    for (Player pl : Bukkit.getOnlinePlayers())
                        w.sendBlockChange(pl, l, Material.AIR);
                });

                it.remove();
            }
        }, delay);
    }

    @Override
    public Rarity getRarity() {
        return rarity;
    }

    @Override
    public @NotNull Set<StructurePoint> getPointsPlaced() {
        return placed;
    }
    
}
