package me.gamercoder215.starcosmetics.api.cosmetics.pet;

import me.gamercoder215.starcosmetics.api.Rarity;
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
    Rarity getRarity();

    /**
     * Fetches the Pet's Owner.
     * @return Pet Owner
     */
    @NotNull
    Player getOwner();

    /**
     * Fetches the Pet's Display Name.
     * @return Pet Display Name
     */
    @NotNull
    String getPetName();

}
