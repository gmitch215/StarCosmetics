package me.gamercoder215.starcosmetics.api.cosmetics;

import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.api.cosmetics.capes.Cape;
import me.gamercoder215.starcosmetics.api.player.StarPlayerUtil;
import me.gamercoder215.starcosmetics.util.Constants;
import me.gamercoder215.starcosmetics.util.StarMaterial;
import me.gamercoder215.starcosmetics.util.StarRunnable;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
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
    public void run(@NotNull Player p, @Nullable CosmeticLocation<?> loc) throws IllegalArgumentException {
        StarRunnable.sync(() -> {
            ArmorStand stand = StarPlayerUtil.checkCape(p);

            Location location = p.getEyeLocation()
                    .subtract(0.0, 2.4, 0.0)
                    .subtract(p.getEyeLocation().getDirection().setY(0.0).multiply(0.4))
                    .setDirection(p.getLocation().getDirection());
            location.setYaw(p.getLocation().getYaw() + 180.0F);
            stand.teleport(location);

            if (this == NORMAL) {
                ItemStack item = (ItemStack) loc.getInput();
                stand.setHelmet(item);
            } else {
                AnimatedItem data = (AnimatedItem) loc.getInput();
                data.tryStart(p);
            }
        });
    }

    @Override
    public @NotNull State getType() {
        return State.valueOf(name());
    }
}
