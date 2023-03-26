package me.gamercoder215.starcosmetics.api.cosmetics.hat;

import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import me.gamercoder215.starcosmetics.api.cosmetics.CosmeticLocation;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a StarCosmetics Hat.
 */
public interface Hat extends Cosmetic {

    /**
     * Runs a CosmeticLocation based on this parent.
     * @param p Player to use
     * @param loc CosmeticLocation to use
     */
    void run(@NotNull Player p, CosmeticLocation<?> loc);

    /**
     * @deprecated use {@link #run(Player, CosmeticLocation)}
     */
    @Override
    @Deprecated
    default void run(Location l, CosmeticLocation<?> cloc) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the Hat's Type.
     * @return The Hat's Type.
     */
    @NotNull
    HatType getType();

}
