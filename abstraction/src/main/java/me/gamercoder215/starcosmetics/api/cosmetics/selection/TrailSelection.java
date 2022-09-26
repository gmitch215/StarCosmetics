package me.gamercoder215.starcosmetics.api.cosmetics.selection;

import me.gamercoder215.starcosmetics.api.cosmetics.BaseTrail;
import me.gamercoder215.starcosmetics.api.cosmetics.CompletionCriteria;
import me.gamercoder215.starcosmetics.api.cosmetics.CosmeticKey;
import me.gamercoder215.starcosmetics.api.cosmetics.CosmeticRarity;

public class TrailSelection extends CosmeticSelection {

    private final String name;
    private final BaseTrail parent;

    public TrailSelection(String name, BaseTrail parent, Object value, CompletionCriteria criteria, CosmeticRarity rarity) {
        super(value, criteria, rarity);

        this.name = name;
        this.parent = parent;
    }

    @Override
    public String getKey() {
        return name;
    }

    @Override
    public CosmeticKey getParent() {
        return parent;
    }
}