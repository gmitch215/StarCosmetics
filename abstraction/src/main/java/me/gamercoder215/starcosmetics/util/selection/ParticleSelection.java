package me.gamercoder215.starcosmetics.util.selection;

import me.gamercoder215.starcosmetics.api.cosmetics.CompletionCriteria;
import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import me.gamercoder215.starcosmetics.api.cosmetics.CosmeticRarity;
import me.gamercoder215.starcosmetics.api.cosmetics.ParticleShape;

public class ParticleSelection extends CosmeticSelection {

    private final ParticleShape parent;
    private final String name;

    public ParticleSelection(String name, ParticleShape parent, Object object, CompletionCriteria criteria, CosmeticRarity rarity) {
        super(object, criteria, rarity);

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