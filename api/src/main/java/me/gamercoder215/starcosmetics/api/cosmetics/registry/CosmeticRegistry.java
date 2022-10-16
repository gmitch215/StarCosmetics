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
     * @param <T> Parent Cosmetic Class Type
     * @return List of Cosmetic Locations
     */
    @NotNull
    <T extends Cosmetic> List<CosmeticLocation<? extends T>> getAllFor(@Nullable Class<T> parentClass);

    /**
     * Fetches a list of all registered CosmeticLocations.
     * @return List of Cosmetic Locations
     */
    @NotNull
    default List<CosmeticLocation<? extends Cosmetic>> getAllCosmetics() {
        return getAllFor(Cosmetic.class);
    }

}
