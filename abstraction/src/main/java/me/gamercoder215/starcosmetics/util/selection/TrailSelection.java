package me.gamercoder215.starcosmetics.util.selection;

import me.gamercoder215.starcosmetics.api.CompletionCriteria;
import me.gamercoder215.starcosmetics.api.Rarity;
import me.gamercoder215.starcosmetics.api.cosmetics.trail.Trail;
import org.jetbrains.annotations.NotNull;

public final class TrailSelection extends CosmeticSelection<Object> {

    private final String name;
    private final Trail<?> parent;

    public TrailSelection(String name, Trail<?> parent, Object value, CompletionCriteria criteria, Rarity rarity) {
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
    public @NotNull Class<?> getInputType() {
        return input.getClass();
    }

}