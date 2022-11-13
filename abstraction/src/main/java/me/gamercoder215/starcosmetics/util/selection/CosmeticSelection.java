package me.gamercoder215.starcosmetics.util.selection;

import me.gamercoder215.starcosmetics.api.CompletionCriteria;
import me.gamercoder215.starcosmetics.api.Rarity;
import me.gamercoder215.starcosmetics.api.cosmetics.registry.CosmeticLocation;

import java.util.Objects;

public abstract class CosmeticSelection<T> implements CosmeticLocation<T> {

    protected final T input;

    protected final CompletionCriteria completionCriteria;

    protected final Rarity rarity;

    protected CosmeticSelection(T input, CompletionCriteria criteria, Rarity rarity) {
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
    public final Rarity getRarity() {
        return rarity;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CosmeticSelection<?>)) return false;
        CosmeticSelection<?> s = (CosmeticSelection<?>) o;
        if (this == o) return true;

        return getFullKey().equals(s.getFullKey());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(getFullKey());
    }

    @Override
    public String toString() {
        return "CosmeticSelection[" + getFullKey() + "]";
    }
    
}
