package me.gamercoder215.starcosmetics.api.cosmetics.capes;

import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import me.gamercoder215.starcosmetics.api.cosmetics.State;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a StarCosmetics Cape.
 */
public interface Cape extends Cosmetic {

    /**
     * Gets the type of the cape.
     * @return the type of the cape
     */
    @NotNull
    State getType();

}
