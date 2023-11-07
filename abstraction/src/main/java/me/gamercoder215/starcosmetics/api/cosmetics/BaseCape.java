package me.gamercoder215.starcosmetics.api.cosmetics;

import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.api.cosmetics.capes.Cape;
import me.gamercoder215.starcosmetics.util.Constants;
import me.gamercoder215.starcosmetics.util.StarMaterial;
import org.bukkit.Location;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum BaseCape implements Cape {

    NORMAL,
    ANIMATED

    ;

    BaseCape() { Constants.PARENTS.add(this); }

    @Override
    public @NotNull String getNamespace() {
        return "cape/" + name().toLowerCase();
    }

    @Override
    public @NotNull String getDisplayName() {
        return StarConfig.getConfig().get("menu.cosmetics.capes." + name().toLowerCase());
    }

    @Override
    public @NotNull Material getIcon() {
        return this == NORMAL ? StarMaterial.WHITE_BANNER.find() : StarMaterial.LIME_BANNER.find();
    }

    @Override
    public void run(@Nullable Location l, @NotNull CosmeticLocation<?> location) throws IllegalArgumentException {

    }

    @Override
    public @NotNull State getType() {
        return State.valueOf(name());
    }
}
