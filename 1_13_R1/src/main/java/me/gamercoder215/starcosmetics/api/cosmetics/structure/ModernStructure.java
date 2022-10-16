package me.gamercoder215.starcosmetics.api.cosmetics.structure;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.NotNull;

public final class ModernStructure extends Structure {

    private final Map<StructurePoint, Material> points;
    private final Map<StructurePoint, BlockData> pointData;

    private final Set<StructurePoint> placed = new HashSet<>();

    ModernStructure(String minVersion, String displayKey, Map<StructurePoint, Material> points, Map<StructurePoint, String> pointData) {
        super(minVersion, displayKey);

        this.points = points;
        this.pointData = pointData.entrySet()
            .stream()
            .filter(Objects::nonNull)
            .map(e -> new AbstractMap.SimpleEntry<>(e.getKey(), Bukkit.createBlockData(e.getValue())))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }


    @Override
    public @NotNull Map<StructurePoint, Material> getMaterials() {
        return points;
    }

    @Override
    public void place(@NotNull Location center) throws IllegalArgumentException {
        if (center == null) throw new IllegalArgumentException("Location Center cannot be null");
        if (isBeingPlaced()) throw new IllegalStateException("Structure is currently being placed");

        isPlacing = true;

        points.forEach((p, m) -> {
            Location l = new Location(
                center.getWorld(),
                p.getX() + center.getBlockX(), p.getY() + center.getBlockY(), p.getZ() + center.getBlockZ());
            
            if (!l.getBlock().isEmpty()) return;

            BlockData d = pointData.get(p);

            l.getBlock().setType(m);
            if (d != null) l.getBlock().setBlockData(d);

            placed.add(p);
        });

        isPlacing = false;
    }


    @Override
    public @NotNull Set<StructurePoint> getPointsPlaced() {
        return placed;
    }
    
}
