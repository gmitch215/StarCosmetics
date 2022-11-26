package me.gamercoder215.starcosmetics.api.cosmetics.pet;

import me.gamercoder215.starcosmetics.api.Rarity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.IronGolem;

/**
 * Represents a Pet for a {@link EntityType#IRON_GOLEM}.
 */
public interface GolemPet extends Pet {

    @Override
    IronGolem getEntity();

    @Override
    default Rarity getRarity() {
        return Rarity.EPIC;
    }

}
