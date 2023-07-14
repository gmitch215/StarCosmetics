package me.gamercoder215.starcosmetics.api.cosmetics;

import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.api.cosmetics.gadget.Gadget;
import me.gamercoder215.starcosmetics.util.Constants;
import org.bukkit.Location;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum BaseGadget implements Gadget {

    INSTANCE

    ;

    BaseGadget() {
        Constants.PARENTS.add(this);
    }

    @Override
    public @NotNull String getNamespace() { return "gadget"; }

    @Override
    public @NotNull String getDisplayName() { return StarConfig.getConfig().get("menu.cosmetics.choose.gadget"); }

    @Override
    public @NotNull Material getIcon() { return Material.FIREWORK; }

    @Override
    public void run(@Nullable Location l, @NotNull CosmeticLocation<?> location) throws IllegalArgumentException {

    }
}
