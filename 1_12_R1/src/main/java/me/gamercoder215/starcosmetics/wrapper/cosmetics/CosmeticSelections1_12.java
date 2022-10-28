package me.gamercoder215.starcosmetics.wrapper.cosmetics;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import me.gamercoder215.starcosmetics.api.CompletionCriteria;
import me.gamercoder215.starcosmetics.api.Rarity;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseTrail;
import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import me.gamercoder215.starcosmetics.util.selection.CosmeticSelection;
import me.gamercoder215.starcosmetics.util.selection.TrailSelection;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;

import java.util.List;
import java.util.Map;

import static me.gamercoder215.starcosmetics.wrapper.cosmetics.CosmeticSelections.join;

public class CosmeticSelections1_12 implements CosmeticSelections {

    // Cosmetic Maps

    // Trails
    private static final List<CosmeticSelection<?>> PROJECTILE_TRAILS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new TrailSelection("concrete", BaseTrail.PROJECTILE_TRAIL, ImmutableList.of(
                    Material.RED_GLAZED_TERRACOTTA, Material.ORANGE_GLAZED_TERRACOTTA, Material.YELLOW_GLAZED_TERRACOTTA,
                    Material.GREEN_GLAZED_TERRACOTTA, Material.CYAN_GLAZED_TERRACOTTA, Material.BLUE_GLAZED_TERRACOTTA,
                    Material.MAGENTA_GLAZED_TERRACOTTA, Material.PURPLE_GLAZED_TERRACOTTA, Material.PINK_GLAZED_TERRACOTTA
            ), CompletionCriteria.fromStatistic(Statistic.ANIMALS_BRED, 650), Rarity.RARE))

            .add(new TrailSelection("nuggets", BaseTrail.PROJECTILE_TRAIL, ImmutableList.of(Material.GOLD_NUGGET, Material.IRON_NUGGET),
                    CompletionCriteria.fromKilled(1000, EntityType.IRON_GOLEM), Rarity.LEGENDARY))

            .build();

    // Selections

    private static final Map<Cosmetic, List<CosmeticSelection<?>>> SELECTIONS = ImmutableMap.<Cosmetic, List<CosmeticSelection<?>>>builder()
            .put(BaseTrail.PROJECTILE_TRAIL, join(PROJECTILE_TRAILS, BaseTrail.PROJECTILE_TRAIL, "1_11"))
            .build();

    @Override
    public Map<Cosmetic, List<CosmeticSelection<?>>> getAllSelections() {
        return SELECTIONS;
    }

}
