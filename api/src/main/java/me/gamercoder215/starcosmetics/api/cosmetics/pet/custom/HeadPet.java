package me.gamercoder215.starcosmetics.api.cosmetics.pet.custom;

import me.gamercoder215.starcosmetics.api.cosmetics.pet.Pet;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

/**
 * Represents a Pet that uses an Invisible {@link EntityType#ARMOR_STAND} with a head.
 */
public interface HeadPet extends Pet {

    @Override
    ArmorStand getEntity();

}
