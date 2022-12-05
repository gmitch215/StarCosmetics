package me.gamercoder215.starcosmetics.api.cosmetics.pet;

import me.gamercoder215.starcosmetics.api.Rarity;
import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.api.cosmetics.pet.custom.HeadPet;
import org.bukkit.Sound;
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
    PIG(PigPet.class, Sound.ENTITY_PIG_AMBIENT),

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
    DOLPHIN(Sound.ENTITY_PLAYER_SPLASH),

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
    FOX("ENTITY_FOX_AMBIENT"),

    /**
     * Represents a Narwhal on a {@link HeadPet}.
     */
    NARWHAL("ENTITY_DROWNED_SWIM", 1.5F),

    /**
     * Represents a Pufferfish on a {@link HeadPet}.
     */
    PUFFERFISH("ENTITY_PUFFER_FISH_FLOP"),

    /**
     * Represents a Gorilla on a {@link HeadPet}.
     */
    GORILLA("ENTITY_PANDA_SNEEZE", 0F),

    /**
     * Represents a Strider on a {@link HeadPet}.
     */
    STRIDER("ENTITY_STRIDER_AMBIENT"),

    /**
     * Represents a Tardigrade on a {@link HeadPet}.
     */
    TARDIGRADE,

    /**
     * Represents a Hummingbird on a {@link HeadPet}.
     */
    HUMMINGBIRD("ENTITY_BEE_LOOP", 2F),

    /**
     * Represents an Axolotl on a {@link HeadPet}.
     */
    AXOLOTL("ENTITY_AXOLOTL_ATTACK", 2F),

    /**
     * Represents a Capybara on a {@link HeadPet}.
     */
    CAPYBARA,

    /**
     * Represents a Mouse on a {@link HeadPet}.
     */
    MOUSE(Sound.ENTITY_SILVERFISH_AMBIENT, 2F),

    /**
     * Represents a Tiger on a {@link HeadPet}.
     */
    TIGER(Sound.ENTITY_WITHER_AMBIENT, 2F),

    /**
     * Represents a Blaze on a {@link HeadPet}.
     */
    BLAZE(Sound.ENTITY_BLAZE_AMBIENT),

    /**
     * Represents a Jellyfish on a {@link HeadPet}.
     */
    JELLYFISH(Sound.ENTITY_SLIME_JUMP, 0F),

    /**
     * Represents a Whale on a {@link HeadPet}.
     */
    WHALE(Sound.ENTITY_GHAST_AMBIENT, 0F),

    /**
     * Represents a Slime on a {@link HeadPet}.
     */
    SLIME(Sound.ENTITY_SLIME_JUMP)
    ;

    private final Class<? extends Pet> petClass;
    private final Sound ambientSound;
    private final float ambientPitch;

    PetType() { this((Sound) null); }

    PetType(String s) { this(s, 1F); }

    PetType(String s, float ambientPitch) { this(findSound(s), ambientPitch); }

    PetType(Sound ambientSound) { this(ambientSound, 1F); }

    PetType(Sound ambientSound, float ambientPitch) { this(HeadPet.class, ambientSound, ambientPitch); }

    PetType(Class<? extends Pet> petClass, Sound ambientSound, float ambientPitch) {
        this.petClass = petClass;
        this.ambientSound = ambientSound;
        this.ambientPitch = ambientPitch;
    }

    PetType(Class<? extends Pet> petClass, Sound ambientSound) { this(petClass, ambientSound, 1F); }

    PetType(Class<? extends Pet> petClass) { this(petClass, null); }

    private static Sound findSound(String s) {
        try {
            return Sound.valueOf(s);
        } catch (IllegalArgumentException e) {
            return null;
        }
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
     * Fetches the sound that is played ambiently, when this Pet is spawned.
     * @return Ambient Pet Sound
     */
    @NotNull
    public Sound getAmbientSound() {
        return ambientSound;
    }

    /**
     * Fetches the pitch of the ambient sound.
     * @return Ambient Sound Pitch
     */
    public float getAmbientPitch() {
        return ambientPitch;
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
