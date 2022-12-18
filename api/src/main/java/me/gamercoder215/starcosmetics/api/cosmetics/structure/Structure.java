package me.gamercoder215.starcosmetics.api.cosmetics.structure;

import me.gamercoder215.starcosmetics.api.Rarity;
import me.gamercoder215.starcosmetics.api.StarConfig;
import org.bukkit.Location;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a StarCosmetics Structure
 */
public abstract class Structure implements Cloneable {
    
    /**
     * Represents the default removal time, in ticks, of a Structure (30 seconds).
     */
    public static final long DEFAULT_REMOVAL_TIME = 600;

    final String minVersion;

    final String key;

    private final String displayKey;

    Structure(String minVersion, String displayKey, String key) {
        this.minVersion = minVersion;
        this.displayKey = displayKey;
        this.key = key;
    }

    /**
     * Fetches a Map of StructurePoints to Materials that the Structure will place.
     * @return StructurePoint to Material Blueprint Map
     */
    @NotNull
    public abstract Map<StructurePoint, Material> getMaterials();

    /**
     * Fetches the Structure's Structure Info.
     * @return Structure Info
     */
    @NotNull
    public final StructureInfo getInfo() {
        return new StructureInfo(this);
    }

    /**
     * Places this Structure WITHOUT automatically removing it.
     * @param center Center of Structure
     * @throws IllegalArgumentException if center is null
     * @throws IllegalStateException if this instance has already been placed
     */
    public abstract void place(@NotNull Location center) throws IllegalArgumentException, IllegalStateException;

    /**
     * Fetches the rarity of this Structure.
     * @return Structure Rarity
     */
    public abstract Rarity getRarity();
    
    /**
     * Places this Structure.
     * @param center Center of Structure
     * @param delay Delay, in ticks, to remove this Structure
     * @throws IllegalArgumentException if delay is negative or center is null
     * @throws IllegalStateException if this instance has already been placed and not removed
     */
    public abstract void placeAndRemove(@NotNull Location center, long delay) throws IllegalArgumentException, IllegalStateException;

    /**
     * <p>Fetches all of the points of this Structure that have been placed.</p>
     * <p>If the structure hasn't been placed, this set will be empty. This is to ensure nothing that was previously there doesn't get removed.</p>
     * @return All Structure Points
     */
    @NotNull
    public abstract Set<StructurePoint> getPointsPlaced();

    /**
     * Fetches the localized name of this Structure.
     * @return Localized Name
     */
    @NotNull
    public String getLocalizedName() {
        if (displayKey.startsWith("\"") && displayKey.endsWith("\"")) return displayKey.substring(1, displayKey.length() - 1);
        return StarConfig.getConfig().get(displayKey);
    }

    @Override
    public Structure clone() {
        try {
            Structure clone = (Structure) super.clone();
            clone.getPointsPlaced().clear();

            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Structure structure = (Structure) o;
        return minVersion.equals(structure.minVersion) && key.equals(structure.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(minVersion, key);
    }

    @Override
    public String toString() {
        return "Structure{" +
                "minVersion='" + minVersion + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
