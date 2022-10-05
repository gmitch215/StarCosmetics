package me.gamercoder215.starcosmetics.api.cosmetics.registry;

import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a specific entry for a cosmetic.
 * @since 1.0.0
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
     * <p>Fetches the full key.</p>
     * <p>The full key is constructed as "{@link #getNamespace()}{@code :}{@link #getKey()}".</p>
     * @return Full Key
     * @since 1.0.0
     */
    @NotNull
    default String getFullKey() {
        return getNamespace() + ":" + getKey();
    }

}
