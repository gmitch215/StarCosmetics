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

    // Special with no visible requirements
    /**
     * Represents the special rarity. This rarity is the only one with no visible requirements.
     */
    SPECIAL(ChatColor.RED, true),
    ;

    private final String prefix;
    private final boolean invisibleRequirements;

    Rarity(String prefix, boolean visible) {
        this.prefix = prefix;
        this.invisibleRequirements = visible;
    }

    Rarity(String prefix) {
        this(prefix, false);
    }

    Rarity(ChatColor color) {
        this(String.valueOf(color));
    }

    Rarity(ChatColor color, boolean secret) { this(String.valueOf(color), secret); }

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
     * Whether this rarity has an invisible Completion Requirement.
     * @return true if requirement is invisible, false otherwise
     */
    public boolean hasInvisibleRequirements() {
        return invisibleRequirements;
    }
}
