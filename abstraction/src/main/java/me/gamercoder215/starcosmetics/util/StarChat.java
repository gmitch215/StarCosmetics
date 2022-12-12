package me.gamercoder215.starcosmetics.util;

import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

public final class StarChat {

    public static String hexMessage(@NotNull String hex, @NotNull String msg) {
        StringBuilder b = new StringBuilder();
        for (char c : hex.toCharArray()) b.append("&").append(c);

        return ChatColor.translateAlternateColorCodes('&', "&x" + b + msg);
    }

}
