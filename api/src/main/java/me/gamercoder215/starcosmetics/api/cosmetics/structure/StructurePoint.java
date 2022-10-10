package me.gamercoder215.starcosmetics.api.cosmetics.structure;

import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Represents a point on a Structure.
*/
public final class StructurePoint implements Comparable<StructurePoint> {
    
    private final int x;
    private final int y;
    private final int z;

    /**
     * Creates a new StructurePoint.
     * @param x Relative X to the Center
     * @param y Relative Y to the Center
     * @param z Relative Z to the Center
     */
    public StructurePoint(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Fetches the relative X distance to the Center.
     * @return Relative X Distance
     */
    public int getX() {
        return x;
    }

    /**
     * Fetches the relative Y distance to the Center.
     * @return Relative Y Distance
     */
    public int getY() {
        return y;
    }

    /**
     * Fetches the relative Z distance to the Center.
     * @return Relative Z Distance
     */
    public int getZ() {
        return z;
    }

    /**
     * Converts this StructurePoint to a Location.
     * @param relative Center Location
     * @return Location of this StructurePoint according to the relative Location
     */
    @NotNull
    public Location toLocation(@NotNull Location relative) {
        return new Location(relative.getWorld(),
         relative.getX() + x, relative.getY() + y, relative.getZ() + z);
    }

    /**
     * Converts this StructurePoint to a Vector, inputting its actual X, Y, and Z.
     * @return Vector of this StructurePoint
     */
    @NotNull
    public Vector toVector() {
        return new Vector(x, y, z);
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

    @Override
    public int compareTo(@NotNull StructurePoint o) {
        return Integer.compare(this.hashCode(), o.hashCode());
    }
}
