package me.gamercoder215.starcosmetics.wrapper.cosmetics;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import me.gamercoder215.starcosmetics.api.cosmetics.*;
import me.gamercoder215.starcosmetics.util.selection.CosmeticSelection;
import me.gamercoder215.starcosmetics.util.selection.HatSelection;
import me.gamercoder215.starcosmetics.util.selection.TrailSelection;
import me.gamercoder215.starcosmetics.wrapper.Wrapper;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;

import java.util.List;
import java.util.Map;

import static me.gamercoder215.starcosmetics.api.CompletionCriteria.fromCrafted;
import static me.gamercoder215.starcosmetics.api.CompletionCriteria.fromKilled;
import static me.gamercoder215.starcosmetics.api.Rarity.*;
import static me.gamercoder215.starcosmetics.util.selection.HatSelection.of;
import static me.gamercoder215.starcosmetics.wrapper.cosmetics.CosmeticSelections.getForVersion;
import static me.gamercoder215.starcosmetics.wrapper.cosmetics.CosmeticSelections.join;

@SuppressWarnings("UnstableApiUsage")
final class CosmeticSelections1_20_R3 implements CosmeticSelections {

    // Trails

    // Projectile Trails

    public static final List<CosmeticSelection<?>> PROJECTILE_TRAILS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new TrailSelection("gust", BaseTrail.PROJECTILE_TRAIL, Particle.GUST,
                    fromKilled(1, EntityType.BREEZE), UNCOMMON))
            .build();

    // Hats

    // Normal Hats

    public static final List<CosmeticSelection<?>> NORMAL_HATS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new HatSelection("copper_grate", Material.COPPER_GRATE,
                    fromCrafted(10, Material.COPPER_GRATE), COMMON))
            .build();

    public static final List<CosmeticSelection<?>> ANIMATED_HATS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new HatSelection("copper_grates",
                    of(15, Material.COPPER_GRATE, Material.EXPOSED_COPPER_GRATE,
                            Material.WEATHERED_COPPER_GRATE, Material.OXIDIZED_COPPER_GRATE),
                    fromCrafted(100, Material.COPPER_GRATE), UNCOMMON))
            .build();

    private static final Map<Cosmetic, List<CosmeticSelection<?>>> SELECTIONS = ImmutableMap.<Cosmetic, List<CosmeticSelection<?>>>builder()
            .put(BaseTrail.PROJECTILE_TRAIL, join(PROJECTILE_TRAILS, BaseTrail.PROJECTILE_TRAIL, "1_20"))
            .put(BaseTrail.GROUND_TRAIL, getForVersion(BaseTrail.GROUND_TRAIL, "1_20"))
            .put(BaseTrail.SOUND_TRAIL, getForVersion(BaseTrail.SOUND_TRAIL, "1_20"))

            .put(BaseShape.ALL, getForVersion(BaseShape.ALL, "1_20"))

            .put(BaseHat.NORMAL, join(NORMAL_HATS, BaseHat.NORMAL, "1_20"))
            .put(BaseHat.ANIMATED, join(ANIMATED_HATS, BaseHat.ANIMATED, "1_20"))

            .put(BaseGadget.INSTANCE, getForVersion(BaseGadget.INSTANCE, "1_20"))

            .put(BaseCape.NORMAL, getForVersion(BaseCape.NORMAL, "1_20"))
            .put(BaseCape.ANIMATED, getForVersion(BaseCape.ANIMATED, "1_20"))
            .build();

    @Override
    public Map<Cosmetic, List<CosmeticSelection<?>>> getAllSelections() {
        if (Wrapper.getServerVersion().equalsIgnoreCase("1_20_R3") && !w.hasFeatureFlag("c")) return CosmeticSelections.getForVersion("1_20");

        return SELECTIONS;
    }
    
}
