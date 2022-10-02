package me.gamercoder215.starcosmetics.api.cosmetics.structure;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Represents a StarCosmetics Structure
 * @since 1.0.0
 */
public final class Structure {
    
    final String minVersion;
    private final String displayKey;
    private final Map<StructurePoint, Material> points;
    private final Map<StructurePoint, EntityType> entities;

    Structure(String minVersion, String displayKey, Map<StructurePoint, Material> points, Map<StructurePoint, EntityType> entities) {
        this.minVersion = minVersion;
        this.points = points;
        this.entities = entities;
        this.displayKey = displayKey;
    }

    /**
     * Fetches a Map of StructurePoints to Materials that the Structure will place.
     * @return StructurePoint to Material Blueprint Map
     */
    @NotNull
    public Map<StructurePoint, Material> getPoints() {
        return points;
    }

    /**
     * Whether this Structure is compatible with this version of Minecraft.
     * @return true if this Structure is compatible with this version of Minecraft, false otherwise.
     * @since 1.0.0
     */
    public boolean isCompatible() {
        if (minVersion.equalsIgnoreCase("ALL")) return true;
        String currentV = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].substring(1);

        int current = Integer.parseInt(currentV.split("_")[1]);
        int required = Integer.parseInt(minVersion.split("\\.")[1]);
        return current >= required;
    }

}
