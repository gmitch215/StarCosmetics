package me.gamercoder215.starcosmetics.api.cosmetics.structure;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import me.gamercoder215.starcosmetics.api.StarConfig;

public class Structure {
    
    private final String minVersion;
    private final String displayKey;
    private Map<StructurePoint, Material> points;
    private Map<StructurePoint, EntityType> entities;

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
