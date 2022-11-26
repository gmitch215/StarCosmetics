package me.gamercoder215.starcosmetics.api.cosmetics.pet;

import me.gamercoder215.starcosmetics.api.CompletionCriteria;
import me.gamercoder215.starcosmetics.api.Rarity;
import me.gamercoder215.starcosmetics.api.cosmetics.pet.custom.HeadPet;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Represents Pet Information, spawned or not
 */
public class PetInfo {

    private final Rarity rarity;
    private final ItemStack icon;

    private final String name;

    private final CompletionCriteria criteria;

    PetInfo(Rarity rarity, CompletionCriteria criteria, ItemStack icon, String name) {
        this.rarity = rarity;
        this.criteria = criteria;
        this.icon = icon;
        this.name = name;
    }

    /**
     * Fetches the Pet's Rarity.
     * @return Pet Rarity
     */
    @NotNull
    public Rarity getRarity() {
        return rarity;
    }

    /**
     * Fetches the Pet's Icon. Also used in {@link HeadPet} for the Armor Stand's Head.
     * @return Pet Icon
     */
    @NotNull
    public ItemStack getIcon() {
        return icon;
    }

    /**
     * Fetches the CompletionCrtieria needed to unlock the Pet.
     * @return CompletionCriteria
     */
    @NotNull
    public CompletionCriteria getCriteria() {
        return criteria;
    }

    /**
     * Fetches the Pet's Display Name.
     * @return Pet Display Name
     */
    public String getName() {
        return name;
    }
}
