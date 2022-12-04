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
    HEAD,

    /**
     * Represents a Bee on a {@link HeadPet}.
     */
    BEE,

    /**
     * Represents a Rabbit on a {@link HeadPet}.
     */
    RABBIT,

    /**
     * Represents a Giraffe on a {@link HeadPet}.
     */
    GIRAFFE,

    /**
     * Represents a Dolphin on a {@link HeadPet}.
     */
    DOLPHIN,

    /**
     * Represents a Llama on a {@link HeadPet}.
     */
    LLAMA,

    /**
     * Represents a Polar Bear on a {@link HeadPet}.
     */
    POLAR_BEAR,

    /**
     * Represents an Elephant on a {@link HeadPet}.
     */
    ELEPHANT,

    /**
     * Represents a Panda on a {@link HeadPet}.
     */
    PANDA,

    /**
     * Represents a Fox on a {@link HeadPet}.
     */
    FOX,

    /**
     * Represents a Narwhal on a {@link HeadPet}.
     */
    NARWHAL,

    /**
     * Represents a Pufferfish on a {@link HeadPet}.
     */
    PUFFERFISH,

    /**
     * Represents a Gorilla on a {@link HeadPet}.
     */
    GORILLA,

    /**
     * Represents a Strider on a {@link HeadPet}.
     */
    STRIDER,

    /**
     * Represents a Tardigrade on a {@link HeadPet}.
     */
    TARDIGRADE,

    /**
     * Represents a Hummingbird on a {@link HeadPet}.
     */
    HUMMINGBIRD,

    /**
     * Represents an Axolotl on a {@link HeadPet}.
     */
    AXOLOTL,

    /**
     * Represents a Capybara on a {@link HeadPet}.
     */
    CAPYBARA,

    /**
     * Represents a Mouse on a {@link HeadPet}.
     */
    MOUSE,

    /**
     * Represents a Tiger on a {@link HeadPet}.
     */
    TIGER,

    /**
     * Represents a Blaze on a {@link HeadPet}.
     */
    BLAZE,

    /**
     * Represents a Jellyfish on a {@link HeadPet}.
     */
    JELLYFISH,

    /**
     * Represents a Whale on a {@link HeadPet}.
     */
    WHALE,

    /**
     * Represents a Slime on a {@link HeadPet}.
     */
    SLIME
    ;

    private final Class<? extends Pet> petClass;

    PetType() { this(HeadPet.class); }

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
