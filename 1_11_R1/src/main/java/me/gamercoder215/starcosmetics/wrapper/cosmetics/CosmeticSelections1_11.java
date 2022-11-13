package me.gamercoder215.starcosmetics.wrapper.cosmetics;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import me.gamercoder215.starcosmetics.api.CompletionCriteria;
import me.gamercoder215.starcosmetics.api.Rarity;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseGadget;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseShape;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseTrail;
import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import me.gamercoder215.starcosmetics.util.StarMaterial;
import me.gamercoder215.starcosmetics.util.selection.CosmeticSelection;
import me.gamercoder215.starcosmetics.util.selection.ParticleSelection;
import me.gamercoder215.starcosmetics.util.selection.TrailSelection;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;

import java.util.List;
import java.util.Map;

import static me.gamercoder215.starcosmetics.wrapper.cosmetics.CosmeticSelections.getForVersion;
import static me.gamercoder215.starcosmetics.wrapper.cosmetics.CosmeticSelections.join;

public class CosmeticSelections1_11 implements CosmeticSelections {

    // Cosmetic Maps

    // Trails
    private static final List<CosmeticSelection<?>> PROJECTILE_TRAILS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new TrailSelection("totems", BaseTrail.PROJECTILE_TRAIL, StarMaterial.TOTEM_OF_UNDYING.find(),
                    CompletionCriteria.fromKilled(350, EntityType.EVOKER), Rarity.EPIC))
            .add(new TrailSelection("shulker_shell", BaseTrail.PROJECTILE_TRAIL, Material.SHULKER_SHELL,
                    CompletionCriteria.fromKilled(350, EntityType.SHULKER), Rarity.EPIC))

            .build();

    // Shapes

    // Small Rings

    private static final List<CosmeticSelection<?>> SMALL_RINGS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new ParticleSelection("totem", BaseShape.SMALL_RING, Particle.TOTEM,
                    CompletionCriteria.fromKilled(350, EntityType.EVOKER), Rarity.EPIC))
            .build();

    // Selections

    private static final Map<Cosmetic, List<CosmeticSelection<?>>> SELECTIONS = ImmutableMap.<Cosmetic, List<CosmeticSelection<?>>>builder()
            .put(BaseTrail.PROJECTILE_TRAIL, join(PROJECTILE_TRAILS, BaseTrail.PROJECTILE_TRAIL, "1_10"))
            .put(BaseTrail.GROUND_TRAIL, getForVersion(BaseTrail.GROUND_TRAIL, "1_10"))
            .put(BaseTrail.SOUND_TRAIL, getForVersion(BaseTrail.SOUND_TRAIL, "1_10"))

            .put(BaseShape.SMALL_RING, join(SMALL_RINGS, BaseShape.SMALL_RING, "1_10"))
            .put(BaseShape.SMALL_DETAILED_RING, getForVersion(BaseShape.SMALL_DETAILED_RING, "1_10"))
            .put(BaseShape.LARGE_RING, getForVersion(BaseShape.LARGE_RING, "1_10"))
            .put(BaseShape.LARGE_DETAILED_RING, getForVersion(BaseShape.LARGE_DETAILED_RING, "1_10"))

            .put(BaseGadget.CLICK_GADGET, getForVersion(BaseGadget.CLICK_GADGET, "1_10"))
            .build();

    @Override
    public Map<Cosmetic, List<CosmeticSelection<?>>> getAllSelections() {
        return SELECTIONS;
    }

}
