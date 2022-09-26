package me.gamercoder215.starcosmetics.api.cosmetics;

import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.wrapper.Wrapper;

public abstract class CosmeticSelection {

    public static final Wrapper w = StarConfig.getWrapper();

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
