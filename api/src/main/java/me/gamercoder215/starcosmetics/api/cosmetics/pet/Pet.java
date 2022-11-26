package me.gamercoder215.starcosmetics.api.cosmetics.pet;

import me.gamercoder215.starcosmetics.api.Rarity;
import me.gamercoder215.starcosmetics.api.StarConfig;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an active StarCosmetics Pet.
 */
public interface Pet {

    /**
     * The Scoreboard Tag for a StarCosmetics Pet.
     */
    String PET_TAG = "starcosmetics:pet";

    /**
     * Fetches the Pet's Bukkit Entity.
     * @return Entity Type
     */
    @NotNull
    LivingEntity getEntity();

    /**
     * Fetches the Pet's Rarity.
     * @return Pet Rarity
     */
    @NotNull
    default Rarity getRarity() {
        return getInfo().getRarity();
    }

    /**
     * Fetches the Pet's Information.
     * @return Pet Info
     */
    @NotNull
    default PetInfo getInfo() {
        return StarConfig.getRegistry().getPetInfo(getPetType());
    }

    /**
     * Fetches the Pet's Owner.
     * @return Pet Owner
     */
    @NotNull
    Player getOwner();

    /**
     * Fetches the Pet's Display Name based on {@link #getOwner()}.
     * @return Pet Display Name with Owner
     */
    @NotNull
    default String getPetName() {
        return getRarity().getPrefix() +
                (getOwner().getDisplayName() != null ? getOwner().getDisplayName() : getOwner().getName()) + "'s " +
                getRarity().getPrefix() + " " + getInfo().getName();
    }

    /**
     * Fetches this Pet's {@link PetType}.
     * @return Pet Type
     */
    @NotNull
    default PetType getPetType() {
        return PetType.fromPetClass(this.getClass());
    }

}
