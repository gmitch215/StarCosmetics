package me.gamercoder215.starcosmetics.util.selection;

import me.gamercoder215.starcosmetics.api.cosmetics.CompletionCriteria;
import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import me.gamercoder215.starcosmetics.api.cosmetics.CosmeticRarity;
import me.gamercoder215.starcosmetics.api.cosmetics.Trail;

public class TrailSelection extends CosmeticSelection {

    private final String name;
    private final Trail parent;

    public TrailSelection(String name, Trail parent, Object value, CompletionCriteria criteria, CosmeticRarity rarity) {
        super(value, criteria, rarity);

        this.name = name;
        this.parent = parent;
    }

    @Override
    public String getKey() {
        return name;
    }

    @Override
    public Cosmetic getParent() {
        return parent;
    }
}