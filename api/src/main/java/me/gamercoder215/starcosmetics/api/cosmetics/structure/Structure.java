package me.gamercoder215.starcosmetics.api.cosmetics.structure;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import me.gamercoder215.starcosmetics.api.StarConfig;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Represents a StarCosmetics Structure
 */
public abstract class Structure {
    
    /**
     * Represents the default removal time, in ticks, of a Structure (30 seconds).
     */
    public static final long DEFAULT_REMOVAL_TIME = 600;

    final String minVersion;
    boolean isPlacing;

    private final String displayKey;

    Structure(String minVersion, String displayKey) {
        this.minVersion = minVersion;
        this.displayKey = displayKey;
    }

    /**
     * Fetches a Map of StructurePoints to Materials that the Structure will place.
     * @return StructurePoint to Material Blueprint Map
     */
    @NotNull
    public abstract Map<StructurePoint, Material> getMaterials();

    /**
     * Places this Structure WITHOUT automatically removing it.
     * @param center Center of Structure
     * @throws IllegalArgumentException if center is null
     * @throws IllegalStateException if structure is currently being placed
     */
    public abstract void place(@NotNull Location center) throws IllegalArgumentException, IllegalStateException;

    /**
     * Whether this Structure Instance is currently being placed.
     * @return true if structure is being placed, else false
     */
    public final boolean isBeingPlaced() {
        return isPlacing;
    }
    
    /**
     * Places this Structure.
     * @param center Center of Structure
     * @param delay Delay, in ticks, to remove this Structure
     * @throws IllegalArgumentException if delay is negative or center is null
     */
    public final void placeAndRemove(@NotNull Location center, long delay) throws IllegalArgumentException {
        if (delay < 0) throw new IllegalArgumentException("Delay must be greater than 0");
        place(center);

        new BukkitRunnable() {
            @Override
            public void run() {
                Iterator<StructurePoint> it = getPointsPlaced().iterator();
                while (it.hasNext()) {
                    StructurePoint p = it.next();

                    Location l = new Location(center.getWorld(),
                    p.getX() + center.getBlockX(), p.getY() + center.getBlockY(), p.getZ() + center.getBlockZ());

                    l.getBlock().setType(Material.AIR);

                    it.remove();
                };
            }
        }.runTaskLater(StarConfig.getPlugin(), delay);
    }

    /**
     * <p>Fetches all of the points of this Structure that have been placed.</p>
     * <p>If the structure hasn't been placed, this set will be empty. This is to ensure nothing that was previously there doesn't get removed.</p>
     * @return All Structure Points
     */
    @NotNull
    public abstract Set<StructurePoint> getPointsPlaced();

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

    /**
     * Fetches the localized name of this Structure.
     * @return Localized Name
     */
    @NotNull
    public String getLocalizedName() {
        return StarConfig.getConfig().get(displayKey);
    }

}
