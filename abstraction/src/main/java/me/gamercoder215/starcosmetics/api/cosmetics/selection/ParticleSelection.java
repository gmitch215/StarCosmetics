package me.gamercoder215.starcosmetics.api.cosmetics.selection;

import me.gamercoder215.starcosmetics.api.cosmetics.BaseShape;
import me.gamercoder215.starcosmetics.api.cosmetics.CompletionCriteria;
import me.gamercoder215.starcosmetics.api.cosmetics.CosmeticKey;
import me.gamercoder215.starcosmetics.api.cosmetics.CosmeticRarity;

public class ParticleSelection extends CosmeticSelection {

    private final BaseShape parent;
    private final String name;

    public ParticleSelection(String name, BaseShape parent, Object object, CompletionCriteria criteria, CosmeticRarity rarity) {
        super(object, criteria, rarity);

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