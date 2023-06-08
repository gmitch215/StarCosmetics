package me.gamercoder215.starcosmetics.wrapper.cosmetics;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseHat;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseShape;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseTrail;
import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import me.gamercoder215.starcosmetics.util.selection.CosmeticSelection;
import me.gamercoder215.starcosmetics.util.selection.TrailSelection;
import org.bukkit.Material;
import org.bukkit.Statistic;

import java.util.List;
import java.util.Map;

import static me.gamercoder215.starcosmetics.api.CompletionCriteria.fromMined;
import static me.gamercoder215.starcosmetics.api.CompletionCriteria.fromStatistic;
import static me.gamercoder215.starcosmetics.api.Rarity.EPIC;
import static me.gamercoder215.starcosmetics.api.Rarity.OCCASIONAL;
import static me.gamercoder215.starcosmetics.wrapper.cosmetics.CosmeticSelections.getForVersion;
import static me.gamercoder215.starcosmetics.wrapper.cosmetics.CosmeticSelections.join;

final class CosmeticSelections1_20 implements CosmeticSelections {

    // Trails

    // Projectile Trails

    private static final List<CosmeticSelection<?>> PROJECTILE_TRAIL = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new TrailSelection("relic", BaseTrail.PROJECTILE_TRAIL, Material.MUSIC_DISC_RELIC,
                    fromStatistic(Statistic.ANIMALS_BRED, 5500), EPIC))
            .build();

    // Ground Trails

    private static final List<CosmeticSelection<?>> GROUND_TRAIL = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new TrailSelection("pink_petals", BaseTrail.GROUND_TRAIL, Material.PINK_PETALS,
                    fromMined(100, Material.CHERRY_SAPLING), OCCASIONAL))
            .build();

    // Selections

    private static final Map<Cosmetic, List<CosmeticSelection<?>>> SELECTIONS = ImmutableMap.<Cosmetic, List<CosmeticSelection<?>>>builder()
            .put(BaseTrail.PROJECTILE_TRAIL, join(PROJECTILE_TRAIL, BaseTrail.PROJECTILE_TRAIL, "1_19_R3"))
            .put(BaseTrail.GROUND_TRAIL, join(GROUND_TRAIL, BaseTrail.GROUND_TRAIL, "1_19_R3"))
            .put(BaseTrail.SOUND_TRAIL, getForVersion(BaseTrail.SOUND_TRAIL, "1_19_R3"))

            .put(BaseShape.ALL, getForVersion(BaseShape.ALL, "1_19_R3"))

            .put(BaseHat.NORMAL, getForVersion(BaseHat.NORMAL, "1_19_R3"))
            .put(BaseHat.ANIMATED, getForVersion(BaseHat.ANIMATED, "1_19_R3"))
            .build();


    @Override
    public Map<Cosmetic, List<CosmeticSelection<?>>> getAllSelections() {
        return SELECTIONS;
    }
}
