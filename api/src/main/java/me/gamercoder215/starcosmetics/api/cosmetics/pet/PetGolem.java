package me.gamercoder215.starcosmetics.api.cosmetics.pet;

import me.gamercoder215.starcosmetics.api.Rarity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.IronGolem;

/**
 * Represents a Rideable {@link EntityType#IRON_GOLEM}.
 */
public interface PetGolem extends Rideable {

    @Override
    IronGolem getEntity();

    @Override
    default Rarity getRarity() {
        return Rarity.EPIC;
    }

    @Override
    default String getPetName() {
        return getRarity().getPrefix() +
                (getOwner().getDisplayName() != null ? getOwner().getDisplayName() : getOwner().getName()) + "'s " +
                getRarity().getPrefix() + " Golem";
    }

}
