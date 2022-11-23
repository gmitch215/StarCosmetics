package me.gamercoder215.starcosmetics.api;

import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a rarity used in a Cosmetic, Completion, or anything else.
 */
public enum Rarity {

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
    SPECIAL(ChatColor.RED, true),

    /**
     * <p>Represents the secret rarity.</p>
     * <p>This rarity is one of two rarities that have no visible requirements in their description.</p>
     */
    SECRET(ChatColor.DARK_RED, true),
    ;

    private final String prefix;
    private final boolean secret;

    Rarity(String prefix, boolean secret) {
        this.prefix = prefix;
        this.secret = secret;
    }

    Rarity(String prefix) {
        this(prefix, false);
    }

    Rarity(ChatColor color) {
        this(color + "");
    }

    Rarity(ChatColor color, boolean secret) { this(color + "", secret); }

    Rarity(int hexColor) {
        this(ChatColor.translateAlternateColorCodes('&',
                "&x&" + Integer.toHexString(hexColor).charAt(0) +
                        "&" + Integer.toHexString(hexColor).charAt(1) +
                        "&" + Integer.toHexString(hexColor).charAt(2) +
                        "&" + Integer.toHexString(hexColor).charAt(3) +
                        "&" + Integer.toHexString(hexColor).charAt(4) +
                        "&" + Integer.toHexString(hexColor).charAt(5)));
    }

    @Override
    @NotNull
    public String toString() {
        return prefix + ChatColor.BOLD + name();
    }

    /**
     * Fetches the Rarity's prefix, to mimic the Rarity Color.
     * @return Rarity Prefix
     */
    @NotNull
    public String getPrefix() {
        return prefix;
    }

    /**
     * Whether this rarity has a visible Completion Requirement.
     * @return true if requirement is visible, false otherwise
     */
    public boolean isSecret() {
        return secret;
    }
}
