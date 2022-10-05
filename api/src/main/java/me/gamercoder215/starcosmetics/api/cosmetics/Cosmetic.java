package me.gamercoder215.starcosmetics.api.cosmetics;

import org.bukkit.Material;

/**
 * Parent Interface for Base Cosmetics, ready for material, particle, or entity implementations.
 * @since 1.0.0m
 */
public interface Cosmetic {

    /**
     * Fetches the namespace of this Comsmetic.
     * @return Cosmetic Namespace
     */
    String getNamespace();

    /**
     * Fetches the display name of this cosmetic.
     * @return Display name of Cosmetic
     */
    String getDisplayName();

    /**
     * Fetches the icon of this cosmetic.
     * @return The icon of this cosmetic.
     */
    Material getIcon();

}
