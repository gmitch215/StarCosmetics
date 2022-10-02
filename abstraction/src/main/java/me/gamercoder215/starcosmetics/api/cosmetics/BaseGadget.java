package me.gamercoder215.starcosmetics.api.cosmetics;

import org.bukkit.Material;

import java.util.List;
import java.util.function.Consumer;

public enum BaseGadget implements Gadget {

    CLICK_GADGET
    ;

    private final Consumer<List<Object>> consumer;

    BaseGadget(Consumer<List<Object>> args) {
        this.consumer = args;
    }

    BaseGadget() { this((args) -> {}); }

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

}
