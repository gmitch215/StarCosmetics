package me.gamercoder215.starcosmetics.api.cosmetics.registry;

import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Represents the registry for all StarCosmetics Cosmetics.
 * @since 1.0.0
 */
public interface CosmeticRegistry {

    /**
     * Fetches a list of Locations for a specific Cosmetic.
     * @param parentClass Parent Cosmetic Class
     * @return List of Cosmetic Locations
     * @since 1.0.0
     */
    @NotNull
    List<CosmeticLocation> getAllFor(@Nullable Class<Cosmetic> parentClass);

}
