package me.gamercoder215.starcosmetics.api.cosmetics.structure;

import me.gamercoder215.starcosmetics.api.StarConfig;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import java.util.Map;

public class Structure {
    
    private final String minVersion;
    private final String displayKey;
    private final Map<StructurePoint, Material> points;
    private final Map<StructurePoint, EntityType> entities;

    public Structure(String minVersion, String displayKey, Map<StructurePoint, Material> points, Map<StructurePoint, EntityType> entities) {
        this.minVersion = minVersion;
        this.points = points;
        this.entities = entities;
        this.displayKey = displayKey;
    }

    public Map<StructurePoint, Material> getPoints() {
        return points;
    }

    public Map<StructurePoint, EntityType> getEntities() {
        return entities;
    }

    public String getMinVersion() {
        return minVersion;
    }

    public boolean isCompatible() {
        if (minVersion.equalsIgnoreCase("ALL")) return true;
        int current = Integer.parseInt(StarConfig.getServerVersion().split("_")[1]);
        int required = Integer.parseInt(minVersion.split("\\.")[1]);
        return current >= required;
    }

    public String getDisplayKey() {
        return displayKey;
    }

}
