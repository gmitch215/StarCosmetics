package me.gamercoder215.starcosmetics.api.cosmetics.structure;

import me.gamercoder215.starcosmetics.api.Rarity;
import me.gamercoder215.starcosmetics.util.StarRunnable;
import me.gamercoder215.starcosmetics.wrapper.Wrapper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

import static me.gamercoder215.starcosmetics.wrapper.Wrapper.getWrapper;

public final class ModernStructure extends Structure {

    private static final Wrapper w = getWrapper();

    private final Map<StructurePoint, Material> points;
    private final Map<StructurePoint, BlockData> pointData;

    private final Rarity rarity;

    private final Set<StructurePoint> placed = new HashSet<>();

    ModernStructure(String key, String minVersion, String displayKey, Map<StructurePoint, Material> points, Map<StructurePoint, String> pointData, Rarity rarity) {
        super(minVersion, displayKey, key);

        this.rarity = rarity == null ? Rarity.COMMON : rarity;
        this.points = points;
        this.pointData = pointData.entrySet()
            .stream()
            .filter(Objects::nonNull)
            .filter(e -> e.getValue() != null && !e.getValue().isEmpty())
            .map(e -> new AbstractMap.SimpleEntry<>(e.getKey(), Bukkit.createBlockData(e.getValue())))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }


    @Override
    public @NotNull Map<StructurePoint, Material> getMaterials() {
        return points;
    }

    @Override
    public void place(@NotNull Location center) throws IllegalArgumentException, IllegalStateException {
        if (center == null) throw new IllegalArgumentException("Location Center cannot be null");
        if (!placed.isEmpty()) throw new IllegalStateException("Structure has already been placed! Use clone()");

        points.forEach((p, m) -> {
            Location l = p.toLocation(center);
            if (!l.getBlock().isEmpty()) return;

            BlockData d = pointData.get(p);
            StarRunnable.async(() -> { for (Player pl : Bukkit.getOnlinePlayers()) pl.sendBlockChange(l, d == null ? m.createBlockData() : d ); });

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
