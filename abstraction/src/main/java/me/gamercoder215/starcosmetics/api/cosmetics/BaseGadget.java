package me.gamercoder215.starcosmetics.api.cosmetics;

import org.bukkit.Material;

public enum BaseGadget implements CosmeticKey {

    CLICK_GADGET
    ;

    @Override
    public String getNamespace() {
        return name().toLowerCase();
    }

    @Override
    public String getDisplayKey() {
        return "cosmetic." + getNamespace();
    }

    @Override
    public Material getIcon() {
        return Material.HOPPER;
    }

    @Override
    public void accept(Object... args) {
    }
}
