package me.gamercoder215.starcosmetics.api.cosmetics;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Parent Interface for Base Cosmetics, ready for material, particle, or entity implementations.
 */
public interface Cosmetic {

    /**
     * Fetches the namespace of this Comsmetic.
     * @return Cosmetic Namespace
     */
    @NotNull
    String getNamespace();

    /**
     * Fetches the display name of this cosmetic.
     * @return Display name of Cosmetic
     */
    @NotNull
    String getDisplayName();

    /**
     * Fetches the inventory icon of this cosmetic.
     * @return The Icon of this cosmetic.
     */
    @NotNull
    Material getIcon();

    /**
     * <p>Runs a CosmeticLocation based on this parent.</p>
     * <p>Note: Some implementations of this interface have this method deprecated in favor of the first parameter being {@link Entity} or a sub-interface.
     * Casting when running this method is recommended.</p>
     * @param l Bukkit Location to use at (not necessary for some Cosmetics)
     * @param location CosmeticLocation to input
     * @throws IllegalArgumentException if location is null or is not supported
     */
    void run(@Nullable Location l, @NotNull CosmeticLocation<?> location) throws IllegalArgumentException;

}
