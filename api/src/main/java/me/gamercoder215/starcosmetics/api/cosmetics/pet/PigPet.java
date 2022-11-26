package me.gamercoder215.starcosmetics.api.cosmetics.pet;

import me.gamercoder215.starcosmetics.api.Rarity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Pig;

/**
 * Represents a Pet for a {@link EntityType#PIG}.
 */
public interface PigPet extends Pet {

    @Override
    Pig getEntity();

    @Override
    default Rarity getRarity() {
        return Rarity.COMMON;
    }

}
