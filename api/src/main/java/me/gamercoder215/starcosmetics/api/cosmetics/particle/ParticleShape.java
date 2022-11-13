package me.gamercoder215.starcosmetics.api.cosmetics.particle;

import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a StarCosmetics Particle Shape.
*/
public interface ParticleShape extends Cosmetic {

    /**
     * Fetches the size of this ParticleShape.
     * @return Particle Size
     */
    @NotNull
    ParticleSize getSize();

}
