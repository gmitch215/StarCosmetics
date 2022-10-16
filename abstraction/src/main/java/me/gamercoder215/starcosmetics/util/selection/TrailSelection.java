package me.gamercoder215.starcosmetics.util.selection;

import org.jetbrains.annotations.NotNull;

import me.gamercoder215.starcosmetics.api.cosmetics.CompletionCriteria;
import me.gamercoder215.starcosmetics.api.cosmetics.CosmeticRarity;
import me.gamercoder215.starcosmetics.api.cosmetics.Trail;

public final class TrailSelection extends CosmeticSelection<Object> {

    private final String name;
    private final Trail<?> parent;

    public TrailSelection(String name, Trail<?> parent, Object value, CompletionCriteria criteria, CosmeticRarity rarity) {
        super(value, criteria, rarity);

        this.name = name;
        this.parent = parent;
    }

    @Override
    public String getKey() {
        return name;
    }

    @Override
    public Trail<?> getParent() {
        return parent;
    }

    @Override
    public @NotNull Class<? extends Object> getInputType() {
        return input.getClass();
    }

}