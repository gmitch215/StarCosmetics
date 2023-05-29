package me.gamercoder215.starcosmetics.api.cosmetics;

import me.gamercoder215.starcosmetics.api.cosmetics.hat.Hat;
import me.gamercoder215.starcosmetics.api.cosmetics.trail.Trail;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

public enum CosmeticParent {

    TRAILS(Material.ARROW, 20, Trail.class,
            BaseTrail.PROJECTILE_TRAIL, BaseTrail.GROUND_TRAIL, BaseTrail.SOUND_TRAIL),

    HATS(Material.LEATHER_HELMET, 22, Hat.class,
            BaseHat.NORMAL, BaseHat.ANIMATED)

    ;

    private final Material icon;

    private final Cosmetic[] children;

    private final Class<? extends Cosmetic> childClass;
    private final String prefix;

    private final int place;

    @SafeVarargs
    <T extends Cosmetic> CosmeticParent(String prefix, Material icon, int place, Class<T> childClass, T... children) {
        this.prefix = prefix;
        this.icon = icon;
        this.children = children;
        this.place = place;
        this.childClass = childClass;
    }

    @SafeVarargs
    <T extends Cosmetic> CosmeticParent(Material icon, int place, Class<T> childClass, T... children) {
        this(ChatColor.YELLOW, icon, place, childClass, children);
    }

    @SafeVarargs
    <T extends Cosmetic> CosmeticParent(ChatColor color, Material icon, int place, Class<T> childClass, T... children) {
        this(color.toString(), icon, place, childClass, children);
    }

    @SafeVarargs
    <T extends Cosmetic> CosmeticParent(int hexColor, Material icon, int place, Class<T> childClass, T... children) {
        this(ChatColor.translateAlternateColorCodes('&',
                "&x&" + Integer.toHexString(hexColor).charAt(0) +
                        "&" + Integer.toHexString(hexColor).charAt(1) +
                        "&" + Integer.toHexString(hexColor).charAt(2) +
                        "&" + Integer.toHexString(hexColor).charAt(3) +
                        "&" + Integer.toHexString(hexColor).charAt(4) +
                        "&" + Integer.toHexString(hexColor).charAt(5)), icon, place, childClass, children);
    }

    public String getPrefix() {
        return prefix;
    }

    public Class<? extends Cosmetic> getChildClass() {
        return childClass;
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
