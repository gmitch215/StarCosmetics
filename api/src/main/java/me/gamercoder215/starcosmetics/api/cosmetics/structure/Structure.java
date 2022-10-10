package me.gamercoder215.starcosmetics.api.cosmetics.structure;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Represents a StarCosmetics Structure
 */
public final class Structure {
    
    final String minVersion;
    private final String displayKey;
    private final Map<StructurePoint, Material> points;

    private final Map<StructurePoint, String> blockData;

    Structure(String minVersion, String displayKey, Map<StructurePoint, Material> points, Map<StructurePoint, String> blockData) {
        this.minVersion = minVersion;
        this.points = points;
        this.displayKey = displayKey;
        this.blockData = blockData;
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
     * Fetches a Map of StructurePoints to BlockData that the Structure will place (only works for 1.13+).
     * @return StructurePoint to BlockData Blueprint Map
     */
    @NotNull
    public Map<StructurePoint, String> getBlockData() {
        return blockData;
    }

    /**
     * Whether this Structure is compatible with this version of Minecraft.
     * @return true if this Structure is compatible with this version of Minecraft, false otherwise.
     */
    public boolean isCompatible() {
        if (minVersion.equalsIgnoreCase("ALL")) return true;
        String currentV = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].substring(1);

        int current = Integer.parseInt(currentV.split("_")[1]);
        int required = Integer.parseInt(minVersion.split("\\.")[1]);
        return current >= required;
    }

}
