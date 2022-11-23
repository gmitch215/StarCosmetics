package me.gamercoder215.starcosmetics.api.cosmetics;

import me.gamercoder215.starcosmetics.api.CompletionCriteria;
import me.gamercoder215.starcosmetics.api.Rarity;
import me.gamercoder215.starcosmetics.api.StarConfig;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a specific entry for a cosmetic.
 * @param <T> Input Type
*/
public interface CosmeticLocation<T> {

    /**
     * Fetches the namespace of this cosmetic location that would belong to parent {@link Cosmetic}.
     * @return Cosmetic Namespace
     */
    @NotNull
    default String getNamespace() {
        return getParent().getNamespace();
    }

    /**
     * Whether this Player has unlocked this cosmetic.
     * @param p Player to test
     * @return true if unlocked, else false
     */
    default boolean isUnlocked(@NotNull Player p) {
        if (p == null) return false;
        return getCompletionCriteria().getCriteria().test(p);
    }

    /**
     * Fetches the key of this cosmetic location.
     * @return Cosmetic Location Key
     */
    @NotNull
    String getKey();

    /**
     * Fetches the display name of this cosmetic location.
     * @return Cosmetic Location Display Name
     */
    @NotNull
    String getDisplayName();

    /**
     * Fetches the parent of this cosmetic location.
     * @return Parent Cosmetic
     */
    @NotNull
    Cosmetic getParent();

    /**
     * Fetches the class of {@link #getParent()}.
     * @return Parent Cosmetic Class
     */
    Class<? extends Cosmetic> getParentClass();

    /**
     * Fetches the rarity of this CosmeticLocation.
     * @return Cosmetic Rarity
     */
    @NotNull
    Rarity getRarity();

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
        return getNamespace() + "[" + getKey() + "]";
    }

    /**
     * Fetches the input type of this CosmeticLocation.
     * @return Class of Input Type
     */
    @NotNull
    Class<? extends T> getInputType();

    /**
     * Fetches the input used in {@link Cosmetic#run(Location, CosmeticLocation)}.
     * @return CosmeticLocation Input for Parent Cosmetic
     */
    @NotNull
    T getInput();

    /**
     * Fetches a CosmeticLocation by {@link #getFullKey()}.
     * @param fullKey Full Key
     * @return Cosmetic Location
     */
    @Nullable
    static CosmeticLocation<?> getByFullKey(@Nullable String fullKey) {
        if (fullKey == null) return null;

        return StarConfig.getRegistry().getAllCosmetics()
                .stream()
                .filter(c -> c.getFullKey().equalsIgnoreCase(fullKey))
                .findFirst().orElse(null);
    }

}
