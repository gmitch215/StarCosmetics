package me.gamercoder215.starcosmetics.api.cosmetics;

import org.bukkit.ChatColor;

/**
 * Represents a rarity used in a Cosmetic.
 * @since 1.0.0
 */
public enum CosmeticRarity {

    /**
     * Represents the common rarity.
     * @since 1.0.0
     */
    COMMON(ChatColor.WHITE),

    /**
     * Represents the occasional rarity.
     * @since 1.0.0
     */
    OCCASIONAL(ChatColor.DARK_GREEN),

    /**
     * Represents the uncommon rarity.
     * @since 1.0.0
     */
    UNCOMMON(ChatColor.GREEN),
    /**
     * Represents the rare rarity.
     * @since 1.0.0
     */
    RARE(ChatColor.BLUE),
    /**
     * Represents the epic rarity.
     * @since 1.0.0
     */
    EPIC(ChatColor.DARK_PURPLE),
    /**
     * Represents the legendary rarity.
     * @since 1.0.0
     */
    LEGENDARY(ChatColor.GOLD),
    /**
     * Represents the mythical rarity.
     * @since 1.0.0
     */
    MYTHICAL(ChatColor.LIGHT_PURPLE),
    /**
     * Represents the ultra rarity.
     * @since 1.0.0
     */
    ULTRA(ChatColor.AQUA),

    // Special / Secret with no visible requirements
    /**
     * <p>Represents the special rarity.</p>
     * <p>This rarity is one of two rarities that have no visible requirements in their description.</p>
     * @since 1.0.0
     */
    SPECIAL(ChatColor.RED),

    /**
     * <p>Represents the secret rarity.</p>
     * <p>This rarity is one of two rarities that have no visible requirements in their description.</p>
     * @since 1.0.0
     */
    SECRET(ChatColor.DARK_RED),
    ;

    private final String prefix;

    CosmeticRarity(String prefix) {
        this.prefix = prefix;
    }

    CosmeticRarity(ChatColor color) {
        this(color + "");
    }

    CosmeticRarity(int hexColor) {
        this(ChatColor.translateAlternateColorCodes('&',
                "&x&" + Integer.toHexString(hexColor).charAt(0) +
                        "&" + Integer.toHexString(hexColor).charAt(1) +
                        "&" + Integer.toHexString(hexColor).charAt(2) +
                        "&" + Integer.toHexString(hexColor).charAt(3) +
                        "&" + Integer.toHexString(hexColor).charAt(4) +
                        "&" + Integer.toHexString(hexColor).charAt(5)));
    }

    public String getPrefix() {
        return prefix;
    }
}
