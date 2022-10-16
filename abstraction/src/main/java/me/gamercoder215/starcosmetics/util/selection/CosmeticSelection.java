package me.gamercoder215.starcosmetics.util.selection;

import me.gamercoder215.starcosmetics.api.cosmetics.CompletionCriteria;
import me.gamercoder215.starcosmetics.api.cosmetics.CosmeticRarity;
import me.gamercoder215.starcosmetics.api.cosmetics.registry.CosmeticLocation;

public abstract class CosmeticSelection<T> implements CosmeticLocation<T> {

    protected final T input;

    protected final CompletionCriteria completionCriteria;

    protected final CosmeticRarity rarity;

    protected CosmeticSelection(T input, CompletionCriteria criteria, CosmeticRarity rarity) {
        this.input = input;
        this.completionCriteria = criteria;
        this.rarity = rarity;
    }

    @Override
    public final T getInput() {
        return input;
    }

    @Override
    public final CompletionCriteria getCompletionCriteria() {
        return completionCriteria;
    }

    @Override
    public final CosmeticRarity getRarity() {
        return rarity;
    }

    @Override
    public final String getNamespace() { return getParent().getNamespace(); }
}
