package me.gamercoder215.starcosmetics.api.cosmetics.capes;

import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import me.gamercoder215.starcosmetics.api.cosmetics.CosmeticLocation;
import me.gamercoder215.starcosmetics.api.cosmetics.State;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a StarCosmetics Cape.
 */
public interface Cape extends Cosmetic {

    /**
     * Runs a CosmeticLocation based on this parent.
     * @param p Player to use
     * @param loc CosmeticLocation to use
     */
    void run(@NotNull Player p, @Nullable CosmeticLocation<?> loc);

    /**
     * @deprecated use {@link #run(Player, CosmeticLocation)}
     */
    @Override
    @Deprecated
    default void run(Location l, CosmeticLocation<?> cloc) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the type of the cape.
     * @return the type of the cape
     */
    @NotNull
    State getType();

}
