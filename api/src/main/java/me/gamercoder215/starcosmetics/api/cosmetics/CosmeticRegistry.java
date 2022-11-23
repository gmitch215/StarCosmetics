package me.gamercoder215.starcosmetics.api.cosmetics;

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
    List<CosmeticLocation<?>> getAllFor(@Nullable Class<? extends Cosmetic> parentClass);

    /**
     * Fetches a list of Locations for a specific Cosmetic.
     * @param parent Parent Cosmetic
     * @return List of Cosmetic Locations
     */
    @NotNull
    List<CosmeticLocation<?>> getAllFor(@Nullable Cosmetic parent);

    /**
     * Fetches a list of all registered CosmeticLocations.
     *
     * @return List of Cosmetic Locations
     */
    @NotNull
    default List<CosmeticLocation<?>> getAllCosmetics() {
        return getAllFor(Cosmetic.class);
    }

    /**
     * Fetches a list of all registered Cosmetic Parents.
     * @return List of Registered Cosmetic Parents
     */
    @NotNull
    List<Cosmetic> getAllParents();

    /**
     * Fetches a Cosmetic by its namespace.
     * @param key Cosmetic Namespace
     * @return Cosmetic Parent
     */
    @Nullable
    Cosmetic getByNamespace(@NotNull String key);

}
