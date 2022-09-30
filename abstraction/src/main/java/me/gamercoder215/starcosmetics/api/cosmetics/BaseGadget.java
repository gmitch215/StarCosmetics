package me.gamercoder215.starcosmetics.api.cosmetics;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.bukkit.Material;

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

    @Override
    public void accept(Object... args) {
        consumer.accept(Arrays.asList(args));
    }
}
