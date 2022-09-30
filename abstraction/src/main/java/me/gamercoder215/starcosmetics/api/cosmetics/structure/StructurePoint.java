package me.gamercoder215.starcosmetics.api.cosmetics.structure;

import org.bukkit.Location;

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

}
