package me.gamercoder215.starcosmetics.api.cosmetics;

import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a rarity used in a Cosmetic.
 */
public enum CosmeticRarity {

    /**
     * Represents the common rarity.
     */
    COMMON(ChatColor.WHITE),

    /**
     * Represents the occasional rarity.
     */
    OCCASIONAL(ChatColor.DARK_GREEN),

    /**
     * Represents the uncommon rarity.
     */
    UNCOMMON(ChatColor.GREEN),
    /**
     * Represents the rare rarity.
     */
    RARE(ChatColor.BLUE),
    /**
     * Represents the epic rarity.
     */
    EPIC(ChatColor.DARK_PURPLE),
    /**
     * Represents the legendary rarity.
     */
    LEGENDARY(ChatColor.GOLD),
    /**
     * Represents the mythical rarity.
     */
    MYTHICAL(ChatColor.LIGHT_PURPLE),
    /**
     * Represents the ultra rarity.
     */
    ULTRA(ChatColor.AQUA),

    // Special / Secret with no visible requirements
    /**
     * <p>Represents the special rarity.</p>
     * <p>This rarity is one of two rarities that have no visible requirements in their description.</p>
     */
    SPECIAL(ChatColor.RED),

    /**
     * <p>Represents the secret rarity.</p>
     * <p>This rarity is one of two rarities that have no visible requirements in their description.</p>
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

    /**
     * Fetches the prefix that this prefix uses.
     * @return String Preifx
     */
    @NotNull
    public String getPrefix() {
        return prefix;
    }

    @Override
    public String toString() {
        return getPrefix();
    }
}
