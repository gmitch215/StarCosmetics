package me.gamercoder215.starcosmetics.api.cosmetics.registry;

import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Represents the registry for all StarCosmetics Cosmetics.
*/
public interface CosmeticRegistry {

    /**
     * Fetches a list of Locations for a specific Cosmetic.
     * @param parentClass Parent Cosmetic Class
     * @return List of Cosmetic Locations
     */
    @NotNull
    List<CosmeticLocation> getAllFor(@Nullable Class<Cosmetic> parentClass);

    /**
     * Fetches a list of all registered CosmeticLocations.
     * @return List of Cosmetic Locations
     */
    @NotNull
    default List<CosmeticLocation> getAllCosmetics() {
        return getAllFor(Cosmetic.class);
    }

}
