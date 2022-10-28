package me.gamercoder215.starcosmetics.api.cosmetics;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

public enum CosmeticParent {

    TRAILS(Material.ARROW, 20,
            BaseTrail.PROJECTILE_TRAIL, BaseTrail.GROUND_TRAIL, BaseTrail.SOUND_TRAIL),

    ;

    private final Material icon;

    private final Cosmetic[] children;
    private final String prefix;

    private final int place;

    CosmeticParent(String prefix, Material icon, int place, Cosmetic... children) {
        this.prefix = prefix;
        this.icon = icon;
        this.children = children;
        this.place = place;
    }

    CosmeticParent(Material icon, int place, Cosmetic... children) {
        this(ChatColor.YELLOW, icon, place, children);
    }

    CosmeticParent(ChatColor color, Material icon, int place, Cosmetic... children) {
        this(color.toString(), icon, place, children);
    }

    CosmeticParent(int hexColor, Material icon, int place, Cosmetic... children) {
        this(ChatColor.translateAlternateColorCodes('&',
                "&x&" + Integer.toHexString(hexColor).charAt(0) +
                        "&" + Integer.toHexString(hexColor).charAt(1) +
                        "&" + Integer.toHexString(hexColor).charAt(2) +
                        "&" + Integer.toHexString(hexColor).charAt(3) +
                        "&" + Integer.toHexString(hexColor).charAt(4) +
                        "&" + Integer.toHexString(hexColor).charAt(5)), icon, place, children);
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
        return "menu.cosmetics." + name().toLowerCase();
    }

    public Material getIcon() {
        return this.icon;
    }

}
