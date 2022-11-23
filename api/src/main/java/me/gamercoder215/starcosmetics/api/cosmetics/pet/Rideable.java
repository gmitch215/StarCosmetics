package me.gamercoder215.starcosmetics.api.cosmetics.pet;

/**
 * Represents a Rideable Pet.
 */
public interface Rideable extends Pet {

    /**
     * The Base Y Motion of the Rideable.
     */
    double BASE_Y_MOTION = 0.30000001192092896D;

    /**
     * The Y Collision Modifier, added to its position, when detecting if the entity can move.
     */
    double Y_COLLISION_MODIFIER = 0.6000000238418579D;

    /**
     * The constant that is multiplied by an entity's Y movement, when moving in water.
     */
    double WATER_Y_MOVEMENT_MODIFIER = 0.800000011920929D;

    /**
     * The Base Friction Value of the Rideable.
     */
    float FRICTION = 0.91F;

    /**
     * Fetches the speed this Pet should move at.
     * @return Pet Walking Speed
     */
    double getWalkSpeed();

    /**
     * Whether this Rideable has a rider.
     * @return true if has rider, else false
     */
    boolean hasRider();

    /**
     * Whether this rideable is flying.
     * @return true if flying, else false
     */
    boolean isFlying();

}
