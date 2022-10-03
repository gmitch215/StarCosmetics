package me.gamercoder215.starcosmetics.api.cosmetics;

import org.bukkit.Material;

/**
 * Parent Interface for Cosmetics.
 * @since 1.0.0m
 */
public interface Cosmetic {

    /**
     * Fetches the namespace of this Comsmetic.
     * @return The namespace of this Cosmetic.
     */
    String getNamespace();

    /**
     * Fetches the key used to display the name of this Cosmetic.
     * @return The key used to display the name of this Cosmetic.
     */
    String getDisplayKey();

    /**
     * Fetches the icon of this cosmetic.
     * @return The icon of this cosmetic.
     */
    Material getIcon();

}
