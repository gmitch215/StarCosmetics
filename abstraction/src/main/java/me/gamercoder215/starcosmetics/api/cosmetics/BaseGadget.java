package me.gamercoder215.starcosmetics.api.cosmetics;

import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.api.cosmetics.registry.CosmeticLocation;
import me.gamercoder215.starcosmetics.util.selection.GadgetSelection;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

public enum BaseGadget implements Gadget {

    CLICK_GADGET
    ;

    BaseGadget() {}

    @Override
    public String getNamespace() {
        return name().toLowerCase();
    }

    @Override
    public String getDisplayName() {
        return StarConfig.getConfig().get("cosmetic.gadget" + getNamespace());
    }

    @Override
    public Material getIcon() {
        return Material.HOPPER;
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void run(@NotNull Location l, @NotNull CosmeticLocation<?> location) throws IllegalArgumentException {
        GadgetSelection sel = (GadgetSelection) location;
        sel.run((Event) location.getInput());
    }

}
