package me.gamercoder215.starcosmetics.api.cosmetics.hat;

import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import me.gamercoder215.starcosmetics.api.cosmetics.State;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a StarCosmetics Hat.
 */
public interface Hat extends Cosmetic {

    /**
     * Gets the Hat's Type.
     * @return The Hat's Type.
     */
    @NotNull
    State getType();

}
