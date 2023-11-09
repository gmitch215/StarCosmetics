package me.gamercoder215.starcosmetics.api.cosmetics;

import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.api.cosmetics.capes.Cape;
import me.gamercoder215.starcosmetics.util.Constants;
import me.gamercoder215.starcosmetics.util.StarMaterial;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static me.gamercoder215.starcosmetics.util.StarUtil.local;

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

    private static final Map<UUID, ArmorStand> CAPES = new HashMap<>();

    @Override
    public void run(@NotNull Player p, @Nullable CosmeticLocation<?> loc) throws IllegalArgumentException {
        if (!ItemStack.class.isAssignableFrom(loc.getInputType()) && !AnimatedItem.class.isAssignableFrom(loc.getInputType())) return;

        ArmorStand stand = checkCape(p);

        Location location = local(p.getLocation(), new Vector(0.0, -2.0, -0.5));
        location.setYaw(p.getLocation().getYaw() + 180.0F);
        stand.teleport(location);

        if (this == NORMAL) {
            ItemStack item = (ItemStack) loc.getInput();
            stand.setChestplate(item);
        } else {
            AnimatedItem data = (AnimatedItem) loc.getInput();
            data.tryStart(p);
        }
    }

    public static ArmorStand checkCape(@NotNull Player p) {
        ArmorStand stand = CAPES.get(p.getUniqueId());
        if (stand == null) {
            stand = p.getWorld().spawn(p.getLocation(), ArmorStand.class);
            stand.setInvulnerable(true);
            stand.setCollidable(false);
            stand.setVisible(false);
            stand.setArms(true);
            stand.setGravity(false);
            stand.setMarker(true);
            stand.setBasePlate(false);

            CAPES.put(p.getUniqueId(), stand);
        }

        return stand;
    }

    public static void removeCape(@NotNull Player p) {
        ArmorStand stand = CAPES.get(p.getUniqueId());
        if (stand != null) {
            stand.remove();
            CAPES.remove(p.getUniqueId());
        }
    }

    public static void setCape(@NotNull Player p, @NotNull ItemStack item) {
        checkCape(p).setChestplate(item);
    }

    @Override
    public @NotNull State getType() {
        return State.valueOf(name());
    }
}
