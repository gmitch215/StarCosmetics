package me.gamercoder215.starcosmetics.api.cosmetics;

import org.bukkit.ChatColor;

public enum CosmeticRarity {

    COMMON(ChatColor.WHITE),
    OCCASIONAL(ChatColor.DARK_GREEN),
    UNCOMMON(ChatColor.GREEN),
    RARE(ChatColor.BLUE),
    EPIC(ChatColor.DARK_PURPLE),
    LEGENDARY(ChatColor.GOLD),
    MYTHICAL(ChatColor.LIGHT_PURPLE),
    ULTRA(ChatColor.AQUA),

    SPECIAL(ChatColor.RED),
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
