package me.gamercoder215.starcosmetics.api.cosmetics.pet;

import me.gamercoder215.starcosmetics.api.cosmetics.particle.ParticleShape;

/**
 * <p>Setting used to determine whether or not a Pet replicates the cosmetics of its owner.</p>
 * <p>This setting will only apply to:</p>
 * <ul>
 *     <li>{@link ParticleShape}</li>
 * </ul>
 * <p>More may be added in the future.</p>
 */
public enum PetCosmetics {

    /**
     * Applicable Cosmetics will only apply to you (Default).
     */
    OWNER_ONLY(false, false),

    /**
     * Applicable Cosmetics will apply to you and your StarCosmetics Pet.
     */
    STARCOSMETICS_PET_ONLY(true, false),

    /**
     * Applicable Cosmetics will apply to you and any Tameables you own.
     */
    TAMEABLES_ONLY(false, true),

    /**
     * Applicable Cosmetics will apply to you, Tameables you own, and your StarCosmetics Pet.
     */
    ALL(true, true),

    ;

    private final boolean starPet;
    private final boolean tameables;

    PetCosmetics(boolean starPet, boolean tameables) {
        this.starPet = starPet;
        this.tameables = tameables;
    }

    /**
     * Whether the setting applies to StarCosmetics Pets.
     * @return true if applies, false otherwise
     */
    public boolean isStarPet() {
        return starPet;
    }

    /**
     * Whether the setting applies to Tameables.
     * @return true if applies, false otherwise
     */
    public boolean isTameables() {
        return tameables;
    }
}
