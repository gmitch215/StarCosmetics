package me.gamercoder215.starcosmetics.util.selection;

import me.gamercoder215.starcosmetics.api.CompletionCriteria;
import me.gamercoder215.starcosmetics.api.Rarity;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseShape;
import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import me.gamercoder215.starcosmetics.api.cosmetics.particle.ParticleShape;
import me.gamercoder215.starcosmetics.util.inventory.StarInventoryUtil;
import me.gamercoder215.starcosmetics.wrapper.Wrapper;
import org.jetbrains.annotations.NotNull;

public final class ParticleSelection extends CosmeticSelection<Object> {

    private final ParticleShape parent;
    private final String name;

    public ParticleSelection(String name, ParticleShape parent, Object object, CompletionCriteria criteria, Rarity rarity) {
        super(object, criteria, rarity);

        this.name = name;
        this.parent = parent;
    }

    @Override
    public String getKey() {
        return name;
    }

    @Override
    public @NotNull String getDisplayName() {
        return Wrapper.get("cosmetics.particle_shapes." + name, StarInventoryUtil.toInputString(getInput()));
    }

    @Override
    public Cosmetic getParent() {
        return parent;
    }

    @Override
    public Class<? extends Cosmetic> getParentClass() {
        return BaseShape.class;
    }

    @Override
    @NotNull
    public Class<?> getInputType() {
        return getInput().getClass();
    }
}