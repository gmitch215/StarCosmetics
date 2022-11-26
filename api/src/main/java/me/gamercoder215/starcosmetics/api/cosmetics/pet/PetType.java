package me.gamercoder215.starcosmetics.api.cosmetics.pet;

import me.gamercoder215.starcosmetics.api.Rarity;
import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.api.cosmetics.pet.custom.HeadPet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents all of the Pet Types.
 */
public enum PetType {

    /**
     * Represents the {@link GolemPet}.
     */
    GOLEM(GolemPet.class),

    /**
     * Represents the {@link PigPet}.
     */
    PIG(PigPet.class),

    // Head Pets

    /**
     * Represents a base {@link HeadPet}.
     */
    HEAD(HeadPet.class),

    /**
     * Represents a Bee on a {@link HeadPet}.
     */
    BEE(HeadPet.class)
    ;

    private final Class<? extends Pet> petClass;

    PetType(Class<? extends Pet> petClass) {
        this.petClass = petClass;
    }

    /**
     * Fetches the Pet Class.
     * @return Pet Class
     */
    @NotNull
    public Class<? extends Pet> getPetClass() {
        return petClass;
    }

    /**
     * Fetches the Pet Information associated with this PetType.
     * @return Pet Information
     */
    @NotNull
    public PetInfo getInfo() {
        return StarConfig.getRegistry().getPetInfo(this);
    }

    /**
     * Fetches this PetType's Rarity.
     * @return Pet Rarity
     */
    @NotNull
    public Rarity getRarity() {
        return getInfo().getRarity();
    }

    /**
     * Fetches the Pet Type from a Pet Class.
     * @param petClass Pet Class
     * @return Pet Type
     */
    @Nullable
    public static PetType fromPetClass(@Nullable Class<? extends Pet> petClass) {
        for (PetType t : PetType.values()) if (petClass.isAssignableFrom(t.petClass)) return t;

        return null;
    }
}
