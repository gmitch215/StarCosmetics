package me.gamercoder215.starcosmetics.api.cosmetics.particle;

/**
 * Represents a modifier applied to the amount of Particles used in a shape.
 */
public enum ParticleReducer {

    /**
     * No reduction (divide/multiply by 1)
     */
    NORMAL(1),

    /**
     * Minimal reduction (divide/multiply by 2)
     */
    MINIMAL(2),

    /**
     * Moderate reduction (divide/multiply by 3)
     */
    MODERATE(3),

    /**
     * High reduction (divide/multiply by 5)
     */
    HIGH(5),

    /**
     * Very high reduction (divide/multiply by 7)
     */
    VERY_HIGH(7),

    /**
     * Maximum reduction (divide/multiply by 10)
     */
    MAXIMUM(10),

    ;

    private final int modifier;

    ParticleReducer(int modifier) {
        this.modifier = modifier;
    }

    /**
     * Fetches the divider used to reduce the particle count.
     * @return Divider of this ParticleReducer
     */
    public int getModifier() {
        return modifier;
    }
}
