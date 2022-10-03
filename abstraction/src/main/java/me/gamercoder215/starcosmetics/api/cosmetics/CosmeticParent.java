package me.gamercoder215.starcosmetics.api.cosmetics;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

public enum CosmeticParent {

    TRAILS(Material.ARROW, 20, false,
            BaseTrail.PROJECTILE_TRAIL, BaseTrail.GROUND_TRAIL, BaseTrail.SOUND_TRAIL)
    ;

    private final Material icon;

    private final Cosmetic[] children;
    private final String prefix;

    private final int place;

    private final boolean clickToKeys;

    CosmeticParent(String prefix, Material icon, int place, boolean clickToKeys, Cosmetic... children) {
        this.prefix = prefix;
        this.icon = icon;
        this.children = children;
        this.place = place;
        this.clickToKeys = clickToKeys;
    }

    CosmeticParent(Material icon, int place, boolean clickToKeys, Cosmetic... children) {
        this(ChatColor.YELLOW, icon, place, clickToKeys, children);
    }

    CosmeticParent(ChatColor color, Material icon, int place, boolean clickToKeys, Cosmetic... children) {
        this(color.toString(), icon, place, clickToKeys, children);
    }

    CosmeticParent(int hexColor, Material icon, int place, boolean clickToKeys, Cosmetic... children) {
        this(ChatColor.translateAlternateColorCodes('&',
                "&x&" + Integer.toHexString(hexColor).charAt(0) +
                        "&" + Integer.toHexString(hexColor).charAt(1) +
                        "&" + Integer.toHexString(hexColor).charAt(2) +
                        "&" + Integer.toHexString(hexColor).charAt(3) +
                        "&" + Integer.toHexString(hexColor).charAt(4) +
                        "&" + Integer.toHexString(hexColor).charAt(5)), icon, place, clickToKeys, children);
    }

    public String getPrefix() {
        return prefix;
    }

    public List<Cosmetic> getChildren() {
        return Arrays.asList(children);
    }

    public int getPlace() {
        return place;
    }

    public String getDisplayKey() {
        return "cosmetics." + name().toLowerCase();
    }

    public Material getIcon() {
        return this.icon;
    }

    public boolean isClickToKeys() {
        return clickToKeys;
    }
}
