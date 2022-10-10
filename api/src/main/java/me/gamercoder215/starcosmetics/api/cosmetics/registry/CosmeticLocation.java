package me.gamercoder215.starcosmetics.api.cosmetics.registry;

import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.api.cosmetics.CompletionCriteria;
import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import me.gamercoder215.starcosmetics.api.cosmetics.CosmeticRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a specific entry for a cosmetic.
*/
public interface CosmeticLocation {

    /**
     * Fetches the namespace of this cosmetic location that would belong to parent {@link Cosmetic}.
     * @return Cosmetic Namespace
     */
    @NotNull
    String getNamespace();

    /**
     * Fetches the key of this cosmetic location.
     * @return Cosmetic Location Key
     */
    @NotNull
    String getKey();

    /**
     * Fetches the parent of this cosmetic location.
     * @return Parent Cosmetic
     */
    @NotNull
    Cosmetic getParent();

    /**
     * Fetches the rarity of this CosmeticLocation.
     * @return Cosmetic Rarity
     */
    @NotNull
    CosmeticRarity getRarity();

    /**
     * Fetches the criteria required to unlock this CosmeticLocation.
     * @return Completion Criteria
     */
    @NotNull
    CompletionCriteria getCompletionCriteria();

    /**
     * <p>Fetches the full key.</p>
     * <p>The full key is constructed as "{@link #getNamespace()}{@code :}{@link #getKey()}".</p>
     * @return Full Key
     */
    @NotNull
    default String getFullKey() {
        return getNamespace() + ":" + getKey();
    }

    /**
     * Fetches a CosmeticLocation by {@link #getFullKey()}.
     * @param fullKey Full Key
     * @return Cosmetic Location
     */
    @Nullable
    static CosmeticLocation getByFullKey(@Nullable String fullKey) {
        if (fullKey == null) return null;

        return StarConfig.getRegistry().getAllCosmetics()
                .stream()
                .filter(c -> c.getFullKey().equalsIgnoreCase(fullKey))
                .findFirst().orElse(null);
    }

}
