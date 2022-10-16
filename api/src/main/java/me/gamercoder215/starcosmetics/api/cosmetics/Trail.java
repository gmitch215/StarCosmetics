package me.gamercoder215.starcosmetics.api.cosmetics;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

import me.gamercoder215.starcosmetics.api.cosmetics.registry.CosmeticLocation;

/**
 * Represents a StarCosmetics Trail
 * @param <T> Trail Type (Projectile Trails, Entity Trails, etc.)
 */
public interface Trail<T> extends Cosmetic {

    /**
     * Fetches the trail type.
     * @return Trail Type
     */
    @NotNull
    Class<T> getTrailType();

    /**
     * Runs a CosmeticLocation based on this parent.
     * @param en Entity to use
     * @param loc CosmeticLocation to use
     */
    void run(@NotNull Entity en, CosmeticLocation<?> loc);

    /**
     * @deprecated use {@link #run(Entity, CosmeticLocation)}
     */
    @Deprecated
    default void run(Location l, CosmeticLocation<?> cloc) {
        throw new UnsupportedOperationException();
    }

}
