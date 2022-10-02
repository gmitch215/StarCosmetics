package me.gamercoder215.starcosmetics.util.selection;

import me.gamercoder215.starcosmetics.api.cosmetics.CompletionCriteria;
import me.gamercoder215.starcosmetics.api.cosmetics.CosmeticKey;
import me.gamercoder215.starcosmetics.api.cosmetics.CosmeticRarity;

public abstract class CosmeticSelection {

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

    public final String constructKey() { return getParent().getNamespace() + ":" + getKey(); }

    public abstract String getKey();

    public abstract CosmeticKey getParent();
}
