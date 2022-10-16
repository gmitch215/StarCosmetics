package me.gamercoder215.starcosmetics.api.cosmetics.structure;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

public final class LegacyStructure extends Structure {

    private final Map<StructurePoint, Material> points;

    private final Set<StructurePoint> placed = new HashSet<>();

    LegacyStructure(String minVersion, String displayKey, Map<StructurePoint, Material> points) {
        super(minVersion, displayKey);

        this.points = points;
    }

    @Override
    public @NotNull Map<StructurePoint, Material> getMaterials() {
        return points;
    }

    @Override
    public void place(@NotNull Location center) throws IllegalArgumentException {
        if (center == null) throw new IllegalArgumentException("Location Center cannot be null");

        points.forEach((p, m) -> {
            Location l = new Location(
                center.getWorld(),
                p.getX() + center.getBlockX(), p.getY() + center.getBlockY(), p.getZ() + center.getBlockZ());
       
            if (!l.getBlock().isEmpty()) return;

            l.getBlock().setType(m);
            placed.add(p);
        });
    }

    @Override
    public @NotNull Set<StructurePoint> getPointsPlaced() {
        return placed;
    }
    
}
