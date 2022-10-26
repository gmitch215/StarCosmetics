package me.gamercoder215.starcosmetics.api.cosmetics.trail;

/**
 * Represents what the Trail will specifically apply to
 */
public enum TrailType {

    /**
     * Represents a Trail that will apply to a Player's thrown Projectiles
     */
    PROJECTILE,

    /**
     * Represents a Trail that will also apply to a Player's thrown Projectiles, but only with Sound 
     */
    PROJECTILE_SOUND,

    /**
     * Represents a Trail that will follow around a Player's Movements
     */
    GROUND,

    
}
