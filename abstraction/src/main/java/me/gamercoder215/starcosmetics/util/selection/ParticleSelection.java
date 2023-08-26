package me.gamercoder215.starcosmetics.util.selection;

import me.gamercoder215.starcosmetics.api.CompletionCriteria;
import me.gamercoder215.starcosmetics.api.Rarity;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseShape;
import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import me.gamercoder215.starcosmetics.api.cosmetics.particle.ParticleShape;
import me.gamercoder215.starcosmetics.util.Constants;
import me.gamercoder215.starcosmetics.util.inventory.StarInventoryUtil;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.NoSuchElementException;

import static me.gamercoder215.starcosmetics.wrapper.Wrapper.get;

public final class ParticleSelection extends CosmeticSelection<Object> {

    private final ParticleShape parent;
    private final String name;
    private final String displayName;

    public ParticleSelection(String name, ParticleShape parent, Object object, CompletionCriteria criteria, Rarity rarity) {
        super(object, criteria, rarity);

        this.name = name;
        this.displayName = get("cosmetics.particle_shapes." + name, StarInventoryUtil.toInputString(getInput()));
        this.parent = parent;
    }

    public ParticleSelection(Map<String, Object> customData) {
        super(parseObject(customData.get("object")), CompletionCriteria.fromPermission(customData.get("permission").toString()), Rarity.valueOf(customData.get("rarity").toString().toUpperCase()));

        this.name = customData.get("id").toString();
        this.displayName = customData.get("name").toString();
        this.parent = (ParticleShape) Constants.PARENTS.stream()
                .filter(p -> p instanceof ParticleShape)
                .filter(p -> p.getNamespace().equalsIgnoreCase(customData.get("shape").toString()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown shape '" + customData.get("shape") + "'"));
    }

    @Override
    public String getKey() {
        return name;
    }

    @Override
    public @NotNull String getDisplayName() {
        return displayName;
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

    private static Enum<?> parseObject(Object obj) {
        String s = obj.toString().toUpperCase();

        try {
            return Particle.valueOf(s);
        } catch (NoSuchElementException e) {
            Material m = Material.matchMaterial(s);
            if (m != null) return m;

            throw new IllegalArgumentException("Unknown Object '" + obj + "'");
        }
    }
}