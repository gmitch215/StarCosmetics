package me.gamercoder215.starcosmetics.api.cosmetics.structure;

import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Represents a point on a Structure.
 * @since 1.0.0
 */
public final class StructurePoint {
    
    private final int x;
    private final int y;
    private final int z;

    /**
     * Creates a new StructurePoint.
     * @param x Relative X to the Center
     * @param y Relative Y to the Center
     * @param z Relative Z to the Center
     * @since 1.0.0
     */
    public StructurePoint(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Fetches the relative X distance to the Center.
     * @return Relative X Distance
     * @since 1.0.0
     */
    public int getX() {
        return x;
    }

    /**
     * Fetches the relative Y distance to the Center.
     * @return Relative Y Distance
     * @since 1.0.0
     */
    public int getY() {
        return y;
    }

    /**
     * Fetches the relative Z distance to the Center.
     * @return Relative Z Distance
     * @since 1.0.0
     */
    public int getZ() {
        return z;
    }

    /**
     * Converts this StructurePoint to a Location.
     * @param relative Center Location
     * @return Location of this StructurePoint according to the relative Location
     * @since 1.0.0
     */
    @NotNull
    public Location toLocation(@NotNull Location relative) {
        return new Location(relative.getWorld(),
         relative.getX() + x, relative.getY() + y, relative.getZ() + z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StructurePoint that = (StructurePoint) o;
        return x == that.x && y == that.y && z == that.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public String toString() {
        return "StructurePoint{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
