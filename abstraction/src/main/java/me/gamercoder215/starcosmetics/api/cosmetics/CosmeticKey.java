package me.gamercoder215.starcosmetics.api.cosmetics;

import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.wrapper.Wrapper;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.security.SecureRandom;

public interface CosmeticKey {

    SecureRandom r = new SecureRandom();

    Wrapper w = StarConfig.getWrapper();

    String getNamespace();

    String name();

    String getDisplayKey();

    Material getIcon();

    default String getDisplayPrefix() {
        return ChatColor.YELLOW + "";
    }

    void accept(Object... args);

}
