package me.gamercoder215.starcosmetics.api.cosmetics.structure;

import org.bukkit.Location;

import java.util.Objects;

public class StructurePoint {
    
    private final int x;
    private final int y;
    private final int z;

    public StructurePoint(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public Location toLocation(Location relative) {
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
