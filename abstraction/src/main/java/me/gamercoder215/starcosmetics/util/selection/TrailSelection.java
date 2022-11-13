package me.gamercoder215.starcosmetics.util.selection;

import me.gamercoder215.starcosmetics.api.CompletionCriteria;
import me.gamercoder215.starcosmetics.api.Rarity;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseTrail;
import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import me.gamercoder215.starcosmetics.api.cosmetics.trail.Trail;
import me.gamercoder215.starcosmetics.util.inventory.StarInventoryUtil;
import me.gamercoder215.starcosmetics.wrapper.Wrapper;
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
    public @NotNull String getDisplayName() {
        return Wrapper.get("cosmetics.trail." + name, StarInventoryUtil.toInputString(getInput()));
    }

    @Override
    public Trail<?> getParent() {
        return parent;
    }

    @Override
    public Class<? extends Cosmetic> getParentClass() {
        return BaseTrail.class;
    }

    @Override
    public @NotNull Class<?> getInputType() {
        return input.getClass();
    }

}