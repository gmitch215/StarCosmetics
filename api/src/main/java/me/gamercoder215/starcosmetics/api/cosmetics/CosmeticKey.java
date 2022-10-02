package me.gamercoder215.starcosmetics.api.cosmetics;

import org.bukkit.ChatColor;
import org.bukkit.Material;

/**
 * Parent Interface for Cosmetics.
 * @since 1.0.0m
 */
public interface CosmeticKey {

    String getNamespace();

    String name();

    String getDisplayKey();

    Material getIcon();

    default String getDisplayPrefix() {
        return ChatColor.YELLOW + "";
    }

}
