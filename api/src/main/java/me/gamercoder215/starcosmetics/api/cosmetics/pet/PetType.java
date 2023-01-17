package me.gamercoder215.starcosmetics.api.cosmetics.pet;

import me.gamercoder215.starcosmetics.api.Rarity;
import me.gamercoder215.starcosmetics.api.StarConfig;
import org.bukkit.Sound;
import org.jetbrains.annotations.NotNull;

/**
 * Represents all of the Pet Types.
 */
public enum PetType {

    /**
     * Represents a Bee.
     */
    BEE,

    /**
     * Represents a Rabbit.
     */
    RABBIT,

    /**
     * Represents a Giraffe.
     */
    GIRAFFE,

    /**
     * Represents a Dolphin.
     */
    DOLPHIN(Sound.ENTITY_PLAYER_SPLASH),

    /**
     * Represents a Llama.
     */
    LLAMA,

    /**
     * Represents a Polar Bear.
     */
    POLAR_BEAR,

    /**
     * Represents an Elephant.
     */
    ELEPHANT,

    /**
     * Represents a Panda.
     */
    PANDA,

    /**
     * Represents a Fox.
     */
    FOX("ENTITY_FOX_AMBIENT"),

    /**
     * Represents a Narwhal.
     */
    NARWHAL("ENTITY_DROWNED_SWIM", 1.5F),

    /**
     * Represents a Pufferfish.
     */
    PUFFERFISH("ENTITY_PUFFER_FISH_FLOP"),

    /**
     * Represents a Gorilla.
     */
    GORILLA("ENTITY_PANDA_SNEEZE", 0F),

    /**
     * Represents a Strider.
     */
    STRIDER("ENTITY_STRIDER_AMBIENT"),

    /**
     * Represents a Tardigrade.
     */
    TARDIGRADE,

    /**
     * Represents a Hummingbird.
     */
    HUMMINGBIRD("ENTITY_BEE_LOOP", 2F),

    /**
     * Represents an Axolotl.
     */
    AXOLOTL("ENTITY_AXOLOTL_ATTACK", 2F),

    /**
     * Represents a Capybara.
     */
    CAPYBARA,

    /**
     * Represents a Mouse.
     */
    MOUSE(Sound.ENTITY_SILVERFISH_AMBIENT, 2F),

    /**
     * Represents a Tiger.
     */
    TIGER(Sound.ENTITY_WITHER_AMBIENT, 2F),

    /**
     * Represents a Blaze.
     */
    BLAZE(Sound.ENTITY_BLAZE_AMBIENT),

    /**
     * Represents a Jellyfish.
     */
    JELLYFISH(Sound.ENTITY_SLIME_JUMP, 0F),

    /**
     * Represents a Whale.
     */
    WHALE(Sound.ENTITY_GHAST_AMBIENT, 0F),

    /**
     * Represents a Slime.
     */
    SLIME(Sound.ENTITY_SLIME_JUMP),

    /**
     * Represents a Unicorn.
     */
    UNICORN(Sound.ENTITY_HORSE_AMBIENT),

    /**
     * Represents a Demogorgon.
     */
    DEMOGORGON(Sound.ENTITY_WOLF_GROWL, 0F),
    ;

    private final Sound ambientSound;
    private final float ambientPitch;

    PetType() { this((Sound) null); }

    PetType(String s) { this(s, 1F); }

    PetType(String s, float ambientPitch) { this(findSound(s), ambientPitch); }

    PetType(Sound ambientSound) { this(ambientSound, 1F); }

    PetType(Sound ambientSound, float ambientPitch) {
        this.ambientSound = ambientSound;
        this.ambientPitch = ambientPitch;
    }

    private static Sound findSound(String s) {
        try {
            return Sound.valueOf(s);
        } catch (IllegalArgumentException e) {
            return null;
        }
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
}
