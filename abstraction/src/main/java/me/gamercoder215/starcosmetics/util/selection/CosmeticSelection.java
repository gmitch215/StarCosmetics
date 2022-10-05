package me.gamercoder215.starcosmetics.util.selection;

import me.gamercoder215.starcosmetics.api.cosmetics.CompletionCriteria;
import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import me.gamercoder215.starcosmetics.api.cosmetics.CosmeticRarity;
import me.gamercoder215.starcosmetics.api.cosmetics.registry.CosmeticLocation;

public abstract class CosmeticSelection implements CosmeticLocation {

    protected final Object input;

    protected final CompletionCriteria completionCriteria;

    protected final CosmeticRarity rarity;

    protected CosmeticSelection(Object input, CompletionCriteria criteria, CosmeticRarity rarity) {
        this.input = input;
        this.completionCriteria = criteria;
        this.rarity = rarity;
    }

    public final Object getInput() {
        return input;
    }

    public final CompletionCriteria getCompletionCriteria() {
        return completionCriteria;
    }

    public final CosmeticRarity getRarity() {
        return rarity;
    }

    @Override
    public final String getNamespace() { return getParent().getNamespace(); }

    public abstract Cosmetic getParent();
}
